package com.rssoftware.upiint.schema;

import java.io.Serializable;

/**
 * Created by NeeloyG on 22-06-2016.
 */
public enum Mode implements Serializable {

    PAY,
    ALL,
    COLLECT;

    private Mode() {
    }

    public String value() {
        return this.name();
    }

    public static Mode fromValue(String v) {
        return valueOf(v);
    }
}
