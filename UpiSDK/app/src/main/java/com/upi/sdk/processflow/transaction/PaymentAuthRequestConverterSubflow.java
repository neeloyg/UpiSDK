package com.upi.sdk.processflow.transaction;

import com.rssoftware.upiint.schema.PaymentAuthRequest;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.InPaymentAuthorization;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class PaymentAuthRequestConverterSubflow implements ProcessSubflow {
    @Override
    public void execute(Object o, ProcessChain chain) {

        InPaymentAuthorization payAuth = (InPaymentAuthorization)o;

        PaymentAuthRequest paymentAuthRequest = null;
        try {
            paymentAuthRequest = Converters.getConverter(InPaymentAuthorization.class,
                    PaymentAuthRequest.class).convert(payAuth);
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null);
        }

        if (paymentAuthRequest == null)
            chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
        else
            chain.doNext(paymentAuthRequest);

    }
}
