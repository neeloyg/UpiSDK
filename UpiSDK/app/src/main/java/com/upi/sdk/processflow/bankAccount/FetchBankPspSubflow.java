package com.upi.sdk.processflow.bankAccount;

import com.rssoftware.upiint.schema.ListParm;
import com.rssoftware.upiint.schema.PspBankList;
import com.rssoftware.upiint.schema.UserEntityType;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by SwapanP on 15-05-2016.
 */
public class FetchBankPspSubflow implements ProcessSubflow {
    @Override
    public void execute(Object o, final ProcessChain chain) {
        ListParm param = new ListParm();
        param.setParmType(UserEntityType.BANK_PSP);
        param.setUserId(UpiSDKContext.getInstance().getUserId());
        param.setOwnerId(UpiSDKContext.getInstance().getUserId());
        UppServices.fetchBankPSPs(param, new ServiceCallback<PspBankList[]>(PspBankList[].class) {
            @Override
            public void onSuccess(PspBankList[] result) {
                chain.doNext(result);
            }

            @Override
            public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                if (errors!=null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                }else{
                    chain.breakChain(SDKErrorCodes.ERR00030.name(),null);
                }
            }
        });
    }
}
