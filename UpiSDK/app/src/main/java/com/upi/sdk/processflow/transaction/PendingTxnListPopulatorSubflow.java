package com.upi.sdk.processflow.transaction;

import com.rssoftware.upiint.schema.ListParm;
import com.rssoftware.upiint.schema.UserEntityType;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InPendingTxnQuery;
import com.upi.sdk.domain.OutPendingTxn;
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
public class PendingTxnListPopulatorSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        InPendingTxnQuery historyQuery = (InPendingTxnQuery)o;

        ListParm param = new ListParm();
        param.setParmType(UserEntityType.AUTH_REQ);
        param.setUserId(UpiSDKContext.getInstance().getUserId());
        param.setValidUpto(historyQuery.getValidUpto());
        UppServices.getAllCollectRequest(param, new ServiceCallback<OutPendingTxn[]>(OutPendingTxn[].class) {
            @Override
            public void onSuccess(OutPendingTxn[] result) {
                // TODO We can convert the bean if required
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
