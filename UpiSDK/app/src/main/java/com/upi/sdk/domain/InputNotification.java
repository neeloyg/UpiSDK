package com.upi.sdk.domain;

/**
 * Created by Neeloy on 04-01-2017.
 */
public class InputNotification {

    private String userId;
    private long fromDate;
    private long toDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getFromDate() {
        return fromDate;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public long getToDate() {
        return toDate;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }


}
