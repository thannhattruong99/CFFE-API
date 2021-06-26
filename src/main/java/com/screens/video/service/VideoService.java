package com.screens.video.service;

import com.common.service.BaseService;
import com.filter.dto.AuthorDTO;
import com.screens.video.dao.VideoDAO;
import com.screens.video.dto.VideoDTO;
import com.screens.video.form.RequestGetVideoListForm;
import com.screens.video.form.ResponseGetVideoForm;
import com.util.MessageConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class VideoService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(VideoService.class);
    private static final String ALL_OPTION = "all";
    private static final String DAY_TIME_FORMAT = "yyyy-MM-dd";
    private static final String TIME_BEGIN_DAY = " 00:00:00";
    private static final String TIME_END_DAY = " 23:59:59";
    private static final int COUNT_TYPE_VIDEO = 1;
    private static final int EMOTION_TYPE_VIDEO = 2;

    @Autowired
    private VideoDAO videoDAO;

    public ResponseGetVideoForm getVideoList(RequestGetVideoListForm requestForm, AuthorDTO authorDTO){
        ResponseGetVideoForm response = new ResponseGetVideoForm();
        int authorStatus = checkAuthor(authorDTO);
        if (authorStatus == ADMIN || authorStatus == MANAGER_WITHIN_STORE) {
            VideoDTO videoDTO = convertGetVideoFormToDTO(requestForm,authorDTO);
            // check Day
            if (StringUtils.isNotEmpty(videoDTO.getDayEnd()) && StringUtils.isEmpty(videoDTO.getDayStart())) {
                response.setErrorCodes(getError(MessageConstant.MSG_115));
            }
            if ((StringUtils.isNotEmpty(videoDTO.getDayEnd())) && (StringUtils.isNotEmpty(videoDTO.getDayStart()))
            && (StringUtils.compare(videoDTO.getDayEnd(),videoDTO.getDayStart()) < 0) ) {
                response.setErrorCodes(getError(MessageConstant.MSG_116));
            }
            if (response.getErrorCodes() != null) {
                return response;
            }
            try {
                if (requestForm.getVideoType() == COUNT_TYPE_VIDEO) {
                    response.setVideoCount(videoDAO.getVideoCountList(videoDTO));
                }
                if (requestForm.getVideoType() == EMOTION_TYPE_VIDEO) {
                    if (StringUtils.isEmpty(videoDTO.getProductId())) {
                        response.setErrorCodes(getError(MessageConstant.MSG_096));
                    } else {
                        response.setVideoEmotion(videoDAO.getVideoEmotionList(videoDTO));
                    }
                }
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
            }
        }
        return response;
    }


    private VideoDTO convertGetVideoFormToDTO(RequestGetVideoListForm requestForm, AuthorDTO authorDTO) {
        VideoDTO videoDTO = new VideoDTO();
        if(requestForm.getPageNum() > 0){
            videoDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }
        videoDTO.setFetchNext(requestForm.getFetchNext());
        if(requestForm.getFetchNext() <= 0){
            videoDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        }
        //===
        videoDTO.setVideoType(requestForm.getVideoType());
        if (StringUtils.isNotEmpty(requestForm.getShelfId())) {
            if (requestForm.getShelfId().toLowerCase().equals(ALL_OPTION)){
                videoDTO.setShelfId(requestForm.getShelfId());
            }
        }
        if (StringUtils.isNotEmpty(requestForm.getDayStart())) {
            videoDTO.setDayStart(requestForm.getDayStart());
        }
        if (StringUtils.isNotEmpty(requestForm.getDayEnd())) {
            videoDTO.setDayEnd(requestForm.getDayEnd());
        }
        if (StringUtils.isNotEmpty(requestForm.getProductId())) {
            videoDTO.setProductId(requestForm.getProductId());
        }
        // Store = all => null
        // Manager quan ly 1 store => chi dc xem store do
        if (!requestForm.getStoreId().toLowerCase().equals(ALL_OPTION)){
            videoDTO.setStoreId(requestForm.getStoreId());
        }
        if (authorDTO != null){
            if (StringUtils.isNotEmpty(authorDTO.getStoreId())) {
                videoDTO.setStoreId(authorDTO.getStoreId());
            }
        }

        // nhap start ko end => thoi diem hien tai
        if (StringUtils.isNotEmpty(requestForm.getDayStart()) && StringUtils.isEmpty(requestForm.getDayEnd())) {
            videoDTO.setDayEnd(getTime(0)+TIME_END_DAY);
        }
        // start end null => lay 3 ngay ngan nhat
        if (StringUtils.isEmpty(requestForm.getDayStart()) && StringUtils.isEmpty(requestForm.getDayEnd())) {
            videoDTO.setDayStart(getTime(-3)+TIME_BEGIN_DAY);
            videoDTO.setDayEnd(getTime(0)+TIME_END_DAY);
        }
        return videoDTO;
    }

    private String getTime(int dayBefore) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, dayBefore);
        DateFormat dateFormat = new SimpleDateFormat(DAY_TIME_FORMAT);
        return dateFormat.format(cal.getTime());
    }

}
