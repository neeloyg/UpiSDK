package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.BeneficiaryDetails;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InputBeneficiary;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;

/**
 * Created by NeeloyG on 5/19/2016.
 */
public class AddBeneficiaryConverter implements Converter<InputBeneficiary, BeneficiaryDetails> {

    @Override
    public BeneficiaryDetails convert(InputBeneficiary in) throws ConversionException {
        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }


        BeneficiaryDetails beneficiaryDetails = new BeneficiaryDetails();
        beneficiaryDetails.setUserId(UpiSDKContext.getInstance().getUserId());
        beneficiaryDetails.setBeneficiaryName(in.getBeneficiaryName());
        beneficiaryDetails.setPaymentAddress(in.getPaymentAddress());

        if(in.getMaxLimit()!=null)
            beneficiaryDetails.setMaxLimit(in.getMaxLimit());

        return beneficiaryDetails;
    }
}
