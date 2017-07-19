package com.upi.sdk.processflow.initialization;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.Payer;
import com.rssoftware.upiint.schema.ReqRegMobile;
import com.rssoftware.upiint.schema.RequestOtp;
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
public class RequestOtpSubflow implements ProcessSubflow {
    private Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void execute(final Object o, final ProcessChain chain) {
        RequestOtp request = new RequestOtp();
        request.setPaymentTxnId(((ReqRegMobile) o).getTxnId());

        request.setOperation("GENERATE");
        Payer payer = new Payer();
        if (((ReqRegMobile) o).getAccNo() != null)
            payer.setBankAcNumber(((ReqRegMobile) o).getAccNo());
        payer.setPayerAcVpa(UpiSDKContext.getInstance().getUserDefaultVpa());
        payer.setPayerAcNickName(UpiSDKContext.getInstance().getUserId());
        payer.setMobile(UpiSDKContext.getInstance().getUserMobile());
        payer.setIfsc(((ReqRegMobile) o).getIfsc());
        request.setDeviceDetails(SDKUtils.getDeviceDetails(HmacGenerationPolicy.GENERAL));
        request.setPayer(payer);
        UppServices.requestOTP(request, new ServiceCallback<Void>(Void.class) {

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
