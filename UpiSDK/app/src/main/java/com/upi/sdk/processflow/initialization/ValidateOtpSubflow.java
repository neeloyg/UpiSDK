package com.upi.sdk.processflow.initialization;

import android.util.Log;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.User;
import com.rssoftware.upiint.schema.UserView;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by SwapanP on 19-05-2016.
 */
public class ValidateOtpSubflow implements ProcessSubflow {

    private User user;

    public ValidateOtpSubflow(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User should not be null");
        }
        this.user = user;
    }

    @Override
    public void execute(final Object o, final ProcessChain chain) {

        String otp = (String)o;
        User user = new User();
        user.setUserId(this.user.getUserId());
        user.setUserMobile(this.user.getUserMobile());
        user.setUserPassword(otp);

        UppServices.validateOtp(user, new ServiceCallback<UserView>(UserView.class) {
            @Override
            public void onSuccess(UserView result) {
                Log.d("Validate OTP Subflow", "OTP validated");
                if (result != null) {
                    ValidateOtpSubflow.this.user.setUserStatus(result.getUserStatus());
                    chain.doNext(o);
                }
            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if (errors!=null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                }else{
                    chain.breakChain(SDKErrorCodes.ERR00009.name(),null);
                }
            }
        });
    }
}
