package com.screens.video.dao;

import com.common.dao.BaseDAO;
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

}
