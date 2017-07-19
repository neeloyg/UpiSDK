package com.upi.sdk.domain;

import com.rssoftware.upiint.schema.ChannelType;
import com.rssoftware.upiint.schema.CredAllowed;
import com.rssoftware.upiint.schema.IStateListener;
import com.rssoftware.upiint.schema.Payee;
import com.rssoftware.upiint.schema.Payer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by SwapanP on 26-04-2016.
 */
public class InPayRequest {

    protected UpiPayer payer;
    protected List<UpiPayee> payees;
    protected String note;
    protected String validUpto;
    private String txn_type="";
    private String bankAcNumber="";
    private CredAllowed credAllowed;




    private Double minAmount=0.0;

    private IStateListener iStateListener;

    private String bank_name="";

    private String refId;

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
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



    public String getBankAcNumber() {
        return bankAcNumber;
    }

    public void setBankAcNumber(String bankAcNumber) {
        this.bankAcNumber = bankAcNumber;
    }




    public String getTxn_type() {
        return txn_type;
    }

    public void setTxn_type(String txn_type) {
        this.txn_type = txn_type;
    }


    public UpiPayer getPayer() {
        return payer;
    }

    public void setPayer(UpiPayer payer) {
        this.payer = payer;
    }

    public List<UpiPayee> getPayees() {
        if (payees == null)
            payees = new LinkedList<>();
        return payees;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(String validUpto) {
        this.validUpto = validUpto;
    }
}
