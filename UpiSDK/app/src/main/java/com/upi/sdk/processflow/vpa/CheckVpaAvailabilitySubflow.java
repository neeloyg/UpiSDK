package com.upi.sdk.processflow.vpa;

import com.rssoftware.upiint.schema.UserValidation;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by JayantaC on 28-04-2016.
 */
public class CheckVpaAvailabilitySubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        final UserValidation userValParm = (UserValidation)o;

        UppServices.checkVpaAvailability(userValParm, new ServiceCallback<Boolean>(Boolean.class) {
            @Override
            public void onSuccess(Boolean result) {
                chain.doNext(result);
            }

            @Override
            public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                if (errors!=null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                }else{
                    chain.breakChain(SDKErrorCodes.ERR00068.name(),null);
                }
            }
        });
    }
}
