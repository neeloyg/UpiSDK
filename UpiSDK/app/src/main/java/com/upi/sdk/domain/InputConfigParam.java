package com.upi.sdk.domain;

import com.rssoftware.upiint.schema.VpaPayee;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jayanta on 25-04-2016.
 */
public class InputConfigParam {

    public String getAuthorization_id() {
        return authorization_id;
    }

    public void setAuthorization_id(String authorization_id) {
        this.authorization_id = authorization_id;
    }

    private String authorization_id="";


}
