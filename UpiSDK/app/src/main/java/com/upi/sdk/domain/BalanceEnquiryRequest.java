package com.upi.sdk.domain;

import com.rssoftware.upiint.schema.CredAllowed;
import com.rssoftware.upiint.schema.IStateListener;

/**
 * Created by SwapanP on 27-05-2016.
 */
public class BalanceEnquiryRequest {

    private String nickName;

    private String bankAcNumber;


    private String accType;




    private String mobileNumber;

    private CredAllowed credAllowed;

    private IStateListener iStateListener;

    private String bank_name="";

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public IStateListener getiStateListener() {
        return iStateListener;
    }

    public void setiStateListener(IStateListener iStateListener) {
        this.iStateListener = iStateListener;
    }




    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    public CredAllowed getCredAllowed() {
        return credAllowed;
    }

    public void setCredAllowed(CredAllowed credAllowed) {
        this.credAllowed = credAllowed;
    }

    public String getBankAcNumber() {
        return bankAcNumber;
    }

    public void setBankAcNumber(String bankAcNumber) {
        this.bankAcNumber = bankAcNumber;
    }



    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

}
