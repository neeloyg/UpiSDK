//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.17 at 04:14:17 PM IST 
//


package com.rssoftware.upiint.schema;


/**
 * <p>Java class for OtpRequestType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OtpRequestType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="GENERATE"/>
 *     &lt;enumeration value="REGENERATE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 *
 */
public enum OtpRequestType {

    GENERATE,
    REGENERATE;

    public String value() {
        return name();
    }

    public static OtpRequestType fromValue(String v) {
        return valueOf(v);
    }

}
