package com.upi.sdk.processflow.vpa;

import com.rssoftware.upiint.schema.ListParm;
import com.rssoftware.upiint.schema.Vpa;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class VpaPopulatorSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        final ListParm param = (ListParm)o;

        UppServices.listVPAs(param, new ServiceCallback<Vpa[]>(Vpa[].class) {
            @Override
            public void onSuccess(Vpa[] result) {
                chain.doNext(result);
            }

            @Override
            public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                if (errors!=null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                }else{
                    chain.breakChain(SDKErrorCodes.ERR00000.name(),null);
                }
            }
        });
    }
}
