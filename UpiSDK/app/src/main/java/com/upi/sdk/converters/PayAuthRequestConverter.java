package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.AuthStatusType;
import com.rssoftware.upiint.schema.PaymentAuthRequest;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InPaymentAuthorization;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class PayAuthRequestConverter implements Converter<InPaymentAuthorization, PaymentAuthRequest> {
    @Override
    public PaymentAuthRequest convert(InPaymentAuthorization in) throws ConversionException {
        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }

        PaymentAuthRequest paymentAuthRequest = new PaymentAuthRequest();
        paymentAuthRequest.setPayerAcNickName(in.getAccountNickName());
        paymentAuthRequest.setTxnId(in.getTxnId());
        paymentAuthRequest.setPayerAcVpa(in.getPaymentAddress());
        if(in.isSpam()){
            paymentAuthRequest.setAuthStatus(AuthStatusType.SPAM);
        }else{
            paymentAuthRequest.setAuthStatus(in.isAuthorized() ? AuthStatusType.ACCEPTED
                    : AuthStatusType.DECLINED);
        }
        paymentAuthRequest.setPayerAmt(in.getAmount());
        paymentAuthRequest.setPayerAmtCurrency(in.getCurrency());
        paymentAuthRequest.setUserId(UpiSDKContext.getInstance().getUserId());
        paymentAuthRequest.setPayerName(UpiSDKContext.getInstance().getUserName());
        paymentAuthRequest.setPayeeName(in.getPayeeName());
        paymentAuthRequest.setPayeeAcVpa(in.getPayeePaymentAddress());
        if (in.getCredAllowed() != null)
            paymentAuthRequest.setCredAllowed(in.getCredAllowed());
        if(in.getiStateListener()!=null)
            paymentAuthRequest.setiStateListener(in.getiStateListener());

        return paymentAuthRequest;
    }
}
