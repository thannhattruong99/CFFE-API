package com.screens.store.form;

import com.common.form.ResponseGetBaseForm;

import java.util.List;

public class ResponseStoreListForm extends ResponseGetBaseForm {
    private List<StoreResponseSupporter> stores;

    public ResponseStoreListForm() {
    }

    public List<StoreResponseSupporter> getStores() {
        return stores;
    }

    public void setStores(List<StoreResponseSupporter> stores) {
        this.stores = stores;
    }
}
