//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.20 at 03:17:06 PM IST 
//


package com.rssoftware.upiint.schema;


import java.io.Serializable;

/**
 * <p>Java class for CredentialType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CredentialType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PIN"/>
 *     &lt;enumeration value="OTP"/>
 *     &lt;enumeration value="AADHAAR"/>
 *     &lt;enumeration value="CARD"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */


public enum CredentialType implements Serializable {

    PIN,
    OTP,
    AADHAAR,
    CARD;

    public String value() {
        return name();
    }

    public static CredentialType fromValue(String v) {
        return valueOf(v);
    }

}