package com.rssoftware.upiint.schema;

import java.util.ArrayList;

/**
 * Created by NeeloyG on 9/20/2016.
 */
public class TxnHistoryDetails {

    protected String txnId ;
    protected String custRef ;
    protected String txnType;
    protected String ownerId;
    protected String name;
    protected String txnTotalamt;
    protected String creationTs;
    protected String status;
    protected String currency;
    protected ArrayList<Payer> payers;
    protected ArrayList<Payee> payees;

    protected String refId;

    protected String txnRefId;

    public String getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(String minAmount) {
        this.minAmount = minAmount;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    protected String minAmount;
    protected String expiryDate;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    protected String errorCode;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    protected Mode mode;

    public String getTxnRefId() {
        return txnRefId;
    }

    public void setTxnRefId(String txnRefId) {
        this.txnRefId = txnRefId;
    }



    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getCustRef() {
        return custRef;
    }

    public void setCustRef(String custRef) {
        this.custRef = custRef;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTxnTotalamt() {
        return txnTotalamt;
    }

    public void setTxnTotalamt(String txnTotalamt) {
        this.txnTotalamt = txnTotalamt;
    }

    public String getCreationTs() {
        return creationTs;
    }

    public void setCreationTs(String creationTs) {
        this.creationTs = creationTs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ArrayList<Payer> getPayers() {
        return payers;
    }

    public void setPayers(ArrayList<Payer> payers) {
        this.payers = payers;
    }

    public ArrayList<Payee> getPayees() {
        return payees;
    }

    public void setPayees(ArrayList<Payee> payees) {
        this.payees = payees;
    }
}
