package com.rssoftware.upiint.schema;

/**
 * Created by AmitKP on 6/20/2016.
 */
public class ForgetMpin {
    protected String ifsc;
    protected CredAllowed credAllowed;
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

    public CredAllowed getCredAllowed() {
        return credAllowed;
    }

    public void setCredAllowed(CredAllowed credAllowed) {
        this.credAllowed = credAllowed;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }



}
