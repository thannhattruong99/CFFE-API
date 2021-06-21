package com.screens.stack.dao;

import com.common.dao.BaseDAO;
import com.screens.stack.dto.StackDTO;
import com.screens.stack.form.ResponseStackDetailForm;
import com.screens.stack.form.ResponseStackListForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class StackDAO extends BaseDAO {

    public StackDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseStackDetailForm getStackDetail(StackDTO stackDTO) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackDetail",stackDTO);
        }finally {
            closeConnection();
        }
    }

    public ResponseStackDetailForm getStackStatus(StackDTO stackDTO) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackStatus",stackDTO);
        }finally {
            closeConnection();
        }
    }

    public ResponseStackListForm getStackListByShelf(StackDTO stackDTO) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackListByShelf",stackDTO);
        }finally {
            closeConnection();
        }
    }

    public ResponseStackListForm getStackListByProductIdStoreId(StackDTO stackDTO) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackListByProductIdStoreId",stackDTO);
        }finally {
            closeConnection();
        }
    }

    public boolean addProduct(StackDTO stackDTO) {
        try{
            openConnection();
            if(sqlSession.insert("com.screens.stack.dao.sql.StackDAO.addProduct",stackDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean addCamera(StackDTO stackDTO) {
        try{
            openConnection();
            if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.activeStack",stackDTO) > 0) {
                if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.activeCamera",stackDTO) > 0){
                    if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.activeMapping",stackDTO) > 0){
                        this.sqlSession.commit();
                        return true;
                    }
                }
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean removeProduct(StackDTO stackDTO) {
        try{
            openConnection();
            if(sqlSession.insert("com.screens.stack.dao.sql.StackDAO.removeProduct",stackDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean removeCamera(StackDTO stackDTO) {
        try{
            openConnection();
            if(sqlSession.insert("com.screens.stack.dao.sql.StackDAO.addNewRecordMapping",stackDTO) > 0){
                if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.updateCameraPending",stackDTO) > 0){
                    if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.updateStackPending",stackDTO) > 0){
                        if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.removeCamera",stackDTO) > 0){
                            this.sqlSession.commit();
                            return true;
                        }
                    }

                }
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean changeStatus(StackDTO stackDTO) {
        try{
            openConnection();
            if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.changeStatus",stackDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean checkProductActive(StackDTO stackDTO) {
        try{
            openConnection();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkProductActive",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public boolean checkStackPending(StackDTO stackDTO) {
        try{
            openConnection();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackPending",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public boolean checkCameraPending(StackDTO stackDTO) {
        try{
            openConnection();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkCameraPending",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public boolean checkStackHaveProduct(StackDTO stackDTO) {
        try {
            openConnection();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackHaveProduct",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public boolean stackIsExistInStore(StackDTO stackDTO) {
        try {
            openConnection();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackExist",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public boolean shelfIsExistInStore(StackDTO stackDTO) {
        try{
            openConnection();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.shelfIsExistInStore",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public boolean checkCameraExist(StackDTO stackDTO) {
        try{
            openConnection();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkCameraExist",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public boolean checkProductExist(StackDTO stackDTO) {
        try{
            openConnection();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkProductExist",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public boolean checkStackProductMapping(StackDTO stackDTO) {
        try {
            openConnection();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackProductMapping",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public boolean checkStackCameraMapping(StackDTO stackDTO) {
        try{
            openConnection();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackCameraMapping",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }
}
