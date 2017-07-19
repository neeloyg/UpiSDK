package com.upi.sdk.services.background;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by SwapanP on 26-05-2016.
 */
public class CommonLibraryInitializer extends IntentService {
    public CommonLibraryInitializer(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        intent.getParcelableExtra("resultReceiver");

       /* CommonLibrary.initializeCL(UpiSDKContext.getInstance().getApplicationContext(),
                new ServiceCallback<CommonLibrary>(CommonLibrary.class) {
                    @Override
                    public void onSuccess(CommonLibrary commonLibrary) {
                        if (commonLibrary != null) {
                            if (attachCLCallback != null) {
                                attachCLCallback.onSuccess(commonLibrary);
                            }
                            chain.doNext(o);
                        }
                    }

                    @Override
                    public void onError(WebServiceStatus status, List<Error> errors) {
                        if (!UpiSDKContext.isInitialized()) {
                            chain.breakChain(UPIErrorCode.SYSTEM_ERROR);
                        } else {
                            Log.d("APP-SDK", "Common Library is not initialized");
                            chain.breakChain(UPIErrorCode.SYSTEM_ERROR);
                        }

                    }
                });*/
    }
}
