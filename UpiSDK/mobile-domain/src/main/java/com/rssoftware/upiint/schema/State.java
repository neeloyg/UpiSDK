package com.rssoftware.upiint.schema;

import java.io.Serializable;

/**
 * Created by NeeloyG on 22-06-2016.
 */
public enum State implements Serializable {

    ACTIVE,
    IDLE;

    private State() {
    }

    public String value() {
        return this.name();
    }

    public static State fromValue(String v) {
        return valueOf(v);
    }
}
