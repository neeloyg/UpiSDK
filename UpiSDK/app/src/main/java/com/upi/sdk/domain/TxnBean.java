package com.upi.sdk.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by SwapanP on 22-04-2016.
 */
public class TxnBean {

    protected String txnId;
    protected String upiTxnId;
    protected String txnType;
    protected BigDecimal txnTotalAmt;
    protected String txnStatus;
    protected String note;
    protected BigInteger linkAccId;
    protected String initiatedBy;
    protected String creationTs;

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getUpiTxnId() {
        return upiTxnId;
    }

    public void setUpiTxnId(String upiTxnId) {
        this.upiTxnId = upiTxnId;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public BigDecimal getTxnTotalAmt() {
        return txnTotalAmt;
    }

    public void setTxnTotalAmt(BigDecimal txnTotalAmt) {
        this.txnTotalAmt = txnTotalAmt;
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigInteger getLinkAccId() {
        return linkAccId;
    }

    public void setLinkAccId(BigInteger linkAccId) {
        this.linkAccId = linkAccId;
    }

    public String getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(String initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    public String getCreationTs() {
        return creationTs;
    }

    public void setCreationTs(String creationTs) {
        this.creationTs = creationTs;
    }
}
