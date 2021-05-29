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

}
