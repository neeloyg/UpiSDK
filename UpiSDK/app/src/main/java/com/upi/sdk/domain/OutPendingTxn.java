package com.upi.sdk.domain;

import java.math.BigDecimal;

/**
 * Created by SwapanP on 28-05-2016.
 */
public class OutPendingTxn {

    private String upiTxnId;
    private String initiatedBy;
    private String creationTs;
    private String txnStatus;
    private String userId;
    private String name;
    private BigDecimal txnIndividualAmt;
    private String curruency;
    private String paymentAddress;
    private String payerPayeeFlag;
    private String note;
    private String payeeAddress;
    private String payeeName;

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    private String minAmount;

    private String validUpto;

    private String custRef;

    public String getCustRef() {
        return custRef;
    }

    public void setCustRef(String custRef) {
        this.custRef = custRef;
    }

    public String getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(String validUpto) {
        this.validUpto = validUpto;
    }

    public String getUpiTxnId() {
        return upiTxnId;
    }

    public void setUpiTxnId(String upiTxnId) {
        this.upiTxnId = upiTxnId;
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

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTxnIndividualAmt() {
        return txnIndividualAmt;
    }

    public void setTxnIndividualAmt(BigDecimal txnIndividualAmt) {
        this.txnIndividualAmt = txnIndividualAmt;
    }

    public String getCurruency() {
        return curruency;
    }

    public void setCurruency(String curruency) {
        this.curruency = curruency;
    }

    public String getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(String paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public String getPayerPayeeFlag() {
        return payerPayeeFlag;
    }

    public void setPayerPayeeFlag(String payerPayeeFlag) {
        this.payerPayeeFlag = payerPayeeFlag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayeeAddress() {
        return payeeAddress;
    }

    public void setPayeeAddress(String payeeAddress) {
        this.payeeAddress = payeeAddress;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }
}
