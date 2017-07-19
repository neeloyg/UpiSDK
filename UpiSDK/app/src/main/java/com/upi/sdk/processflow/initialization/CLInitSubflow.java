package com.upi.sdk.processflow.initialization;

import android.text.TextUtils;
import android.util.Log;

import com.rssoftware.upiint.schema.Error;
import com.upi.sdk.CommonLibrary;
import com.upi.sdk.core.UPPSDKConstants;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.CLInitializationMode;
import com.upi.sdk.domain.UserProfile;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.WebServiceStatus;
import com.upi.sdk.utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by SwapanP on 05-05-2016.
 */
public class CLInitSubflow implements ProcessSubflow {

    private UserProfile user;
    private ServiceCallback<CommonLibrary> attachCLCallback;
    private boolean resetToken = false;

    public CLInitSubflow(UserProfile user, ServiceCallback<CommonLibrary> attachCLCallback) {
        this.user = user;
        this.attachCLCallback = attachCLCallback;
    }

    public CLInitSubflow(UserProfile user, ServiceCallback<CommonLibrary> attachCLCallback, boolean resetToken) {
        this.user = user;
        this.attachCLCallback = attachCLCallback;
        this.resetToken = resetToken;
    }

    @Override
    public void execute(final Object o, final ProcessChain chain) {

        if (user == null) {
            chain.breakChain(SDKErrorCodes.ERR00000.name(),null);
        }

        CLInitializationMode mode = null;
        if (resetToken) {
            mode = CLInitializationMode.INIT_TOKEN;
            Log.d("CL-Init Subflow", "Token should be reset");
        } else if (TextUtils.isEmpty(user.getClToken())
                || user.getClTokenRegistrationDate() == null) {
            mode = CLInitializationMode.INIT_TOKEN;
            Log.d("CL-Init Subflow", "Token should be Initiated");
        } else if (DateUtils.getDifferenceInDays(user.getClTokenRegistrationDate(),
                new Date(System.currentTimeMillis())) > UPPSDKConstants.CL_TOKEN_EXPIRY_DAYS) {
            Log.d("CL-Init Subflow", "Token should be rotated");
            mode = CLInitializationMode.ROTATE_TOKEN;
        } else {
            Log.d("CL-Init Subflow", "Valid token exists.");
            mode = CLInitializationMode.TOKEN_EXISTS;
        }

        Log.d("CL-Init Subflow", "Calling initialize");
        CommonLibrary.initializeCL(UpiSDKContext.getInstance().getApplicationContext(),user,
            mode,
            new ServiceCallback<CommonLibrary>(CommonLibrary.class) {
                @Override
                public void onSuccess(CommonLibrary commonLibrary) {
                    if (commonLibrary != null) {
                        if (attachCLCallback != null) {
                            attachCLCallback.onSuccess(commonLibrary);
                            Log.d("APP-SDK", "Common Library is  initialized");
                        }
                        chain.doNext(o);
                    }
                }

                @Override
                public void onError(WebServiceStatus status, List<Error> errors) {
                    if (!UpiSDKContext.isInitialized()) {
                        chain.breakChain(SDKErrorCodes.ERR00000.name(),null);
                        Log.d("APP-SDK", "Common Library is not initialized");
                    } else {
                        Log.d("APP-SDK", "Common Library is not initialized" );
                        chain.breakChain(SDKErrorCodes.ERR00000.name(),null);
                    }

                }
            });
    }
}
