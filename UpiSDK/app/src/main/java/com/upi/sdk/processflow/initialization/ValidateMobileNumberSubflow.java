package com.upi.sdk.processflow.initialization;

import android.os.Handler;
import android.text.TextUtils;

import com.rssoftware.upiint.schema.Error;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class ValidateMobileNumberSubflow implements ProcessSubflow{
    private int i=0;

    @Override
    public void execute(final Object o, final ProcessChain chain) {

        // get TxnId
        callCodeLOng(chain);

    }

    private void callCodeLOng(final ProcessChain chain){
        UppServices.validateMobileNumber(new ServiceCallback<String>(String.class) {
            @Override
            public void onSuccess(final String mobile) {
                if (!TextUtils.isEmpty(mobile) && mobile.length()>0) {
                    i=3;
                    chain.doNext(mobile);
                } else {
                    if(i==3){
                        chain.breakChain(SDKErrorCodes.ERR00090.name(),null);
                    }
                    if (i < 4) {
                        try {
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    i++;
                                   callCodeLOng(chain);
                                }
                            }, 10000);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if(i==3){
                    chain.breakChain(SDKErrorCodes.ERR00090.name(),null);
                }
                if (i < 4) {
                    try {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                i++;
                                callCodeLOng(chain);
                            }
                        }, 10000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
