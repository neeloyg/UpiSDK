//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.20 at 03:17:06 PM IST 
//


package com.rssoftware.upiint.schema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;









/**
 * <p>Java class for PaymentAuthRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentAuthRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="txnId" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="authStatus" type="{http://rssoftware.com/upiint/schema/}AuthStatusType"/>
 *         &lt;element name="payerAcVpa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="payerAcNickName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="payerAmt" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="payerAmtCurrency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="creds" type="{http://rssoftware.com/upiint/schema/}Cred" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */


public class PaymentAuthRequest {

    
    
    
    protected String txnId;
    
    
    
    protected String userId;
    
    
    protected AuthStatusType authStatus;
    
    protected String payerAcVpa;
    
    protected String payerAcNickName;
    
    protected BigDecimal payerAmt;
    
    protected String payerAmtCurrency;
    protected List<Cred> creds;
    protected String payerName;
    protected String payeeAcVpa;
    protected String payeeName;

    protected CredAllowed credAllowed;

    private IStateListener iStateListener;

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
     * Gets the value of the txnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnId() {
        return txnId;
    }

    /**
     * Sets the value of the txnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnId(String value) {
        this.txnId = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the authStatus property.
     * 
     * @return
     *     possible object is
     *     {@link AuthStatusType }
     *     
     */
    public AuthStatusType getAuthStatus() {
        return authStatus;
    }

    /**
     * Sets the value of the authStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthStatusType }
     *     
     */
    public void setAuthStatus(AuthStatusType value) {
        this.authStatus = value;
    }

    /**
     * Gets the value of the payerAcVpa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayerAcVpa() {
        return payerAcVpa;
    }

    /**
     * Sets the value of the payerAcVpa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayerAcVpa(String value) {
        this.payerAcVpa = value;
    }

    /**
     * Gets the value of the payerAcNickName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayerAcNickName() {
        return payerAcNickName;
    }

    /**
     * Sets the value of the payerAcNickName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayerAcNickName(String value) {
        this.payerAcNickName = value;
    }

    /**
     * Gets the value of the payerAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPayerAmt() {
        return payerAmt;
    }

    /**
     * Sets the value of the payerAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPayerAmt(BigDecimal value) {
        this.payerAmt = value;
    }

    /**
     * Gets the value of the payerAmtCurrency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayerAmtCurrency() {
        return payerAmtCurrency;
    }

    /**
     * Sets the value of the payerAmtCurrency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayerAmtCurrency(String value) {
        this.payerAmtCurrency = value;
    }

    /**
     * Gets the value of the creds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the creds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Cred }
     * 
     * 
     */
    public List<Cred> getCreds() {
        if (creds == null) {
            creds = new ArrayList<Cred>();
        }
        return this.creds;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayeeAcVpa() {
        return payeeAcVpa;
    }

    public void setPayeeAcVpa(String payeeAcVpa) {
        this.payeeAcVpa = payeeAcVpa;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }
}
