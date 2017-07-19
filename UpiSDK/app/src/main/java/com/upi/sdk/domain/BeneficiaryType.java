package com.upi.sdk.domain;

/**
 * Created by NeeloyG on 21-10-2016.
 */
public enum  BeneficiaryType {

    VPA,
    AADHAR,
    ACCOUNT_IFSC;

    public String value() {
        return name();
    }

    public static BeneficiaryType fromValue(String v) {
        return valueOf(v);
    }


}
