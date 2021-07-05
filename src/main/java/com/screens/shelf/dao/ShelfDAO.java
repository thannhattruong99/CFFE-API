package com.screens.shelf.dao;

import com.common.dao.BaseDAO;
import com.screens.shelf.dto.ShelfDTO;
import com.screens.shelf.dto.StackDTO;
import com.screens.shelf.form.ResponseShelfDetailForm;
import com.screens.shelf.form.ResponseShelfListForm;
import com.screens.shelf.form.ResponseShelvesByStoreId;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfDAO extends BaseDAO {

    public ShelfDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseShelfListForm getShelfList(ShelfDTO shelfDTO){
        try{
            openSession();
            return sqlSession.selectOne("ShelfDAO.getShelves", shelfDTO);
        }finally {
            closeSession();
        }
    }

    public ResponseShelvesByStoreId getShelveByStoreId(ShelfDTO shelfDTO){
        try{
            openSession();
            return sqlSession.selectOne("ShelfDAO.getShelveByStoreId", shelfDTO);
        }finally {
            closeSession();
        }
    }

    public ResponseShelfDetailForm getShelfDetail(ShelfDTO shelfDTO){
        try{
            openSession();
            return sqlSession.selectOne("ShelfDAO.getShelfDetail", shelfDTO);
        }finally {
            closeSession();
        }
    }

    public boolean createShelf(ShelfDTO shelfDTO){
        try{
            openSession();
            if(sqlSession.insert("ShelfDAO.createShelf", shelfDTO) > 0){

                //set ShelfId into list of stack
                for (StackDTO stack: shelfDTO.getStacks()) {
                    stack.setShelfId(shelfDTO.getShelfId());
                }

                if(sqlSession.insert("ShelfDAO.createStacks", shelfDTO.getStacks()) == shelfDTO.getStacks().size()){
                    sqlSession.commit(true);
                    return true;
                }
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public boolean updateShelf(ShelfDTO shelfDTO){
        try{
            openSession();
            if(sqlSession.update("ShelfDAO.updateShelf", shelfDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public ShelfDTO getStatusId(ShelfDTO shelfDTO){
        try {
            openSession();
            return sqlSession.selectOne("ShelfDAO.getStatusId", shelfDTO);
        }finally {
            closeSession();
        }
    }

    public boolean updateShelfStatus(ShelfDTO shelfDTO){
        try{
            openSession();
            if(sqlSession.update("ShelfDAO.updateShelfStatus", shelfDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public ShelfDTO getShelfStatus(ShelfDTO shelfDTO){
        try{
            openSession();
            return sqlSession.selectOne("ShelfDAO.getShelfStatus", shelfDTO);
        }finally {
            closeSession();
        }
    }

    public ShelfDTO getCameraStatus(ShelfDTO shelfDTO){
        try {
            openSession();
            return sqlSession.selectOne("ShelfDAO.getCameraStatus", shelfDTO);
        }finally {
            closeSession();
        }

    }

    public ShelfDTO getShelfCameraMappingStatus(ShelfDTO shelfDTO){
        try{
            openSession();
            return sqlSession.selectOne("ShelfDAO.getShelfCameraMappingStatus", shelfDTO);
        }finally {
            closeSession();
        }

    }

    public boolean addShelfCameraIntoShelf(ShelfDTO shelfDTO){
        try{
            openSession();
            if(sqlSession.insert("ShelfDAO.createShelfCameraOnMapping", shelfDTO) > 0){
                if(sqlSession.update("ShelfDAO.createShelfCameraOnCamera", shelfDTO) > 0){
                    if(sqlSession.update("ShelfDAO.createShelfCameraOnShelf", shelfDTO) > 0){
                        sqlSession.commit(true);
                        return true;
                    }
                }
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public boolean removeShelfCameraFromShelf(ShelfDTO shelfDTO){
        try{
            openSession();
            if (sqlSession.update("ShelfDAO.removeShelfCameraFromMapping", shelfDTO) > 0){
                if (sqlSession.update("ShelfDAO.removeShelfCameraFromCamera", shelfDTO) > 0){
                    if(sqlSession.update("ShelfDAO.removeShelfCameraFromShelf", shelfDTO) > 0){
                        sqlSession.commit(true);
                        return true;
                    }
                }
            }
            return false;
        }finally {
            closeSession();
        }
    }
}
