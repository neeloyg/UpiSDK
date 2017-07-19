package com.upi.sdk.domain;

import java.math.BigDecimal;

/**
 * Created by SwapanP on 26-04-2016.
 */
public class UpiEntity {

    protected String name;
    protected String virtualPaymentAddress;
    protected BigDecimal amount;
    protected String currency;
    protected String acNickName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVirtualPaymentAddress() {
        return virtualPaymentAddress;
    }

    public void setVirtualPaymentAddress(String virtualPaymentAddress) {
        this.virtualPaymentAddress = virtualPaymentAddress;
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

    public String getAcNickName() {
        return acNickName;
    }

    public void setAcNickName(String acNickName) {
        this.acNickName = acNickName;
    }
}
