package com.upi.sdk.components;

import android.text.TextUtils;

import com.rssoftware.upiint.schema.Error;
import com.upi.sdk.processflow.ProcessFlow;
import com.upi.sdk.processflow.transaction.ListKeyPopulatorSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by SwapanP on 28-04-2016.
 */
public class ListKeys {

    private static String listKeysXmlPayload = null;

    public static void populateXmlPayLoad(final ServiceCallback<String> callback) {

        if (TextUtils.isEmpty(listKeysXmlPayload)) {
            ProcessFlow.create(null, new ServiceCallback<String>(String.class) {

                                                    @Override
                                                    public void onSuccess(String result) {
                                                        listKeysXmlPayload = result;
                                                        callback.onSuccess(result);
                                                    }

                                                    @Override
                                                    public void onError(WebServiceStatus status, List<Error> errors) {
                                                        callback.onError(status, errors);
                                                    }
                                                })
                    .addNext(new ListKeyPopulatorSubflow())
                    .addNext(new ListKeyPopulatorSubflow())
                    .execute();

        } else {
            callback.onSuccess(listKeysXmlPayload);
        }
    }
/*
    private class ListKeysCallback implements ServiceCallback<String> {

        @Override
        public void onSuccess(String result) {
            listKeysXmlPayload = result;
        }

        @Override
        public void onError(WebServiceStatus status, List<Error> errors) {

        }
    }*/
}
