package com.upi.sdk.processflow.transaction;

import com.rssoftware.upiint.schema.Error;
import com.upi.sdk.domain.InputServiceCharge;
import com.upi.sdk.domain.ServiceChargeResult;
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
public class CalculateServiceChargeSubflow implements ProcessSubflow {


    @Override
    public void execute(final Object o, final ProcessChain chain) {

        final InputServiceCharge param = (InputServiceCharge)o;

        UppServices.calculateServiceCharge(param, new ServiceCallback<ServiceChargeResult>(ServiceChargeResult.class) {
            @Override
            public void onSuccess(ServiceChargeResult result) {
                if (result != null) {
                    chain.doNext(result);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00098.name(),null);

                }
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if (errors != null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
                }
            }
        });
    }
}
