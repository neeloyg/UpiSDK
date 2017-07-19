package com.upi.sdk.domain;


public class BankAccountBean {

    protected String bankAcnumber;
    protected String maskedbankAcnumber;

    public String getBankAcnumber() {
        return bankAcnumber;
    }

    public void setBankAcnumber(String bankAcnumber) {
        this.bankAcnumber = bankAcnumber;
    }

    public String getMaskedbankAcnumber() {
        return maskedbankAcnumber;
    }

    public void setMaskedbankAcnumber(String maskedbankAcnumber) {
        this.maskedbankAcnumber = maskedbankAcnumber;
    }


}
