package com.upi.sdk.domain;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Jayanta on 25-04-2016.
 */
public class InputDeleteVPA {

    private String customerRefId;
    private BigInteger VPAID;
    private String vpa;

    public String getVpa() {
        return vpa;
    }

    public void setVpa(String vpa) {
        this.vpa = vpa;
    }

    public String getCustomerRefId() {
        return customerRefId;
    }

    public void setCustomerRefId(String customerRefId) {
        this.customerRefId = customerRefId;
    }

    public BigInteger getVPAID() {
        return VPAID;
    }

    public void setVPAID(BigInteger VPAID) {
        this.VPAID = VPAID;
    }
}
