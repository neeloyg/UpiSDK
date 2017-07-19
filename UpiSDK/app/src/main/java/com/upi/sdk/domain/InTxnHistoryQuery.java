package com.upi.sdk.domain;

import java.util.Date;

/**
 * Created by SwapanP on 22-04-2016.
 */
public class InTxnHistoryQuery {

    private String customerRefId;
    private String fromDate;
    private String toDate;
   // private int resultPerPage;

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }



    public String getCustomerRefId() {
        return customerRefId;
    }

    public void setCustomerRefId(String customerRefId) {
        this.customerRefId = customerRefId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }


//    public int getResultPerPage() {
//        return resultPerPage;
//    }
//
//    public void setResultPerPage(int resultPerPage) {
//        this.resultPerPage = resultPerPage;
//    }
}
