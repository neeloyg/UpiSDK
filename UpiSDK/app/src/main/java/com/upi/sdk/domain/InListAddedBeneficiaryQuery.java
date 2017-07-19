package com.upi.sdk.domain;

/**
 * Created by NeeloyG on 5/19/2016.
 */
public class InListAddedBeneficiaryQuery {

    private String customerRefId;
    private int resultPerPage;

    public String getCustomerRefId() {
        return customerRefId;
    }

    public void setCustomerRefId(String customerRefId) {
        this.customerRefId = customerRefId;
    }



    public int getResultPerPage() {
        return resultPerPage;
    }

    public void setResultPerPage(int resultPerPage) {
        this.resultPerPage = resultPerPage;
    }
}
