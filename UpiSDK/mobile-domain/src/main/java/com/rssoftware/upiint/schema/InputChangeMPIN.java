package com.rssoftware.upiint.schema;

/**
 * Created by NeeloyG on 13-06-2016.
 */
public class InputChangeMPIN {

    protected String ifsc;

    protected CredAllowed credAllowed;

    private IStateListener iStateListener;

    private String bank_name="";

    protected String accNo;
    protected String accType;

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }



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

    public CredAllowed getCredAllowed() {
        return credAllowed;
    }

    public void setCredAllowed(CredAllowed credAllowed) {
        this.credAllowed = credAllowed;
    }

    /**
     * Get the value of the Ifsc.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getIfsc() {
        return ifsc;
    }
    /**
     * Set the value of the Ifsc.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }


}
