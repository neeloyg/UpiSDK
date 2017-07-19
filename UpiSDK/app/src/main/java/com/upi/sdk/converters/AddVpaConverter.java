package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.Vpa;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InputAddVPA;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;

import java.math.BigInteger;

/**
 * Created by SwapanP on 26-04-2016.
 */
public class AddVpaConverter implements Converter<InputAddVPA, Vpa> {

    @Override
    public Vpa convert(InputAddVPA in) throws ConversionException {
        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }

        if (in.getUserVPA() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00023);
        }

        if (in.getMaxLimit() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00105);
        }

        if(in.getIsForSelectivePayee() && in.getSelectivePayees() == null){
            throw new ConversionException(SDKErrorCodes.ERR00106);
        }

/*
        if(in.getPaymentAccountID() == null){
            throw new ConversionException(UPIErrorCode.INVALID_PAYMENT_ACC);
        }

        if(in.getCollectAccountID() == null){
            throw new ConversionException(UPIErrorCode.INVALID_COLLECTION_ACC);
        }
*/


        Vpa vpa =new Vpa();
        vpa.setOwnerId(UpiSDKContext.getInstance().getUserId());
        vpa.setVpa(in.getUserVPA());
        vpa.setValidUpto(in.getValidUpto());
        vpa.setMaxLimit(in.getMaxLimit());
        vpa.setIsOneTime(in.isOneTimeUse());
        vpa.setSelectivePayee(in.getIsForSelectivePayee());
        vpa.setPayAccId(in.getPaymentAccountID());
        vpa.setCollectAccId(in.getCollectAccountID());
        if(in.getAcctLinkRuleId()!=null && in.getAcctLinkRuleId().length()>0)
        vpa.setAcctLinkRuleId(new BigInteger(in.getAcctLinkRuleId()));
        if(in.getIsForSelectivePayee())
            vpa.getPayees().addAll(in.getSelectivePayees());

        return vpa;
    }
}
