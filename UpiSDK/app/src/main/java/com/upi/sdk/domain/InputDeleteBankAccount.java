package com.upi.sdk.domain;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Jayanta on 25-04-2016.
 */
public class InputDeleteBankAccount {

  //  private String customerRefId;
    private String nickName;

    public String getLinkAccId() {
        return linkAccId;
    }

    public void setLinkAccId(String linkAccId) {
        this.linkAccId = linkAccId;
    }

    private String linkAccId;

//    public String getCustomerRefId() {
//        return customerRefId;
//    }
//
//    public void setCustomerRefId(String customerRefId) {
//        this.customerRefId = customerRefId;
//    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
