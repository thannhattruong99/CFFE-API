package com.screens.file.service;

import com.common.service.BaseService;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.*;
import com.listeners.events.CustomEventListener;
import com.listeners.events.EventCreator;
import com.listeners.events.EventPublisher;
import com.screens.file.dto.FileTransaction;
import com.screens.file.dto.Notification;
import com.screens.file.dto.VideoProperty;
import com.screens.file.form.ResponseUploadImage;
import com.screens.file.form.ResponseUploadVideo;
import com.util.FileHelper;
import com.util.GCPHelper;
import com.util.MessageConstant;
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

    @Autowired
    EventPublisher eventPublisher;

    @Autowired
    CustomEventListener customEventListener;

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

        // upload file to server
        String fileName = FileHelper.storeFileOnServer(file, RESOURCE_PATH + IMAGE_FOLDER_SERVER);
        if (fileName.isEmpty()) {
            response.setErrorCodes(getError(MessageConstant.MSG_114));
            return response;
        }
        // get file on server upload to storage
        try {
            response.setFilePath(GCPHelper.uploadFile(IMAGE_FOLDER_SERVER + fileName,
                    IMAGE_FOLDER_CLOUD + StringUtils.cleanPath(fileName)));
            FileHelper.deleteFile(IMAGE_FOLDER_SERVER + fileName);
        } catch (IOException e) {
            System.out.println("Toang roi ne: " + e.getMessage());
        }
        return response;
    }

    /**
     * Upload Video To Server
     * @param files List video
     * @return List of path file on server
     * @throws IOException
     */
    public ResponseUploadVideo uploadVideoToServer(MultipartFile[] files) throws IOException {
        ResponseUploadVideo response = new ResponseUploadVideo();

        // check size file
        for(MultipartFile file : files) {
            if(file.isEmpty() || file.getSize()==0){
                response.setErrorCodes(getError(MessageConstant.MSG_119));
            }
            // check type file
            else if(!file.getContentType().toLowerCase().equals("video/mp4")){
                response.setErrorCodes(getError(MessageConstant.MSG_118));
            }
            if (response.getErrorCodes() != null) {
                return response;
            }
        }

        // upload file to server
        List<VideoProperty> listVideoProperty = new ArrayList<>();
        for(MultipartFile file : files) {
            String fileNameUUID = FileHelper.storeFileOnServer(file, RESOURCE_PATH + INPUT_VIDEO_PATH);
            if (fileNameUUID.isEmpty()) {
                response.setErrorCodes(getError(MessageConstant.MSG_114));
                return response;
            } else {
                VideoProperty videoProperty = new VideoProperty();
                getVideoProperties(videoProperty, fileNameUUID, file.getOriginalFilename());
                listVideoProperty.add(videoProperty);
            }
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

    private void getVideoProperties(VideoProperty videoProperty, String fileNameUUID, String originalFileName) throws IOException {
        String filePath = FileHelper.getResourcePath() + INPUT_VIDEO_PATH + fileNameUUID;
        IsoFile isoFile = new IsoFile(filePath);
        MovieHeaderBox mhb = isoFile.getMovieBox().getMovieHeaderBox();
        videoProperty.setVideoNameOriginal(originalFileName);
        videoProperty.setVideoNameUUID(fileNameUUID);
        DateFormat dateFormat = new SimpleDateFormat(DAY_TIME_FORMAT);
        videoProperty.setStartedTime(dateFormat.format(mhb.getCreationTime()));
        videoProperty.setDuration((int)(mhb.getDuration() / mhb.getTimescale()));
        videoProperty.setStatusId(ACTIVE_STATUS);
        //  GET ShelfCameraMappingId, StackProductCameraMappingId FORM NAME
        String typeVideo = originalFileName.substring(0,originalFileName.lastIndexOf("_"));
        String oriName = originalFileName.substring(originalFileName.lastIndexOf("_")+1,originalFileName.lastIndexOf("."));
        if ("1".equalsIgnoreCase(typeVideo)){
            videoProperty.setShelfCameraMappingId(oriName);
        }
        if ("2".equalsIgnoreCase(typeVideo)){
            videoProperty.setStackProductCameraMappingId(oriName);
        }
    }


    public Flux<FileTransaction> getFileTransactions(String eventId) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
        // Lay data moi
        EventCreator data = customEventListener.getEventCreatorMap().get(eventId);

        Flux<FileTransaction> fileTransactionFlux = Flux.fromStream(
                // generate new data.
                Stream.generate(() -> new FileTransaction(data.getMessage(), data.getStatus()))
        );

        return Flux.zip(interval, fileTransactionFlux).map(Tuple2::getT2);
    }

}
