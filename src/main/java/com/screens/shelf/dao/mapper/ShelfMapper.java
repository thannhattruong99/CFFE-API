package com.screens.shelf.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.shelf.dto.ShelfDTO;
import com.screens.shelf.dto.StackDTO;
import com.screens.shelf.form.ResponseShelfDetailForm;
import com.screens.shelf.form.ResponseShelfListForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfMapper extends BaseDAO {

    public ShelfMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseShelfListForm getShelfList(ShelfDTO shelfDTO){
        return sqlSession.selectOne("ShelfDAO.getShelves", shelfDTO);
    }

    public ResponseShelfDetailForm getShelfDetail(ShelfDTO shelfDTO){
        return sqlSession.selectOne("ShelfDAO.getShelfDetail", shelfDTO);
    }

    public boolean createShelf(ShelfDTO shelfDTO){

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
    }

    public boolean updateShelf(ShelfDTO shelfDTO){
        if(sqlSession.update("ShelfDAO.updateShelf", shelfDTO) > 0){
            sqlSession.commit(true);
            return true;
        }
        return false;
    }

    public ShelfDTO getStatusId(ShelfDTO shelfDTO){
        return sqlSession.selectOne("ShelfDAO.getStatusId", shelfDTO);
    }

    public boolean updateShelfStatus(ShelfDTO shelfDTO){
        if(sqlSession.update("ShelfDAO.updateShelfStatus", shelfDTO) > 0){
            sqlSession.commit(true);
            return true;
        }
        return false;
    }

    public ShelfDTO getShelfStatus(ShelfDTO shelfDTO){
        ShelfDTO result = sqlSession.selectOne("ShelfDAO.getShelfStatus");
        return result;
    }

    public ShelfDTO getCameraStatus(ShelfDTO shelfDTO){
        ShelfDTO result = sqlSession.selectOne("ShelfDAO.getCameraStatus");
        return result;
    }

    public ShelfDTO getShelfCameraMappingStatus(ShelfDTO shelfDTO){
        ShelfDTO result = sqlSession.selectOne("ShelfDAO.getShelfCameraMappingStatus");
        return result;
    }

    public boolean addShelfCameraIntoShelf(ShelfDTO shelfDTO){
        if(sqlSession.insert("ShelfDAO.createShelfCameraOnMapping", shelfDTO) > 0){
            if(sqlSession.update("ShelfDAO.createShelfCameraOnCamera", shelfDTO) > 0){
                if(sqlSession.update("ShelfDAO.createShelfCameraOnShelf", shelfDTO) > 0){
                    sqlSession.commit(true);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeShelfCameraFromShelf(ShelfDTO shelfDTO){
        if (sqlSession.update("ShelfDAO.removeShelfCameraFromMapping", shelfDTO) > 0){
            if (sqlSession.update("ShelfDAO.removeShelfCameraFromCamera", shelfDTO) > 0){
                if(sqlSession.update("ShelfDAO.removeShelfCameraFromShelf") > 0){
                    sqlSession.commit(true);
                    return true;
                }
            }
        }

        return false;
    }
}
