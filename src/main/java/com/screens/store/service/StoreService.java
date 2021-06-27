package com.screens.store.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.filter.dto.AuthorDTO;
import com.screens.store.dao.StoreDAO;
import com.screens.store.dto.StoreDTO;
import com.screens.store.form.*;
import com.util.MessageConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private StoreDAO storeDAO;

    public ResponseStoreListForm getStoreList(RequestGetStoreListForm requestGetStoreListForm,AuthorDTO authorDTO){
        ResponseStoreListForm responseStoreListForm = null;
        int authorStatus = checkAuthor(authorDTO);
        if (authorStatus == ADMIN) {
            StoreDTO storeDTO = convertGetStoreListFormToDTO(requestGetStoreListForm);
            try {
                responseStoreListForm = storeDAO.getStoreList(storeDTO);
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
            }
        }
        return responseStoreListForm;
    }

    public ResponseStoreListForm getStoreListByProduct(RequestGetStoreListByProductForm requestForm,AuthorDTO authorDTO){
        ResponseStoreListForm responseStoreListForm = null;
        int authorStatus = checkAuthor(authorDTO);
        if (authorStatus == ADMIN) {
            StoreDTO storeDTO = convertGetStoreListByProductFormToDTO(requestForm);
            try {
                responseStoreListForm = storeDAO.getStoreListByProduct(storeDTO);
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
            }
        }
        return responseStoreListForm;
    }

    public ResponseStoreListForm getStoreListShort(RequestGetStoreListShort requestForm,AuthorDTO authorDTO){
        ResponseStoreListForm responseStoreListForm = null;
        int authorStatus = checkAuthor(authorDTO);
        if (authorStatus == ADMIN) {
            StoreDTO storeDTO = new StoreDTO();
            try {
                responseStoreListForm = storeDAO.getStoreListShort(storeDTO);
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
            }
        }
        return responseStoreListForm;
    }

    public ResponseStoreDetailForm getStoreDetail(RequestGetStoreDetailForm requestForm, AuthorDTO authorDTO) {
        ResponseStoreDetailForm responseStoreDetailForm = null;

            StoreDTO storeDTO = convertGetStoreDetailFormToDTO(requestForm,authorDTO);
            try {
                responseStoreDetailForm = storeDAO.getStoreDetail(storeDTO);
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
            }

        return responseStoreDetailForm;
    }

    public ResponseCommonForm createStore(RequestCreateStoreForm requestForm, AuthorDTO authorDTO) {
        ResponseCommonForm response = new ResponseCommonForm();
        int statusAuthor = checkAuthor(authorDTO);
        if (statusAuthor == ADMIN) {
            StoreDTO storeDTO = convertCreateStoreFormToDTO(requestForm);
            try {
                storeDAO.createStore(storeDTO);
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
                response.setErrorCodes(catchSqlException(e.getMessage()));
            }
        } else {
            addErrorMessage(response, MessageConstant.MSG_076);
        }
        return response;
    }

    public ResponseCommonForm changeStatus(RequestChangeStoreStatusForm requestForm, AuthorDTO authorDTO) {
        ResponseCommonForm response = new ResponseCommonForm();
        int statusAuthor = checkAuthor(authorDTO);
        if (statusAuthor == ADMIN) {
            StoreDTO storeDTO = convertChangeStatusFormToDTO(requestForm);
            try {
                ResponseStoreDetailForm responseStoreDetailForm = storeDAO.getStoreStatus(storeDTO);
                if ((responseStoreDetailForm.getStatusId() == 3) && (storeDTO.getStatusId() == 2)
                        && (StringUtils.isNotEmpty(storeDTO.getReasonInactive()))) {
                    System.out.println("ACTION: STORE PENDING => INACTIVE");
                    //check co ton tai shelf nao ko inactive hay khong
                    if (storeDAO.checkShelf(storeDTO)) {
                        storeDAO.changeStatus(storeDTO);
                    } else {
                        addErrorMessage(response,MessageConstant.MSG_081);
                    }
                } else if ((responseStoreDetailForm.getStatusId() == 2) && (storeDTO.getStatusId() == 3)) {
                    System.out.println("ACTION: STORE INACTIVE => PENDING");
                    storeDAO.changeStatus(storeDTO);
                } else {
                    addErrorMessage(response,MessageConstant.MSG_066);
                }
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
                response.setErrorCodes(catchSqlException(e.getMessage()));
            }
        } else {
            addErrorMessage(response,MessageConstant.MSG_076);
        }
        return response;
    }

    public ResponseCommonForm updateStoreInfo(RequestUpdateInfoForm requestForm, AuthorDTO authorDTO) {
        ResponseCommonForm response = new ResponseCommonForm();
        int statusAuthor = checkAuthor(authorDTO);
        if (statusAuthor == ADMIN) {
            StoreDTO storeDTO = convertUpdateInfoFormToDTO(requestForm);
            try {
                storeDAO.updateInfo(storeDTO);
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
                response.setErrorCodes(catchSqlException(e.getMessage()));
            }
        } else {
            addErrorMessage(response,MessageConstant.MSG_076);
        }

        return response;
    }

    public ResponseCommonForm changeManager(RequestChangeManager requestForm, AuthorDTO authorDTO) {
        ResponseCommonForm response = new ResponseCommonForm();
        int statusAuthor = checkAuthor(authorDTO);
        if (statusAuthor == ADMIN) {
            StoreDTO storeDTO = convertChangeManagerFormToDTO(requestForm);
            List<String> errorMsg = new ArrayList<>();
            try {
                // Add manager
                if (requestForm.getActive() == ADD_ACTION) {
                    // check 2 thang ton tai va pending
                    if (!storeDAO.checkAvailableStore(storeDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_075);
                    }
                    else if (!storeDAO.checkAvailableManager(storeDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_074);
                    } else {
                        // do add manager
                        storeDAO.addManager(storeDTO);
                    }
                }
                // Remove manager
                if (requestForm.getActive() == REMOVE_ACTION) {
                    // check 2 thang co ton tai ko
                    if (!storeDAO.countStoreById(storeDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_035);
                    }
                    else if (!storeDAO.countUserById(storeDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_041);
                    }
                    // check 2 thang co mapping voi nhau ko
                    else if (!storeDAO.checkStoreManagerMapping(storeDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_077);
                    } else {
                        // do remove manager
                        storeDAO.removeManager(storeDTO);
                    }
                }
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
                response.setErrorCodes(catchSqlException(e.getMessage()));
            }
        } else {
            addErrorMessage(response,MessageConstant.MSG_076);
        }


        return response;
    }

    private StoreDTO convertChangeManagerFormToDTO(RequestChangeManager requestForm){
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(requestForm.getStoreId());
        if (requestForm.getActive() == ADD_ACTION) {
            storeDTO.setStatusId(ACTIVE_STATUS);
        }
        if (requestForm.getActive() == REMOVE_ACTION) {
            storeDTO.setStatusId(PENDING_STATUS);
        }
        storeDTO.setUserId(requestForm.getUserId());
        return storeDTO;
    }

    private StoreDTO convertChangeStatusFormToDTO(RequestChangeStoreStatusForm requestForm){
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(requestForm.getStoreId());
        storeDTO.setStatusId(requestForm.getStatusId());
        if (StringUtils.isNotEmpty(requestForm.getReasonInactive())) {
            storeDTO.setReasonInactive(requestForm.getReasonInactive());
        }
        return storeDTO;
    }

    private StoreDTO convertUpdateInfoFormToDTO(RequestUpdateInfoForm requestForm) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(requestForm.getStoreId());
        if (StringUtils.isNotEmpty(requestForm.getStoreName()))
            storeDTO.setStoreName(requestForm.getStoreName());
        if (StringUtils.isNotEmpty(requestForm.getImageUrl()))
            storeDTO.setImageUrl(requestForm.getImageUrl());
        if (StringUtils.isNotEmpty(requestForm.getAddress()))
            storeDTO.setAddress(requestForm.getAddress());
        if (requestForm.getDistrictId() != 0) {
            storeDTO.setDistrictId(requestForm.getDistrictId());
        }
        return storeDTO;
    }

    private StoreDTO convertCreateStoreFormToDTO(RequestCreateStoreForm requestForm) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreName(requestForm.getStoreName());
        if (StringUtils.isNotEmpty(requestForm.getImageUrl())){
            storeDTO.setImageUrl(requestForm.getImageUrl());
        } else {
            storeDTO.setImageUrl(DEFAULT_IMAGE);
        }
        storeDTO.setAddress(requestForm.getAddress());
        storeDTO.setDistrictId(requestForm.getDistrictId());
        storeDTO.setStatusId(PENDING_STATUS);
        return storeDTO;
    }

    private StoreDTO convertGetStoreDetailFormToDTO(RequestGetStoreDetailForm requestForm, AuthorDTO authorDTO) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(requestForm.getStoreId());
        if (authorDTO != null) {
            storeDTO.setStoreId(authorDTO.getStoreId());
        }
        return storeDTO;
    }

    private StoreDTO convertGetStoreListFormToDTO(RequestGetStoreListForm requestGetStoreListForm) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setSearchValue(requestGetStoreListForm.getSearchValue().toLowerCase().trim());
        storeDTO.setSearchField(requestGetStoreListForm.getSearchField().toLowerCase().trim());
        storeDTO.setStatusId(requestGetStoreListForm.getStatusId());
        if(requestGetStoreListForm.getPageNum() > 0){
            storeDTO.setOffSet((requestGetStoreListForm.getPageNum() - 1) * requestGetStoreListForm.getFetchNext());
        }
        storeDTO.setCityId(requestGetStoreListForm.getCityId());
        storeDTO.setFetchNext(requestGetStoreListForm.getFetchNext());
        if(requestGetStoreListForm.getFetchNext() <= 0){
            storeDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        }
        return storeDTO;
    }

    private StoreDTO convertGetStoreListByProductFormToDTO(RequestGetStoreListByProductForm requestForm) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setSearchValue(requestForm.getSearchValue().toLowerCase().trim());
        storeDTO.setSearchField(requestForm.getSearchField().toLowerCase().trim());
        storeDTO.setStatusId(requestForm.getStatusId());
        if(requestForm.getPageNum() > 0){
            storeDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }
        storeDTO.setProductId(requestForm.getProductId());
        storeDTO.setFetchNext(requestForm.getFetchNext());
        if(requestForm.getFetchNext() <= 0){
            storeDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        }
        return storeDTO;
    }

}
