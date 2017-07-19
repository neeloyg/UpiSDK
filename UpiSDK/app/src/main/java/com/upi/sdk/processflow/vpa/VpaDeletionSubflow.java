package com.upi.sdk.processflow.vpa;

import com.rssoftware.upiint.schema.Vpa;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by JayantaC on 28-04-2016.
 */
public class VpaDeletionSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        final Vpa vpa = (Vpa)o;

        UppServices.DeleteVPA(vpa, new ServiceCallback<String>(String.class) {
            @Override
            public void onSuccess(String result) {
                chain.doNext("VPA deleted successfully");
            }

            @Override
            public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                if (errors!=null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                }else{
                    chain.breakChain(SDKErrorCodes.ERR00068.name(),null);
                }
            }
        });
    }
}
