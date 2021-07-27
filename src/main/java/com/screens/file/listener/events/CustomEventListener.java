package com.screens.file.listener.events;

import com.common.service.BaseService;
import com.google.cloud.storage.StorageException;
import com.screens.file.listener.detector.DetectService;
import com.screens.file.dto.VideoProperty;
import com.screens.file.listener.detector.DetectService;
import com.screens.file.listener.detector.EmotionDTO;
import com.screens.video.dao.VideoDAO;
import com.util.FileHelper;
import com.util.GCPHelper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.util.PathConstant.*;

@Component
public class CustomEventListener extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(CustomEventListener.class);

    private Map<String, EventCreator> eventCreatorMap;

    @Autowired
    private DetectService detectService;

    @Autowired
    private VideoDAO videoDAO;

    private static final String CONTENT_TYPE_VIDEO = "video/mp4";
    private static final String MSG_FAIL_CONNECTION = " is upload storage fail";
    private static final String MSG_FAIL_DETECTION = " is detected fail";

    /**
     * EVENT LISTENER
     * @param eventCreator
     * @throws InterruptedException
     */
    @Async
    @EventListener
    public void eventListener(EventCreator eventCreator) throws InterruptedException {
        if(eventCreatorMap == null){
            eventCreatorMap = new HashMap<>();
        }

        int totalFile = eventCreator.getVideoPropertyList().size();
        int numberFileDone = 0;
        List<String> fileSuccess = new ArrayList<>();
        List<String> fileError = new ArrayList<>();

        updateEvent(eventCreator,totalFile,numberFileDone,fileSuccess,fileError);

        // DO EVENT
        List<String> videoErrorNameList = new ArrayList<>();
        for (VideoProperty videoProperty: eventCreator.getVideoPropertyList()) {
            try{
                // System.out.println("START DETECT");
                int countHP;
                boolean flag = true;
                // DETECT VIDEO HOT SPOT / EMOTION
                if (DETECT_HOT_SPOT == videoProperty.getTypeVideo()) {
                    if((countHP = detectService.countPerson(videoProperty.getVideoNameUUID(),
                            videoProperty.getVideoNameUUID())) != -1){
                        videoProperty.setTotalPerson(countHP);
                    } else {
                        flag = false;
                    }
                }
                if (DETECT_EMOTION == videoProperty.getTypeVideo()) {
                    EmotionDTO emotionDTO;
                    if((emotionDTO = detectService.countEmotion(videoProperty.getVideoNameUUID(),
                            videoProperty.getVideoNameUUID())) != null){
                        videoProperty.setEmotions(emotionDTO);
                        // System.out.println("OUTPUT DETECT: " + emotionDTO.toString());
                    } else {
                        flag = false;
                    }
                }

                // DELETE FILE INPUT
                FileHelper.deleteFile(INPUT_VIDEO_PATH + videoProperty.getVideoNameUUID());

                // UPLOAD STORAGE CLOUD / INSERT DATABASE
                if (flag) {
                    if (uploadVideoDetectedToStorage(videoProperty)) {
                        insertDatabase(videoProperty,videoErrorNameList,eventCreator);

                        fileSuccess.add(videoProperty.getVideoNameOriginal());
                        numberFileDone++;
                        updateEvent(eventCreator,totalFile,numberFileDone,fileSuccess,fileError);
                    } else {
                        fileError.add(videoProperty.getVideoNameOriginal() + MSG_FAIL_CONNECTION);
                    }
                } else {
                    // System.out.println("DETECT FAIL");
                    fileError.add(videoProperty.getVideoNameOriginal() + MSG_FAIL_DETECTION);
                    numberFileDone++;
                    updateEvent(eventCreator,totalFile,numberFileDone,fileSuccess,fileError);
                }

            } catch (InterruptedException e) {
                fileError.add(videoProperty.getVideoNameOriginal()+ MSG_FAIL_DETECTION);
                numberFileDone++;
                updateEvent(eventCreator,totalFile,numberFileDone,fileSuccess,fileError);
            } catch (IOException e) {
                fileError.add(videoProperty.getVideoNameOriginal()+ MSG_FAIL_DETECTION);
                numberFileDone++;
                updateEvent(eventCreator,totalFile,numberFileDone,fileSuccess,fileError);
            }
        }

    }

    public Map<String, EventCreator> getEventCreatorMap() {
        return eventCreatorMap;
    }

    public void setEventCreatorMap(Map<String, EventCreator> eventCreatorMap) {
        this.eventCreatorMap = eventCreatorMap;
    }

    private boolean uploadVideoDetectedToStorage(VideoProperty videoProperty) {
        try {
            String outputPath = GCPHelper.uploadFile(OUTPUT_VIDEO_PATH + videoProperty.getVideoNameUUID(),
                    VIDEO_FOLDER_CLOUD + org.springframework.util.StringUtils.cleanPath(videoProperty.getVideoNameUUID()),
                    CONTENT_TYPE_VIDEO);
            videoProperty.setVideoUrl(outputPath);
            FileHelper.deleteFile(OUTPUT_VIDEO_PATH + videoProperty.getVideoNameUUID());
            return true;
        } catch (IOException e) {
            // System.out.println("Upload video toang: " + e.getMessage());
            return false;
        } catch (StorageException e) {
            // update videoProperty.error
            // System.out.println("Upload video toang: " + e.getMessage());
            return false;
        }
    }

    private void insertDatabase(VideoProperty videoProperty, List<String> videoErrorNameList, EventCreator eventCreator){
        try {
            if (DETECT_HOT_SPOT == videoProperty.getTypeVideo()) {
                videoDAO.insertHotSpot(videoProperty);
            }
            if (DETECT_EMOTION == videoProperty.getTypeVideo()) {
                videoDAO.insertEmotion(videoProperty);
            }
            videoDAO.insertVideoProperty(videoProperty);
        } catch (PersistenceException e){
            logger.error("Error at CustomEventListener: " + e.getMessage());
        }
    }

    private void updateEvent(EventCreator eventCreator, int totalFile, int numberFileDone, List<String> fileSuccess, List<String> fileError){
        eventCreator.setTotalFile(totalFile);
        eventCreator.setNumberFileDone(numberFileDone);
        eventCreator.setFileSuccess(fileSuccess);
        eventCreator.setFileError(fileError);
        eventCreatorMap.put(eventCreator.getEventId(),eventCreator);
    }

}
