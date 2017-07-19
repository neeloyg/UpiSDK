package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.Vpa;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InputUpdateVPA;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;

import java.math.BigInteger;

/**
 * Created by JayantaC on 26-04-2016.
 */
public class UpdateVpaConverter implements Converter<InputUpdateVPA, Vpa> {

    @Override
    public Vpa convert(InputUpdateVPA in) throws ConversionException {
        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }

//        if (in.getCustomerRefId() == null) {
//            throw new ConversionException(UPIErrorCode.INVALID_USER);
//        }

        if (in.getVPAID() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00107);
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

        if(in.getPaymentAccountID() == null){
            throw new ConversionException(SDKErrorCodes.ERR00111);
        }

        if(in.getCollectAccountID() == null){
            throw new ConversionException(SDKErrorCodes.ERR00112);
        }

        Vpa vpa =new Vpa();
        vpa.setOwnerId(UpiSDKContext.getInstance().getUserId());
        vpa.setVpaId(in.getVPAID());
        vpa.setVpa(in.getUserVPA());
        vpa.setValidUpto(in.getValidUpto());
        vpa.setMaxLimit(in.getMaxLimit());
        vpa.setIsOneTime(in.isOneTimeUse());
        vpa.setSelectivePayee(in.getIsForSelectivePayee());
        vpa.setPayAccId(in.getPaymentAccountID());
        vpa.setCollectAccId(in.getCollectAccountID());
        if(in.getAcctLinkRuleId()!=null && in.getAcctLinkRuleId().length()>0)
            vpa.setAcctLinkRuleId(new BigInteger(in.getAcctLinkRuleId()));
        if(in.getSelectivePayees()!=null && in.getSelectivePayees().size()>0) {
            vpa.getPayees().addAll(in.getSelectivePayees());
        }else {
          //  vpa.getPayees().addAll(in.getSelectivePayees());
        }

        return vpa;
    }
}
