package com.upi.sdk.processflow.initialization;

import android.util.Log;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.OtpServiceDetails;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.HmacGenerationPolicy;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;
import com.upi.sdk.utils.SDKUtils;

import java.util.List;

/**
 * Created by SwapanP on 18-05-2016.
 */
public class SendOtpSubflow implements ProcessSubflow {
    @Override
    public void execute(final Object o, final ProcessChain chain) {
        OtpServiceDetails request = new OtpServiceDetails();
        request.setMobileNo(UpiSDKContext.getInstance().getUserMobile());
        request.setDeviceDetails(SDKUtils.getDeviceDetails(HmacGenerationPolicy.GENERAL));
        UppServices.sendOtpRequest(request, new ServiceCallback<Void>(Void.class) {

            @Override
            public void onSuccess(Void result) {
                Log.d("OTP Subflow", "OTP Request Success");
                chain.doNext(o);
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                Log.d("OTP Subflow", "OTP Request Failure");
                if (errors != null && !errors.isEmpty()) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00052.name(),null);
                }
            }
        });
    }
}
