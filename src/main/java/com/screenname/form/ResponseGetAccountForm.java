package com.screenname.form;

import com.common.form.ResponseGetBaseForm;
import com.screenname.dto.CreateAccountDTO;
import com.screenname.dto.GetAccountDTO;

import java.io.Serializable;
import java.util.List;

public class ResponseGetAccountForm extends ResponseGetBaseForm implements Serializable {
    private List<CreateAccountDTO> accountLst;

    public ResponseGetAccountForm() {
    }

    public List<CreateAccountDTO> getAccountLst() {
        return accountLst;
    }

    public void setAccountLst(List<CreateAccountDTO> accountLst) {
        this.accountLst = accountLst;
    }
}