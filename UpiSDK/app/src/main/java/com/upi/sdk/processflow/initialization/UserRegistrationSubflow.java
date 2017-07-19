package com.upi.sdk.processflow.initialization;

import android.util.Log;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.UserView;
import com.upi.sdk.domain.UserProfile;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by SwapanP on 17-05-2016.
 */
public class UserRegistrationSubflow implements ProcessSubflow {

    private UserProfile user;
    private ServiceCallback<Boolean> initNotifierCallback;

    public UserRegistrationSubflow(UserProfile user, ServiceCallback<Boolean> initNotifierCallback) {
        this.user = user;
        this.initNotifierCallback = initNotifierCallback;
    }

    @Override
    public void execute(final Object o, final ProcessChain chain) {
        Log.d("Registration Subflow", "Starting Registration Subflow");
        UppServices.registerUser(user, new ServiceCallback<UserView>(UserView.class) {

            @Override
            public void onSuccess(UserView result) {
                Log.d("Registration Subflow", "Received result for registration");
                if (result != null) {
                    merge(result);
                    Log.d("Registration Subflow", "merged result for registration");
                    initNotifierCallback.onSuccess(true);
                    chain.doNext(o);
                } else {
                    Log.d("Registration Subflow", "Received null result for registration");
                    chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                    initNotifierCallback.onSuccess(false);
                }

            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                Log.d("Registration Subflow", "Received error for registration");
                if (errors != null && !errors.isEmpty()) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                }
            }
        });
    }

    private void merge(UserView userView) {
        // update the data in the user object
        user.setUserId(userView.getUserId());
        user.setUserName(userView.getUserName());
        user.setUserStatus(userView.getUserStatus());
        user.setUserMobile(userView.getUserMobile());
    }
}
