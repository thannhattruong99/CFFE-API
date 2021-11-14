package com.screens.video.dao;

import com.common.dao.BaseDAO;
import com.screens.file.dto.VideoProperty;
import com.screens.video.dto.VideoDTO;
import com.screens.video.form.ResponseCountVideosForm;
import com.screens.video.form.ResponseEmotionVideosForm;
import com.util.IDBHelper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;

@Repository
public class VideoDAO extends BaseDAO {

    public VideoDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseCountVideosForm getVideoCountList(VideoDTO videoDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.video.dao.VidDAO.getVideoCountList",videoDTO);
        }finally {
            commit();
        }
    }

    public ResponseEmotionVideosForm getVideoEmotionList(VideoDTO videoDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.video.dao.VidDAO.getVideoEmotionList",videoDTO);
        }finally {
            commit();
        }
    }

    public String getShelfCameraMappingId(VideoProperty videoProperty) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.video.dao.VidDAO.getShelfCameraMappingId",videoProperty);
        }finally {
            commit();
        }
    }

    public String getStackProductCameraMappingId(VideoProperty videoProperty) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.video.dao.VidDAO.getStackProductCameraMappingId",videoProperty);
        }finally {
            commit();
        }
    }

    public boolean insertVideoProperty(VideoProperty videoProperty) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.insert("com.screens.video.dao.VidDAO.insertVideoProperty",videoProperty) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public boolean insertHotSpot(VideoProperty videoProperty) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.insert("com.screens.video.dao.VidDAO.insertHotSpot",videoProperty) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public boolean insertEmotion(VideoProperty videoProperty) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.insert("com.screens.video.dao.VidDAO.insertEmotion",videoProperty) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public boolean isDuplicateVideoShelf(VideoProperty videoProperty) {
        try{
            getSqlSession();
            int count = sqlSession.selectOne("com.screens.video.dao.VidDAO.countVideoByMacAndTimeType1",videoProperty);
            if (count > 0) {
                return true;
            }
            return false;
        }finally {
            commit();
        }
    }

    public boolean isDuplicateVideoStack(VideoProperty videoProperty) {
        try{
            getSqlSession();
            int count = sqlSession.selectOne("com.screens.video.dao.VidDAO.countVideoByMacAndTimeType2",videoProperty);
            if (count > 0) {
                return true;
            }
            return false;
        }finally {
            commit();
        }
    }

}
