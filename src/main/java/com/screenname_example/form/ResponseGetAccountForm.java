package com.screenname_example.form;

import com.common.form.ResponseGetBaseForm;
import com.screenname_example.dto.AccountDTO;

import java.io.Serializable;
import java.util.List;

public class ResponseGetAccountForm extends ResponseGetBaseForm implements Serializable {
    private List<AccountDTO> accountLst;

    public ResponseGetAccountForm() {
    }

    public List<AccountDTO> getAccountLst() {
        return accountLst;
    }

    public void setAccountLst(List<AccountDTO> accountLst) {
        this.accountLst = accountLst;
    }
}