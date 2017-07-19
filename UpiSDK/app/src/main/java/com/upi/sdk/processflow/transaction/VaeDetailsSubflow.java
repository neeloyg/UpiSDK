package com.upi.sdk.processflow.transaction;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.TxnHistoryDetails;
import com.upi.sdk.domain.VaeDetails;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by AmitKP on 20-09-2016.
 */
public class VaeDetailsSubflow implements ProcessSubflow {


    @Override
    public void execute(final Object o, final ProcessChain chain) {

        final VaeDetails param = (VaeDetails)o;

        UppServices.getVaeDetails(param, new ServiceCallback<VaeDetails>(VaeDetails.class) {
            @Override
            public void onSuccess(VaeDetails result) {
                if (result != null) {
                    chain.doNext(result);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00113.name(),null);

                }
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if (errors != null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00113.name(),null);
                }
            }
        });
    }
}
