package com.upi.sdk.domain;

import com.rssoftware.upiint.schema.TransactionType;

import java.math.BigInteger;

/**
 * Created by Neeloy Ghosh.
 */
public class InputServiceCharge {

    private String payerVpa;
    private String payeeVpa;
    private String txnAmount;
    private TransactionType txnType;

    public String getPayerVpa() {
        return payerVpa;
    }

    public void setPayerVpa(String payerVpa) {
        this.payerVpa = payerVpa;
    }

    public String getPayeeVpa() {
        return payeeVpa;
    }

    public void setPayeeVpa(String payeeVpa) {
        this.payeeVpa = payeeVpa;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public TransactionType getTxnType() {
        return txnType;
    }

    public void setTxnType(TransactionType txnType) {
        this.txnType = txnType;
    }



}
