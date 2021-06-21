package com.screens.shelf.dao;

import com.common.dao.BaseDAO;
import com.screens.shelf.dto.ShelfDTO;
import com.screens.shelf.dto.StackDTO;
import com.screens.shelf.form.ResponseShelfDetailForm;
import com.screens.shelf.form.ResponseShelfListForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfDAO extends BaseDAO {

    public ShelfDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseShelfListForm getShelfList(ShelfDTO shelfDTO){
        try{
            openConnection();
            return sqlSession.selectOne("ShelfDAO.getShelves", shelfDTO);
        }finally {
            closeConnection();
        }
    }

    public ResponseShelfDetailForm getShelfDetail(ShelfDTO shelfDTO){
        try{
            openConnection();
            return sqlSession.selectOne("ShelfDAO.getShelfDetail", shelfDTO);
        }finally {
            closeConnection();
        }
    }

    public boolean createShelf(ShelfDTO shelfDTO){
        try{
            openConnection();
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
            closeConnection();
        }
    }

    public boolean updateShelf(ShelfDTO shelfDTO){
        try{
            openConnection();
            if(sqlSession.update("ShelfDAO.updateShelf", shelfDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public ShelfDTO getStatusId(ShelfDTO shelfDTO){
        try {
            openConnection();
            return sqlSession.selectOne("ShelfDAO.getStatusId", shelfDTO);
        }finally {
            closeConnection();
        }
    }

    public boolean updateShelfStatus(ShelfDTO shelfDTO){
        try{
            openConnection();
            if(sqlSession.update("ShelfDAO.updateShelfStatus", shelfDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public ShelfDTO getShelfStatus(ShelfDTO shelfDTO){
        try{
            openConnection();
            return sqlSession.selectOne("ShelfDAO.getShelfStatus", shelfDTO);
        }finally {
            closeConnection();
        }
    }

    public ShelfDTO getCameraStatus(ShelfDTO shelfDTO){
        try {
            openConnection();
            return sqlSession.selectOne("ShelfDAO.getCameraStatus", shelfDTO);
        }finally {
            closeConnection();
        }

    }

    public ShelfDTO getShelfCameraMappingStatus(ShelfDTO shelfDTO){
        try{
            openConnection();
            return sqlSession.selectOne("ShelfDAO.getShelfCameraMappingStatus", shelfDTO);
        }finally {
            closeConnection();
        }

    }

    public boolean addShelfCameraIntoShelf(ShelfDTO shelfDTO){
        try{
            openConnection();
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
            closeConnection();
        }
    }

    public boolean removeShelfCameraFromShelf(ShelfDTO shelfDTO){
        try{
            openConnection();
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
            closeConnection();
        }
    }
}
