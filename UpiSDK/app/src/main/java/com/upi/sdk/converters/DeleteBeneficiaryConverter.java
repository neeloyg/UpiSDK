package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.BeneficiaryDetails;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InputDeleteBeneficiary;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;

/**
 * Created by NeeloyG on 5/19/2016.
 */
public class DeleteBeneficiaryConverter implements Converter<InputDeleteBeneficiary, BeneficiaryDetails> {

    @Override
    public BeneficiaryDetails convert(InputDeleteBeneficiary in) throws ConversionException {
        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }


        BeneficiaryDetails beneficiaryDetails = new BeneficiaryDetails();
        beneficiaryDetails.setUserId(UpiSDKContext.getInstance().getUserId());
        beneficiaryDetails.setBeneficiaryId(in.getBeneficiaryId());

        return beneficiaryDetails;
    }
}
