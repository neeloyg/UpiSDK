package com.upi.sdk.processflow.initialization;

import android.widget.Toast;

import com.rssoftware.upiint.schema.Device;
import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.RegisterApp;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.UserProfile;
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
 * Created by SwapanP on 05-05-2016.
 */
public class RegisterAppSubflow implements ProcessSubflow {

    private UserProfile user;
    private ServiceCallback<Boolean> initCallback;

    public RegisterAppSubflow(UserProfile user, ServiceCallback<Boolean> initCallback) {
        this.user = user;
        this.initCallback = initCallback;
    }

    @Override
    public void execute(final Object o, final ProcessChain chain) {

        RegisterApp registerApp=new RegisterApp();
        registerApp.setRegisterAppType("initialize");

        Device device=new Device();
        device.setUserId(UpiSDKContext.getInstance().getUserId());
        device.setDeviceId(SDKUtils.getDeviceId());
        device.setDeviceDetails(SDKUtils.getDeviceDetails(HmacGenerationPolicy.APP_REGISTRATION));
        device.setDeviceFingerprint(SDKUtils.getDeviceFingerprint());
        registerApp.setDevice(device);

        UppServices.registerApp(registerApp, new ServiceCallback<String>(String.class) {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    Toast.makeText(UpiSDKContext.getInstance().getApplicationContext(),
                            "App is registered.", Toast.LENGTH_LONG).show();
                    if (initCallback != null) {
                        initCallback.onSuccess(true);
                    }

                    chain.doNext(o);
                   // chain.breakChain(0);

                } else {
                    Toast.makeText(UpiSDKContext.getInstance()
                            .getApplicationContext(), SDKErrorCodes.ERR00103.name(),
                            Toast.LENGTH_LONG).show();
                    if (initCallback != null) {
                        initCallback.onSuccess(false);
                    }

                }

            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
//                Toast.makeText(UpiSDKContext.getInstance().getApplicationContext(),
//                        "Failed to register app.", Toast.LENGTH_LONG).show();
                if (errors != null && !errors.isEmpty()) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00097.name(),null);
                }

            }
        });
    }
}
