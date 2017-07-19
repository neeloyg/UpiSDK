package com.upi.sdk.processflow.initialization;

import com.rssoftware.upiint.schema.BeneficiaryDetails;
import com.rssoftware.upiint.schema.ConfigElement;
import com.upi.sdk.errors.UPISDKException;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;
import com.upi.sdk.utils.SDKUtils;

import java.util.List;

/**
 * Created by NeeloyG on 5/19/2016.
 */

public class ConfigParamSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        final ConfigElement param = (ConfigElement)o;

        UppServices.fetchConfiguration(param, new ServiceCallback<ConfigElement>(ConfigElement.class) {
            @Override
            public void onSuccess(ConfigElement result) {
                SDKUtils.BANK_NAME=result.getBankName();
                SDKUtils.PUBLIC_KEY=result.getPublickKey();
                SDKUtils.VMN_NUMBER=result.getClMobileNumber();
                SDKUtils.K0HEX=result.getK0Hex();
                if(result.getOrgRefUrl()!=null)
                SDKUtils.REF_URL=result.getOrgRefUrl();
                if(result.getBankPSP()!=null)
                    SDKUtils.PSP=result.getBankPSP();
                chain.doNext(result);
            }

            @Override
            public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                if (errors!=null && errors.size() > 0) {

                    chain.breakChain(errors.get(0).getErrorCode(),null);
                }
            }
        });
    }
}
