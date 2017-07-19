package com.upi.sdk.processflow.initialization;

import com.rssoftware.upiint.schema.Error;
import com.upi.sdk.domain.ChangePassword;
import com.upi.sdk.domain.InputActivateVPA;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;


public class ActivateVPASubflow implements ProcessSubflow {
    @Override
    public void execute(final Object o, final ProcessChain chain) {

        final InputActivateVPA param = (InputActivateVPA)o;

        UppServices.activateVPA(param, new ServiceCallback<String>(String.class) {

            @Override
            public void onSuccess(String result) {
                chain.doNext("VPA activated successfully");
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if (errors != null && !errors.isEmpty()) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00068.name(), null);
                }
            }
        });
    }
}
