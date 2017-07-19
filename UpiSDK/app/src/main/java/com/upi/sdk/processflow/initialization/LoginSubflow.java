package com.upi.sdk.processflow.initialization;

import android.text.TextUtils;
import android.util.Log;

import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.UserView;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.UserProfile;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by SwapanP on 05-05-2016.
 */
public class LoginSubflow implements ProcessSubflow {

    private UserProfile user;
    private ServiceCallback<Boolean> serviceCallback;
   // private DatabaseHelper dbHelper;

    public LoginSubflow(UserProfile user, ServiceCallback<Boolean> serviceCallback) {
        this.user = user;
        this.serviceCallback = serviceCallback;
    }

    @Override
    public void execute(final Object o, final ProcessChain chain) {
        Log.d("Login Subflow", "Starting Login subflow");
        UppServices.login(user, new ServiceCallback<UserView>(UserView.class) {

            @Override
            public void onSuccess(UserView result) {
                Log.d("Login Subflow", "Received result for login");
               // dbHelper=new DatabaseHelper(UpiSDKContext.getInstance().getApplicationContext());
//                dbHelper.fetchFromDb("userid");
             //   Log.d("DB USERID FETCH",  dbHelper.fetchFromDb("user_id"));

                if (result != null) {
                    if (!TextUtils.isEmpty(UpiSDKContext.getInstance().getSessionToken())
                            && result.getUserStatus().equalsIgnoreCase("ACTIVE")) {
                        merge(result);
                        Log.d("Login Subflow", "merged result for login");
                        chain.doNext(o);

                    } else {
                        merge(result);
                        //chain.doNext(o);
                        //Valuable line for CL clearence
                        chain.breakChain(SDKErrorCodes.ERR00100.name(),null);
                       }
                    serviceCallback.onSuccess(true);
                } else {
                    Log.d("Login Subflow", "Received null result for login");
                    chain.breakChain(SDKErrorCodes.ERR00100.name(),null);
                    serviceCallback.onSuccess(false);
                }

            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                Log.d("Login Subflow", "Received error for login");

                if (errors != null && !errors.isEmpty()) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00100.name(),null);
                }
                serviceCallback.onError(status, errors);
            }
        });
    }

    private void merge(UserView userView) {

        // update the data in the user object
        user.setUserId(userView.getUserId());
        user.setUserName(userView.getUserName());
        if (!TextUtils.isEmpty(UpiSDKContext.getInstance().getSessionToken()))
            user.setUserStatus(userView.getUserStatus());
        else
            user.setUserStatus("MOBILE_NOT_VERIFIED");
//        if(userView.getUserMobile()!=null && userView.getUserMobile().length()>0)
//        {
//            String mobile_number="";
//            mobile_number=userView.getUserMobile().substring(userView.getUserMobile().length() - 10);
//            user.setUserMobile(mobile_number);
//        }
        user.setUserMobile(userView.getUserMobile());
        user.setUserVpa(userView.getDefaultVpa());
        user.setClToken(userView.getClToken());
        user.setClTokenRegistrationDate(userView.getClTokenRegistrationDate());
        if (userView.getLastLoginTs() != null && userView.getLastLoginTs().length() > 0)
            user.setLastLoginTs(userView.getLastLoginTs());

        if(userView.getAggrCode()!=null)
            user.setAggrCode(userView.getAggrCode());

        if(userView.getMrchCode()!=null)
            user.setMrchCode(userView.getMrchCode());

        Log.d("Token:", "" + userView.getClToken());
        Log.d("TokenRegDate:", "" + userView.getClTokenRegistrationDate());
    }
}
