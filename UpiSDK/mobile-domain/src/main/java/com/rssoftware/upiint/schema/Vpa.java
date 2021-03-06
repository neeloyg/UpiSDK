//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.20 at 03:17:06 PM IST 
//


package com.rssoftware.upiint.schema;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;









/**
 * <p>Java class for Vpa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Vpa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ownerId" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="vpa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="vpaId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="validUpto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="maxLimit" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="selectivePayee" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="isOneTime" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="defaultVpa" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="acctLinkRuleId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="payAccId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="payNickName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="collectNickName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="collectAccId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="payees" type="{http://rssoftware.com/upiint/schema/}VpaPayee" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */


public class Vpa {

    
    
    
    protected String ownerId;
    
    protected String vpa;
    
    protected BigInteger vpaId;
    
    protected String validUpto;
    
    protected BigDecimal maxLimit;
    protected boolean selectivePayee;
    protected boolean isOneTime;
    protected boolean defaultVpa;
    
    protected String status;
    
    protected BigInteger acctLinkRuleId;
    
    protected BigInteger payAccId;
    
    protected String payNickName;
    
    protected String collectNickName;
    
    protected BigInteger collectAccId;
    protected List<VpaPayee> payees;

    protected String ruleName;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }



    /**
     * Gets the value of the ownerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the value of the ownerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwnerId(String value) {
        this.ownerId = value;
    }

    /**
     * Gets the value of the vpa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVpa() {
        return vpa;
    }

    /**
     * Sets the value of the vpa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVpa(String value) {
        this.vpa = value;
    }

    /**
     * Gets the value of the vpaId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVpaId() {
        return vpaId;
    }

    /**
     * Sets the value of the vpaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVpaId(BigInteger value) {
        this.vpaId = value;
    }

    /**
     * Gets the value of the validUpto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidUpto() {
        return validUpto;
    }

    /**
     * Sets the value of the validUpto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidUpto(String value) {
        this.validUpto = value;
    }

    /**
     * Gets the value of the maxLimit property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxLimit() {
        return maxLimit;
    }

    /**
     * Sets the value of the maxLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxLimit(BigDecimal value) {
        this.maxLimit = value;
    }

    /**
     * Gets the value of the selectivePayee property.
     * 
     */
    public boolean isSelectivePayee() {
        return selectivePayee;
    }

    /**
     * Sets the value of the selectivePayee property.
     * 
     */
    public void setSelectivePayee(boolean value) {
        this.selectivePayee = value;
    }

    /**
     * Gets the value of the isOneTime property.
     * 
     */
    public boolean isIsOneTime() {
        return isOneTime;
    }

    /**
     * Sets the value of the isOneTime property.
     * 
     */
    public void setIsOneTime(boolean value) {
        this.isOneTime = value;
    }

    /**
     * Gets the value of the defaultVpa property.
     * 
     */
    public boolean isDefaultVpa() {
        return defaultVpa;
    }

    /**
     * Sets the value of the defaultVpa property.
     * 
     */
    public void setDefaultVpa(boolean value) {
        this.defaultVpa = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the acctLinkRuleId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAcctLinkRuleId() {
        return acctLinkRuleId;
    }

    /**
     * Sets the value of the acctLinkRuleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAcctLinkRuleId(BigInteger value) {
        this.acctLinkRuleId = value;
    }

    /**
     * Gets the value of the payAccId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPayAccId() {
        return payAccId;
    }

    /**
     * Sets the value of the payAccId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPayAccId(BigInteger value) {
        this.payAccId = value;
    }

    /**
     * Gets the value of the payNickName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayNickName() {
        return payNickName;
    }

    /**
     * Sets the value of the payNickName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayNickName(String value) {
        this.payNickName = value;
    }

    /**
     * Gets the value of the collectNickName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollectNickName() {
        return collectNickName;
    }

    /**
     * Sets the value of the collectNickName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollectNickName(String value) {
        this.collectNickName = value;
    }

    /**
     * Gets the value of the collectAccId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCollectAccId() {
        return collectAccId;
    }

    /**
     * Sets the value of the collectAccId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCollectAccId(BigInteger value) {
        this.collectAccId = value;
    }

    /**
     * Gets the value of the payees property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the payees property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPayees().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VpaPayee }
     * 
     * 
     */
    public List<VpaPayee> getPayees() {
        if (payees == null) {
            payees = new ArrayList<VpaPayee>();
        }
        return this.payees;
    }

}
