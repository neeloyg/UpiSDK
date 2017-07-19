package com.upi.sdk.processflow.bankAccount;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.rssoftware.upiint.schema.Cred;
import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.NewCred;
import com.rssoftware.upiint.schema.SetCredential;
import com.rssoftware.upiint.schema.State;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class ChangeMpinCredentialCaptureAuthSubflow implements ProcessSubflow {
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(final Object o, final ProcessChain chain) {
        final SetCredential request = (SetCredential) o;
        request.getPayer().setPayerAcVpa(UpiSDKContext.getInstance().getUserDefaultVpa());

        if (request.getiStateListener() != null)
            request.getiStateListener().getState(State.IDLE);

        handler.post(new Runnable() {
            @Override
            public void run() {
                UpiSDKContext.getInstance().getCommonLibrary().getCredentialChangeMPIN(request,
                        new ServiceCallback<Cred[]>(Cred[].class) {

                            @Override
                            public void onSuccess(Cred[] result) {

                                for (Cred cred : result) {

                                    if ("NMPIN".equalsIgnoreCase(cred.getSubtype())) {
                                        Log.d("ChangeMpin", "New Cred added");
                                        NewCred newCred = new NewCred();
                                        newCred.setData(cred.getData());
                                        newCred.setType(cred.getType());
                                        newCred.setSubtype("MPIN");
                                        request.getPayer().getNewcreds().add(newCred);
                                    } else if ("MPIN".equalsIgnoreCase(cred.getSubtype())) {
                                        Log.d("ChangeMpin", "Current Cred added");
                                        request.getPayer().getCreds().add(cred);
                                    }
                                }

                                chain.doNext(o);
                            }

                            @Override
                            public void onError(WebServiceStatus status, List<Error> errors) {
                                if (request.getiStateListener() != null)
                                    request.getiStateListener().getState(State.ACTIVE);
                                if (errors != null && errors.size() > 0) {
                                    chain.breakChain(errors.get(0).getErrorCode(), null);
                                } else {
                                    chain.breakChain(SDKErrorCodes.ERR00000.name(), null);
                                }
                            }
                        });
            }
        });


    }
}
