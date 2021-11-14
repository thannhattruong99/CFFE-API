package com.screens.shelf.dao;

import com.common.dao.BaseDAO;
import com.screens.shelf.dto.ShelfDTO;
import com.screens.shelf.dto.StackDTO;
import com.screens.shelf.form.ResponseShelfDetailForm;
import com.screens.shelf.form.ResponseShelfListForm;
import com.screens.shelf.form.ResponseShelvesByStoreId;
import com.util.IDBHelper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfDAO extends BaseDAO {

    public ShelfDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseShelfListForm getShelfList(ShelfDTO shelfDTO){
        try{
            getSqlSession();
            return sqlSession.selectOne("ShelfDAO.getShelves", shelfDTO);
        }finally {
            commit();
        }
    }

    public ResponseShelvesByStoreId getShelveByStoreId(ShelfDTO shelfDTO){
        try{
            getSqlSession();
            return sqlSession.selectOne("ShelfDAO.getShelveByStoreId", shelfDTO);
        }finally {
            commit();
        }
    }

    public ResponseShelfDetailForm getShelfDetail(ShelfDTO shelfDTO){
        try{
            getSqlSession();
            return sqlSession.selectOne("ShelfDAO.getShelfDetail", shelfDTO);
        }finally {
            commit();
        }
    }

    public boolean createShelf(ShelfDTO shelfDTO) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.insert("ShelfDAO.createShelf", shelfDTO) > 0){
                for (StackDTO stack: shelfDTO.getStacks()) {
                    stack.setShelfId(shelfDTO.getShelfId());
                }
                if(sqlSession.insert("ShelfDAO.createStacks", shelfDTO.getStacks()) == shelfDTO.getStacks().size()){
                    return true;
                }
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public boolean updateShelf(ShelfDTO shelfDTO) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.update("ShelfDAO.updateShelf", shelfDTO) > 0){
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

    public ShelfDTO getStatusId(ShelfDTO shelfDTO){
        try {
            getSqlSession();
            return sqlSession.selectOne("ShelfDAO.getStatusId", shelfDTO);
        }finally {
            commit();
        }
    }

    public boolean updateShelfStatus(ShelfDTO shelfDTO) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.update("ShelfDAO.updateShelfStatus", shelfDTO) > 0){
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

    public ShelfDTO getShelfStatus(ShelfDTO shelfDTO){
        try{
            getSqlSession();
            return sqlSession.selectOne("ShelfDAO.getShelfStatus", shelfDTO);
        }finally {
            commit();
        }
    }

    public ShelfDTO getCameraStatus(ShelfDTO shelfDTO){
        try {
            getSqlSession();
            return sqlSession.selectOne("ShelfDAO.getCameraStatus", shelfDTO);
        }finally {
            commit();
        }

    }

    public ShelfDTO getShelfCameraMappingStatus(ShelfDTO shelfDTO){
        try{
            getSqlSession();
            return sqlSession.selectOne("ShelfDAO.getShelfCameraMappingStatus", shelfDTO);
        }finally {
            commit();
        }

    }

    public boolean addShelfCameraIntoShelf(ShelfDTO shelfDTO) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.insert("ShelfDAO.createShelfCameraOnMapping", shelfDTO) > 0){
                if(sqlSession.update("ShelfDAO.createShelfCameraOnCamera", shelfDTO) > 0){
                    if(sqlSession.update("ShelfDAO.createShelfCameraOnShelf", shelfDTO) > 0){
                        return true;
                    }
                }
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public int countCameraByShelfId(ShelfDTO shelfDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("ShelfDAO.countCameraByShelfId", shelfDTO);
        }finally {
            commit();
        }
    }

    public boolean removeShelfCameraFromShelf(ShelfDTO shelfDTO) throws PersistenceException{
        try{
            getSqlSession();
            if (sqlSession.update("ShelfDAO.removeShelfCameraFromMapping", shelfDTO) > 0){
                if (sqlSession.update("ShelfDAO.removeShelfCameraFromCamera", shelfDTO) > 0){
                    this.sqlSession.commit();
                    int count = sqlSession.selectOne("ShelfDAO.countCameraByShelfId", shelfDTO);
                    shelfDTO.setStatusId(1);
                    if(count == 0){
                        shelfDTO.setStatusId(3);
                    }
                    sqlSession.update("ShelfDAO.removeShelfCameraFromShelf", shelfDTO);
                    return true;
                }
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }
}
