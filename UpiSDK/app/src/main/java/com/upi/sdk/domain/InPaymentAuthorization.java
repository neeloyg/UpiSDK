package com.upi.sdk.domain;

import com.rssoftware.upiint.schema.CredAllowed;
import com.rssoftware.upiint.schema.IStateListener;

import java.math.BigDecimal;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class InPaymentAuthorization {

    private String txnId;
    private boolean authorized;
    private boolean isSpam=false;
    private String accountNickName;
    private String paymentAddress;
    private String payeePaymentAddress;
    private String payeeName;
    protected BigDecimal amount;
    protected String currency;

    private CredAllowed credAllowed;

    private IStateListener iStateListener;

    public boolean isSpam() {
        return isSpam;
    }

    public void setSpam(boolean spam) {
        isSpam = spam;
    }



    public IStateListener getiStateListener() {
        return iStateListener;
    }

    public void setiStateListener(IStateListener iStateListener) {
        this.iStateListener = iStateListener;
    }


    public CredAllowed getCredAllowed() {
        return credAllowed;
    }

    public void setCredAllowed(CredAllowed credAllowed) {
        this.credAllowed = credAllowed;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public String getAccountNickName() {
        return accountNickName;
    }

    public void setAccountNickName(String accountNickName) {
        this.accountNickName = accountNickName;
    }

    public String getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(String paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayeePaymentAddress() {
        return payeePaymentAddress;
    }

    public void setPayeePaymentAddress(String payeePaymentAddress) {
        this.payeePaymentAddress = payeePaymentAddress;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }
}
