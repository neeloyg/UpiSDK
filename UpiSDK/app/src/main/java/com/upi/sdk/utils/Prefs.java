package com.upi.sdk.utils;

import android.content.Context;

public class Prefs extends AbsPrefs {


    private static final String PREF_KEY_K0 = "PREFKEY_K0";
    private static final String PREF_DEFAULT_K0 = "";

    public Prefs(Context context) {
        super(context);
    }


    public void setK0(String value) {
        setString(PREF_KEY_K0, value);
    }

    public String getK0() {
        return getString(PREF_KEY_K0, PREF_DEFAULT_K0);
    }


}