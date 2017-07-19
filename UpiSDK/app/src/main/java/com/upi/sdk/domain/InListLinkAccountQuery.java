package com.upi.sdk.domain;

import java.util.Date;

/**
 * Created by Jayanta on 24-04-2016.
 */
public class InListLinkAccountQuery {

    private String customerRefId;
    private boolean fetchActiveVPAOnly = false;
    private int resultPerPage;

    public String getCustomerRefId() {
        return customerRefId;
    }

    public void setCustomerRefId(String customerRefId) {
        this.customerRefId = customerRefId;
    }

    public boolean getFetchActiveVPAOnly() { return fetchActiveVPAOnly;   }

    public void setFetchActiveVPAOnly(boolean fetchActiveVPAOnly) { this.fetchActiveVPAOnly = fetchActiveVPAOnly; }

    public int getResultPerPage() {
        return resultPerPage;
    }

    public void setResultPerPage(int resultPerPage) {
        this.resultPerPage = resultPerPage;
    }
}
