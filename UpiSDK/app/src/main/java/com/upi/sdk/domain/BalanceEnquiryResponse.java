package com.upi.sdk.domain;

import java.math.BigDecimal;

/**
 * Created by SwapanP on 27-05-2016.
 */
public class BalanceEnquiryResponse {

    private BigDecimal accountBalance;

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accounTbalance) {
        this.accountBalance = accounTbalance;
    }
}
