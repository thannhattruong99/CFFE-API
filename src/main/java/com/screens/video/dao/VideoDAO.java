package com.screens.video.dao;

import com.common.dao.BaseDAO;
import com.screens.file.dto.VideoProperty;
import com.screens.video.dto.VideoDTO;
import com.screens.video.form.ResponseCountVideosForm;
import com.screens.video.form.ResponseEmotionVideosForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class VideoDAO extends BaseDAO {

    public VideoDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseCountVideosForm getVideoCountList(VideoDTO videoDTO) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.video.dao.VidDAO.getVideoCountList",videoDTO);
        }finally {
            closeConnection();
        }
    }

    public ResponseEmotionVideosForm getVideoEmotionList(VideoDTO videoDTO) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.video.dao.VidDAO.getVideoEmotionList",videoDTO);
        }finally {
            closeConnection();
        }
    }

    public String getShelfCameraMappingId(VideoProperty videoProperty) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.video.dao.VidDAO.getShelfCameraMappingId",videoProperty);
        }finally {
            closeConnection();
        }
    }

    public String getStackProductCameraMappingId(VideoProperty videoProperty) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.video.dao.VidDAO.getStackProductCameraMappingId",videoProperty);
        }finally {
            closeConnection();
        }
    }

    public boolean insertVideoProperty(VideoProperty videoProperty) {
        try{
            openConnection();
            if(sqlSession.insert("com.screens.video.dao.VidDAO.insertVideoProperty",videoProperty) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean insertHotSpot(VideoProperty videoProperty) {
        try{
            openConnection();
            if(sqlSession.insert("com.screens.video.dao.VidDAO.insertHotSpot",videoProperty) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean insertEmotion(VideoProperty videoProperty) {
        try{
            openConnection();
            if(sqlSession.insert("com.screens.video.dao.VidDAO.insertEmotion",videoProperty) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean isDuplicate(VideoProperty videoProperty) {
        try{
            openConnection();
            int count = sqlSession.selectOne("com.screens.video.dao.VidDAO.countByCameraIdAndTime",videoProperty);
            if (count > 0) {
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

}
