package com.upi.sdk.processflow.beneficiary;

import com.rssoftware.upiint.schema.BeneficiaryDetails;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by NeeloyG on 5/19/2016.
 */

public class BeneficiaryUpdationSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        final BeneficiaryDetails beneficiaryDetails = (BeneficiaryDetails)o;

        UppServices.updateBeneficiary(beneficiaryDetails, new ServiceCallback<String>(String.class) {
            @Override
            public void onSuccess(String result) {
                chain.doNext("Beneficiary updated succesfully");
            }

            @Override
            public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                if (errors!=null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                }else{
                    chain.breakChain(SDKErrorCodes.ERR00022.name(),null);
                }
            }
        });
    }
}
