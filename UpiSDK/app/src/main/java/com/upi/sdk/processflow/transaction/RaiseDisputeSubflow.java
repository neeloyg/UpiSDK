package com.upi.sdk.processflow.transaction;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.RaiseDispute;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by Neeloyg on 20/9/2016.
 */
public class RaiseDisputeSubflow implements ProcessSubflow {


    @Override
    public void execute(final Object o, final ProcessChain chain) {

        final RaiseDispute param = (RaiseDispute) o;

        UppServices.raiseDispute(param, new ServiceCallback<RaiseDispute>(RaiseDispute.class) {
            @Override
            public void onSuccess(RaiseDispute result) {
                if (result != null) {
                    chain.doNext(result);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00101.name(),null);

                }
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if (errors != null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00101.name(),null);
                }
            }
        });
    }


}
