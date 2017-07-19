package com.upi.sdk.utils;

import java.io.Serializable;

/**
 * Created by NeeloyG on 22-06-2016.
 */
public enum CLLanguage implements Serializable {

    hi_IN,
    en_Us,
    gu_IN,
    or_IN,
    as_IN,
    bn_IN,
    kn_IN,
    te_IN,
    ml_IN,
    pa_IN,
    ta_IN,
    ma_IN;

    private CLLanguage() {
    }

    public String value() {
        return this.name();
    }

    public static CLLanguage fromValue(String v) {
        return valueOf(v);
    }
}
