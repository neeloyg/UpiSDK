package com.rssoftware.upiint.schema;

/**
 * Created by AmitKP on 6/8/2016.
 */
public class ListAccountsByMobile {

    protected String accRefNumber;
    protected String accType;
    protected String aeba;
    protected String ifsc;
    protected String maskedAccnumber;
    protected String mbeba;
    protected String mmid;
    protected String name;

    public String getAccRefNumber() {
        return accRefNumber;
    }

    public void setAccRefNumber(String accRefNumber) {
        this.accRefNumber = accRefNumber;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getAeba() {
        return aeba;
    }

    public void setAeba(String aeba) {
        this.aeba = aeba;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }


    public String getMaskedAccnumber() {
        return maskedAccnumber;
    }

    public void setMaskedAccnumber(String maskedAccnumber) {
        this.maskedAccnumber = maskedAccnumber;
    }


    public String getMbeba() {
        return mbeba;
    }

    public void setMbeba(String mbeba) {
        this.mbeba = mbeba;
    }


    public String getMmid() {
        return mmid;
    }

    public void setMmid(String mmid) {
        this.mmid = mmid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
