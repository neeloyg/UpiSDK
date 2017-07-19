package com.upi.sdk.processflow.transaction;

import com.rssoftware.upiint.schema.ListParm;
import com.rssoftware.upiint.schema.TransactionHistory;
import com.rssoftware.upiint.schema.UserEntityType;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InTxnHistoryQuery;
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
public class TransactionHistoryPopulatorSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        InTxnHistoryQuery historyQuery = (InTxnHistoryQuery)o;

        if(historyQuery.getFromDate()!=null && historyQuery.getFromDate().length()>0 &&
                historyQuery.getToDate()!=null && historyQuery.getToDate().length()>0 ){
            ListParm param = new ListParm();
            param.setUserId(UpiSDKContext.getInstance().getUserId());
            param.setMode("ALL");
            param.setToDate(historyQuery.getToDate());
            param.setFromDate(historyQuery.getFromDate());
            UppServices.getAllTransactionsByDateFilter(param, new ServiceCallback<TransactionHistory[]>(TransactionHistory[].class) {
                @Override
                public void onSuccess(TransactionHistory[] result) {
                    // TODO We can convert the bean if required
                    chain.doNext(result);
                }

                @Override
                public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                    if (errors != null && errors.size() > 0) {
                        chain.breakChain(errors.get(0).getErrorCode(), null);
                    } else {
                        chain.breakChain(SDKErrorCodes.ERR00000.name(), null);
                    }
                }
            });

        }else{
            ListParm param = new ListParm();
            param.setParmType(UserEntityType.TRAN_HIST);
            param.setUserId(UpiSDKContext.getInstance().getUserId());
            UppServices.getAllTransactions(param, new ServiceCallback<TransactionHistory[]>(TransactionHistory[].class) {
                @Override
                public void onSuccess(TransactionHistory[] result) {
                    // TODO We can convert the bean if required
                    chain.doNext(result);
                }

                @Override
                public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                    if (errors != null && errors.size() > 0) {
                        chain.breakChain(errors.get(0).getErrorCode(), null);
                    } else {
                        chain.breakChain(SDKErrorCodes.ERR00000.name(), null);
                    }
                }
            });
        }



    }
}
