package com.upi.sdk.domain;

/**
 * Created by Jayanta on 24-04-2016.
 */
public class InListLinkAccountQueryByMobile {

    private int resultPerPage;
    private String referanceId;
    private String ifsc;

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    private String mobile_number;

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }


    public String getReferanceId() {
        return referanceId;
    }

    public void setReferanceId(String referanceId) {
        this.referanceId = referanceId;
    }


    public int getResultPerPage() {
        return resultPerPage;
    }

    public void setResultPerPage(int resultPerPage) {
        this.resultPerPage = resultPerPage;
    }
}
