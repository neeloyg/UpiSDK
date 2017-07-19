package com.upi.sdk.domain;

import com.rssoftware.upiint.schema.Identity;
import com.rssoftware.upiint.schema.ListParm;
import com.rssoftware.upiint.schema.SecurityQuestion;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SwapanP on 17-05-2016.
 */
public class InputAddDefaultVPA {

    private String userVpa;
    private String userName;
    private String userMobile;
    private String userEmail;
    private String userAddress;
    private String userMaxPayLimit;
    private String passphrase;
    private String idNumber;
    private String idType;
    private String cutomerRefId;
    private List<Identity>identities=new ArrayList<Identity>();
    private List<SecurityQuestion>securityAnswers=new ArrayList<SecurityQuestion>();

    public List<SecurityQuestion> getSecurityAnswers() {
        return securityAnswers;
    }

    public void setSecurityAnswers(List<SecurityQuestion> securityAnswers) {
        this.securityAnswers = securityAnswers;
    }



    public List<Identity> getIdentities() {
        return identities;
    }

    public void setIdentities(List<Identity> identities) {
        this.identities = identities;
    }








    public String getUserVpa() {
        return userVpa;
    }

    public void setUserVpa(String userVpa) {
        this.userVpa = userVpa;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserMaxPayLimit() {
        return userMaxPayLimit;
    }

    public void setUserMaxPayLimit(String userMaxPayLimit) {
        this.userMaxPayLimit = userMaxPayLimit;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getCutomerRefId() {
        return cutomerRefId;
    }

    public void setCutomerRefId(String cutomerRefId) {
        this.cutomerRefId = cutomerRefId;
    }
}
