package com.screenname.form;

import com.common.form.ResponseGetBaseForm;
import com.screenname.dto.AccountDTO;
import com.screenname.dto.CreateAccountDTO;
import com.screenname.dto.GetAccountDTO;

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