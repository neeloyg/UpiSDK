package com.upi.sdk.domain;

import com.rssoftware.upiint.schema.VpaPayee;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Jayanta on 25-04-2016.
 */
public class InputUpdateVPA {

    private String customerRefId;
    private BigInteger VPAID;
    private String userVPA;
    private String validUpto;
    private BigDecimal maxLimit;
    private boolean isOneTimeUse;
    private boolean forSelectivePayee;
    private BigInteger PaymentAccountID;
    private BigInteger CollectAccountID;
    private String acctLinkRuleId;
    public String getAcctLinkRuleId() {
        return acctLinkRuleId;
    }

    public void setAcctLinkRuleId(String acctLinkRuleId) {
        this.acctLinkRuleId = acctLinkRuleId;
    }


    private List<VpaPayee> SelectivePayees;

    public String getCustomerRefId() {
        return customerRefId;
    }

    public void setCustomerRefId(String customerRefId) {
        this.customerRefId = customerRefId;
    }

    public BigInteger getVPAID() {
        return VPAID;
    }

    public void setVPAID(BigInteger VPAID) {
        this.VPAID = VPAID;
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
        return PaymentAccountID;
    }

    public void setPaymentAccountID(BigInteger paymentAccountID) {
        PaymentAccountID = paymentAccountID;
    }

    public BigInteger getCollectAccountID() {
        return CollectAccountID;
    }

    public void setCollectAccountID(BigInteger collectAccountID) {
        CollectAccountID = collectAccountID;
    }

    public List<VpaPayee> getSelectivePayees() {
        return SelectivePayees;
    }

    public void setSelectivePayees(List<VpaPayee> selectivePayees) {
        SelectivePayees = selectivePayees;
    }
}
