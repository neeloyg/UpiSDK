package com.upi.sdk.processflow.bankAccount;

import com.rssoftware.upiint.schema.BalanceEnquiry;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.BalanceEnquiryRequest;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class BalanceEnquiryRequestConverterSubflow implements ProcessSubflow {
    @Override
    public void execute(Object o, ProcessChain chain) {

        BalanceEnquiryRequest balanceEnquiryRequest = (BalanceEnquiryRequest)o;

        BalanceEnquiry balanceEnquiry = null;
        try {
            balanceEnquiry = Converters.getConverter(BalanceEnquiryRequest.class,
                    BalanceEnquiry.class).convert(balanceEnquiryRequest);
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null);
        }

        if (balanceEnquiry == null)
            chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
        else
            chain.doNext(balanceEnquiry);

    }
}
