package com.upi.sdk.domain;

import com.rssoftware.upiint.schema.VpaPayee;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jayanta on 25-04-2016.
 */
public class InputAddVPA {

    private String customerRefId;
    private String userVPA;
    private String validUpto;
    private BigDecimal maxLimit;
    private boolean isOneTimeUse;
    private boolean forSelectivePayee;
    private BigInteger paymentAccountID;
    private BigInteger collectAccountID;
    private List<VpaPayee> selectivePayees;
    private String acctLinkRuleId;

    public String getAcctLinkRuleId() {
        return acctLinkRuleId;
    }

    public void setAcctLinkRuleId(String acctLinkRuleId) {
        this.acctLinkRuleId = acctLinkRuleId;
    }



    public String getCustomerRefId() {
        return customerRefId;
    }

    public void setCustomerRefId(String customerRefId) {
        this.customerRefId = customerRefId;
    }

    public String getUserVPA() {
        return userVPA;
    }

    public void setUserVPA(String userVPA) {
        this.userVPA = userVPA;
    }

    public String getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(String validUpto) {
        this.validUpto = validUpto;
    }

    public BigDecimal getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(BigDecimal maxLimit) {
        this.maxLimit = maxLimit;
    }

    public boolean isOneTimeUse() {
        return isOneTimeUse;
    }

    public void setIsOneTimeUse(boolean isOneTimeUse) {
        this.isOneTimeUse = isOneTimeUse;
    }

    public boolean getIsForSelectivePayee() {
        return forSelectivePayee;
    }

    public void setForSelectivePayee(boolean forSelectivePayee) {
        this.forSelectivePayee = forSelectivePayee;
    }

    public BigInteger getPaymentAccountID() {
        return paymentAccountID;
    }

    public void setPaymentAccountID(BigInteger paymentAccountID) {
        this.paymentAccountID = paymentAccountID;
    }

    public BigInteger getCollectAccountID() {
        return collectAccountID;
    }

    public void setCollectAccountID(BigInteger collectAccountID) {
        this.collectAccountID = collectAccountID;
    }

    public List<VpaPayee> getSelectivePayees() {
        if (selectivePayees == null) {
            selectivePayees = new LinkedList<>();
        }
        return selectivePayees;
    }

}
