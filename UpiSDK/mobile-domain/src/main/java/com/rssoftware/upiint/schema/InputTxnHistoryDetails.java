package com.rssoftware.upiint.schema;

/**
 * Created by NeeloyG on 20-09-2016.
 */
public class InputTxnHistoryDetails {
    protected String txnId = "";

    protected String ownerId = "";

    protected String refId;

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }


}
