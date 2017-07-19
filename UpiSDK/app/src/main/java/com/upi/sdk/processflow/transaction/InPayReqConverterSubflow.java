package com.upi.sdk.processflow.transaction;

import com.rssoftware.upiint.schema.PaymentRequest;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.InPayRequest;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class InPayReqConverterSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, ProcessChain chain) {

        InPayRequest payRequest = (InPayRequest)o;

        PaymentRequest paymentRequest = null;
        try {
            paymentRequest = Converters.getConverter(InPayRequest.class, PaymentRequest.class).convert(payRequest);
        } catch (IllegalArgumentException e) {
            chain.breakChain(SDKErrorCodes.ERR00000.name(),null); // TODO: Throw proper error code
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null); // TODO: Throw proper error code
        }

        if (paymentRequest == null)
            chain.breakChain(SDKErrorCodes.ERR00000.name(),null); // TODO: Throw proper error code
        else
            chain.doNext(paymentRequest);

    }
}
