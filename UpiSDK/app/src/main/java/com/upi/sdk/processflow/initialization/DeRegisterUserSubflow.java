package com.upi.sdk.processflow.initialization;

import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.UserInfo;
import com.upi.sdk.domain.UserProfile;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;


public class DeRegisterUserSubflow implements ProcessSubflow {
    private UserProfile userProfile;

   public DeRegisterUserSubflow(UserProfile userProfile){
        this.userProfile=userProfile;
    }

    @Override
    public void execute(Object o, final ProcessChain chain) {

        final UserInfo userInfo = (UserInfo)o;

        UppServices.deregisterProfile(userInfo, new ServiceCallback<String>(String.class) {
            @Override
            public void onSuccess(String result) {
                userProfile=new UserProfile();
                chain.doNext("Profile deregistered succesfully");


            }

            @Override
            public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                if (errors!=null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                }else{
                    chain.breakChain(SDKErrorCodes.ERR00099.name(),null);
                }
            }
        });
    }
}
