package com.upi.sdk.processflow.initialization;

import android.util.Log;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.OtpServiceDetails;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by SwapanP on 18-05-2016.
 */
public class ValidateOTPForForgetPINSubflow implements ProcessSubflow {
    @Override
    public void execute(final Object o, final ProcessChain chain) {

        final OtpServiceDetails param = (OtpServiceDetails)o;

        UppServices.validateOTPforPasswordReset(param, new ServiceCallback<String>(String.class) {

            @Override
            public void onSuccess(String result) {
                chain.doNext(result);
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                Log.d("OTP Subflow", "OTP Request Failure");
                if (errors != null && !errors.isEmpty()) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00009.name(),null);
                }
            }
        });
    }
}
