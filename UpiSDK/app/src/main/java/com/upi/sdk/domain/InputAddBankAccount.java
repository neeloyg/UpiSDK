package com.upi.sdk.domain;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Jayanta on 25-04-2016.
 */
public class InputAddBankAccount {

    private String customerRefId;
    private String nickName;
    private String bankAcNumber;
    private String ifsc;
    private String credDataType;
    private BigInteger credLength;
    private boolean meba;
    private BigInteger atmCredLength;
    private String atmCredDataType;
    private String otpCredDataType;
    private int otpCredLength;

    public String getOtpCredDataType() {
        return otpCredDataType;
    }

    public void setOtpCredDataType(String otpCredDataType) {
        this.otpCredDataType = otpCredDataType;
    }

    public int getOtpCredLength() {
        return otpCredLength;
    }

    public void setOtpCredLength(int otpCredLength) {
        this.otpCredLength = otpCredLength;
    }

    public BigInteger getAtmCredLength() {
        return atmCredLength;
    }

    public void setAtmCredLength(BigInteger atmCredLength) {
        this.atmCredLength = atmCredLength;
    }

    public String getAtmCredDataType() {
        return atmCredDataType;
    }

    public void setAtmCredDataType(String atmCredDataType) {
        this.atmCredDataType = atmCredDataType;
    }









    public String getCredDataType() {
        return credDataType;
    }

    public void setCredDataType(String credDataType) {
        this.credDataType = credDataType;
    }

    public BigInteger getCredLength() {
        return credLength;
    }

    public void setCredLength(BigInteger credLength) {
        this.credLength = credLength;
    }

    public String getAccetptedCred() {
        return accetptedCred;
    }

    public void setAccetptedCred(String accetptedCred) {
        this.accetptedCred = accetptedCred;
    }

    private String accetptedCred;

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    private String accType;

    public String getCustomerRefId() {
        return customerRefId;
    }

    public void setCustomerRefId(String customerRefId) {
        this.customerRefId = customerRefId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBankAcNumber() {
        return bankAcNumber;
    }

    public void setBankAcNumber(String bankAcNumber) {
        this.bankAcNumber = bankAcNumber;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public boolean isMeba() {
        return meba;
    }

    public void setMeba(boolean meba) {
        this.meba = meba;
    }
}
