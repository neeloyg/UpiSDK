package com.upi.sdk.domain;

import java.util.Date;

/**
 * Created by Jayanta on 24-04-2016.
 */
public class InputcheckVPAAvailability {

    private String customerRefId;
    private String userVPA;

    public String getCustomerRefId() {
        return customerRefId;
    }

    public void setCustomerRefId(String customerRefId) {
        this.customerRefId = customerRefId;
    }

    public String getUserVPA() {
        return userVPA;
    }

    public void setUserVPA(String userVPA) {
        this.userVPA = userVPA;
    }
}
