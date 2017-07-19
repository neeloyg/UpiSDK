package com.upi.sdk.processflow.transaction;

import android.os.Handler;
import android.text.TextUtils;

import com.rssoftware.upiint.schema.ListKeysResponse;
import com.rssoftware.upiint.schema.TokenRequest;
import com.rssoftware.upiint.schema.TokenRequestType;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by SwapanP on 28-04-2016.
 */
public class ListKeyPopulatorSubflow implements ProcessSubflow {
    @Override
    public void execute(Object o, final ProcessChain chain) {

        String txnId = (String)o;

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setTokenRequestType(TokenRequestType.LIST_KEYS);
        tokenRequest.setChallenge("");
        tokenRequest.setTxnId(txnId);

        UppServices.getListKeys(tokenRequest, new ServiceCallback<ListKeysResponse>(ListKeysResponse.class) {
            @Override
            public void onSuccess(final ListKeysResponse result) {
                if (result != null) {
                    if (!TextUtils.isEmpty(result.getListKeysXml())) {
                        chain.breakChainOnSuccess(result.getListKeysXml());
                    } else {
                        if (result.getWaitingTime() != null) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    chain.doNext(result.getTxnId());
                                }
                            }, result.getWaitingTime().longValue());
                        } else {
                            chain.doNext(result.getTxnId());
                        }

                    }
                }
            }

            @Override
            public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                chain.breakChain(SDKErrorCodes.ERR00000.name(),null);
            }
        });
    }
}
