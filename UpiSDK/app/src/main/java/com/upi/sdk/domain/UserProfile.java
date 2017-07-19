package com.upi.sdk.domain;

import com.rssoftware.upiint.schema.User;

/**
 * Created by SwapanP on 05-05-2016.
 */
public final class UserProfile extends User {

    protected String keyPhraseHmac;

    public String getKeyPhraseHmac() {
        return keyPhraseHmac;
    }

    public void setKeyPhraseHmac(String keyPhraseHmac) {
        this.keyPhraseHmac = keyPhraseHmac;
    }
}
