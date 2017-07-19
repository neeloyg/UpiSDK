package com.upi.sdk.processflow.transaction;

import android.text.TextUtils;

import com.rssoftware.upiint.schema.BalanceEnquiry;
import com.rssoftware.upiint.schema.Error;
import com.rssoftware.upiint.schema.PaymentRequest;
import com.rssoftware.upiint.schema.ReqRegMobile;
import com.rssoftware.upiint.schema.RequestValAddress;
import com.rssoftware.upiint.schema.SetCredential;
import com.upi.sdk.core.UpiSDKContext;
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
public class TransactionIdGeneratorSubflow implements ProcessSubflow{

    @Override
    public void execute(final Object o, final ProcessChain chain) {

        // get TxnId
        UppServices.getTransactionId(new ServiceCallback<String>(String.class) {
            @Override
            public void onSuccess(final String txnId) {
                if (!TextUtils.isEmpty(txnId)) {
                    if (o instanceof PaymentRequest) {
                        ((PaymentRequest)o).setTxnId(txnId);
                    } else if (o instanceof BalanceEnquiry) {
                        ((BalanceEnquiry)o).setTxnId(txnId);
                    }else if (o instanceof ReqRegMobile) {
                        ((ReqRegMobile)o).setTxnId(txnId);
                        ((ReqRegMobile)o).setOwnerId(UpiSDKContext.getInstance().getUserId());
                    }else if (o instanceof SetCredential) {
                        ((SetCredential)o).setTxnId(txnId);
                        ((SetCredential)o).setMode("REQUEST_SETCRE");
                    }else if (o instanceof RequestValAddress) {
                        ((RequestValAddress)o).setTxnId(txnId);
                    }
                    chain.doNext(o);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00000.name(),null);
                }

            }

            @Override
            public void onError(WebServiceStatus status, List<Error> errors) {
                if (errors!=null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                }else{
                    chain.breakChain(SDKErrorCodes.ERR00000.name(),null);
                }
            }
        });
    }
}
