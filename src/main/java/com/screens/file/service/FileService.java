package com.screens.file.service;

import com.common.service.BaseService;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.screens.file.dto.FileTransaction;
import com.screens.file.dto.VideoProperty;
import com.screens.file.form.ResponseUploadImage;
import com.screens.file.form.ResponseUploadVideo;
import com.screens.file.listener.events.CustomEventListener;
import com.screens.file.listener.events.EventCreator;
import com.screens.file.listener.events.EventPublisher;
import com.screens.video.dao.VideoDAO;
import com.util.FileHelper;
import com.util.GCPHelper;
import com.util.MessageConstant;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

import static com.util.PathConstant.*;

@Service
public class FileService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);


    @Autowired
    EventPublisher eventPublisher;

    @Autowired
    CustomEventListener customEventListener;

    @Autowired
    private VideoDAO videoDAO;

    private static final String CONTENT_TYPE_IMAGE = "";
    private static final String CONTENT_TYPE_VIDEO = "video/mp4";
    private static final String NULL_STRING = "null";
    private static final int TIME_RETURN_TRANSACTIONS = 5;

    /**
     * Get file transactrion
     * @param eventId UUID of event
     * @return Flux<FileTransaction>
     */
    public Flux<FileTransaction> getFileTransactions(String eventId) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(TIME_RETURN_TRANSACTIONS));
        // GET NEW DATA
        EventCreator data = customEventListener.getEventCreatorMap().get(eventId);
        Flux<FileTransaction> fileTransactionFlux = Flux.fromStream(
                // GENERATE DATA
                Stream.generate(() -> new FileTransaction(
                        data.getTotalFile(),
                        data.getNumberFileDone(),
                        data.getFileSuccess(),
                        data.getFileError()))
        );
        return Flux.zip(interval, fileTransactionFlux).map(Tuple2::getT2);
    }

    /**
     * Upload Image To Storage
     * @param file Image
     * @return ResponseUploadImage
     */
    public ResponseUploadImage uploadImageToStorage(MultipartFile file) {
        ResponseUploadImage response = new ResponseUploadImage();

        // check size file
        if(file.isEmpty() || file.getSize()==0){
            response.setErrorCodes(getError(MessageConstant.MSG_112));
        }
        // check type file
        else if(!(file.getContentType().toLowerCase().equals("image/jpg")
                || file.getContentType().toLowerCase().equals("image/jpeg")
                || file.getContentType().toLowerCase().equals("image/png"))){
            response.setErrorCodes(getError(MessageConstant.MSG_113));
        }
        if (response.getErrorCodes() != null) {
            return response;
        }

        try {
            // UPLOAD FILE LOCAL => SERVER
            String fileName = FileHelper.storeFileOnServer(file, RESOURCE_PATH + IMAGE_FOLDER_SERVER);
            if (fileName.isEmpty()) {
                response.setErrorCodes(getError(MessageConstant.MSG_114));
                return response;
            }
            // UPLOAD FILE SERVER => STORAGE
            response.setFilePath(GCPHelper.uploadFile(IMAGE_FOLDER_SERVER + fileName,
                    IMAGE_FOLDER_CLOUD + StringUtils.cleanPath(fileName),CONTENT_TYPE_IMAGE));
            FileHelper.deleteFile(IMAGE_FOLDER_SERVER + fileName);
        } catch (IOException e) {
            logger.error("Error at FileService: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error at FileService: " + e.getMessage());
        }
        return response;
    }

    /**
     * Upload Video To Server
     * @param files List video
     * @return List of path file on server
     * @throws IOException
     */
    public ResponseUploadVideo uploadVideoToServer(MultipartFile[] files) {
        ResponseUploadVideo response = new ResponseUploadVideo();

        // check file
        for(MultipartFile file : files) {
            // check size
            if(file.isEmpty() || file.getSize()==0){
                response.setErrorCodes(getError(MessageConstant.MSG_119));
            }
            // check type
            else if(!file.getContentType().toLowerCase().equals("video/mp4")){
                response.setErrorCodes(getError(MessageConstant.MSG_118));
            }
            // check name
            else if (!validVideoName(file)) {
                response.setErrorCodes(getError(MessageConstant.MSG_121));
            }
            if (response.getErrorCodes() != null) {
                return response;
            }
        }

        List<VideoProperty> listVideoProperty = new ArrayList<>();
        try {
            for(MultipartFile file : files) {
                VideoProperty videoProperty = new VideoProperty();
                String fileNameUUID = FileHelper.storeFileOnServer(file, RESOURCE_PATH + INPUT_VIDEO_PATH);
                if (fileNameUUID.isEmpty()) {
                    response.setErrorCodes(getError(MessageConstant.MSG_114));
                    return response;
                } else {
                    String filePath = FileHelper.getResourcePath() + INPUT_VIDEO_PATH + fileNameUUID;
                    IsoFile isoFile = new IsoFile(filePath);
                    if (isoFile.getMovieBox() == null) {
                        response.setErrorCodes(getError(MessageConstant.MSG_118));
                        return response;
                    }
                    if (!getVideoProperties(videoProperty, fileNameUUID, file.getOriginalFilename())){
                        //TODO: Xoa het video tren server (listVideoProperty)
                        response.setErrorCodes(getError(MessageConstant.MSG_122));
                        return response;
                    }
                    listVideoProperty.add(videoProperty);
                }
            }
        } catch (IOException e){
            logger.error("Error at FileService: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error at FileService: " + e.getMessage());
        }

        if (duplicateVideo(listVideoProperty)) {
            response.setErrorCodes(getError(MessageConstant.MSG_123));
            return response;
        }

        response.setVideoPropertyList(listVideoProperty);
        response.setIdEvent(UUID.randomUUID() + "-" + getTime());
        return response;
    }

    private String getTime() {
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
        return dateFormat.format(cal.getTime());
    }

    // TODO: CHECK VIDEO DUPLICATE
    private boolean duplicateVideo(List<VideoProperty> listVideoProperty) {
        //Check duplicate input
        for (VideoProperty videoProperty : listVideoProperty) {
            int count = 0;
            String macAddress = videoProperty.getMacAddress();
            String startTime = videoProperty.getStartedTime();
            for (VideoProperty videoProperty2 : listVideoProperty) {
                if ((macAddress.equalsIgnoreCase(videoProperty2.getMacAddress()))
                    && (startTime.equalsIgnoreCase(videoProperty2.getStartedTime()))){
                    count++;
                }
            }
            if (count > 1) {
                return true;
            }
        }
        //Check duplicate database
        for (VideoProperty videoProperty : listVideoProperty) {
            if (DETECT_HOT_SPOT == videoProperty.getTypeVideo()) {
                if (videoDAO.isDuplicateVideoShelf(videoProperty)) {
                    return true;
                }
            }
            if (DETECT_EMOTION == videoProperty.getTypeVideo()) {
                if (videoDAO.isDuplicateVideoStack(videoProperty)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean validVideoName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String[] parts = fileName.split("_");
        if (parts.length != 3) {
            return false;
        }
        try {
            Integer.parseInt(parts[0]);
        }catch (NumberFormatException e) {
            return false;
        }
        String fileType = parts[2].substring(parts[2].lastIndexOf("."));
        if (!fileType.equalsIgnoreCase(".mp4")) {
            return false;
        }
        return true;
    }

    private boolean getVideoProperties(VideoProperty videoProperty, String fileNameUUID, String originalFileName) throws IOException {
        String filePath = FileHelper.getResourcePath() + INPUT_VIDEO_PATH + fileNameUUID;
        IsoFile isoFile = new IsoFile(filePath);
        MovieHeaderBox mhb = isoFile.getMovieBox().getMovieHeaderBox();
        videoProperty.setVideoNameOriginal(originalFileName);
        videoProperty.setVideoNameUUID(fileNameUUID);
        DateFormat dateFormat = new SimpleDateFormat(DAY_TIME_FORMAT);
        videoProperty.setStartedTime(dateFormat.format(mhb.getCreationTime()));

        Calendar gcal = new GregorianCalendar();
        gcal.setTime(mhb.getCreationTime());
        gcal.add(Calendar.SECOND, (int)(mhb.getDuration() / mhb.getTimescale()));
        Date endedTime = gcal.getTime();
        videoProperty.setEndedTime(dateFormat.format(endedTime));

        videoProperty.setDuration((int)(mhb.getDuration() / mhb.getTimescale()));
        videoProperty.setStatusId(ACTIVE_STATUS);
        String[] parts = originalFileName.split("_");
        videoProperty.setTypeVideo(Integer.parseInt(parts[0]));
        videoProperty.setMacAddress(parts[1]);

        try {
            if (DETECT_HOT_SPOT == videoProperty.getTypeVideo()) {
                String shelfCameraMappingId = videoDAO.getShelfCameraMappingId(videoProperty);
                // System.out.println("shelfCameraMappingId = " + shelfCameraMappingId);
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(shelfCameraMappingId)
                && !NULL_STRING.equalsIgnoreCase(shelfCameraMappingId)){
                    videoProperty.setShelfCameraMappingId(shelfCameraMappingId);
                } else {
                    return false;
                }
            }
            if (DETECT_EMOTION == videoProperty.getTypeVideo()) {
                String stackProductCameraMappingId = videoDAO.getStackProductCameraMappingId(videoProperty);
                // System.out.println("stackProductCameraMappingId = " + stackProductCameraMappingId);
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(stackProductCameraMappingId)
                        && !NULL_STRING.equalsIgnoreCase(stackProductCameraMappingId)){
                    videoProperty.setStackProductCameraMappingId(stackProductCameraMappingId);
                } else {
                    return false;
                }
            }
        } catch (PersistenceException e) {
            logger.error("Error at FileService: " + e.getMessage());
            return false;
        }
        return true;
    }

}
