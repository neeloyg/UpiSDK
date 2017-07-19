package com.upi.sdk.domain;

/**
 * Created by SwapanP on 26-04-2016.
 */
public class OutPayRequest {

    protected String txnId;
    protected String status;

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
