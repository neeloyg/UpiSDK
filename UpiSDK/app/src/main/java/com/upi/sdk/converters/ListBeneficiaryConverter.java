package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.BeneficiaryDetails;
import com.rssoftware.upiint.schema.ListParm;
import com.rssoftware.upiint.schema.UserEntityType;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InListAddedBeneficiaryQuery;
import com.upi.sdk.domain.InListLinkAccountQuery;
import com.upi.sdk.errors.ConversionException;

/**
 * Created by NeeloyG on 5/19/2016.
 */

public class ListBeneficiaryConverter implements Converter<InListAddedBeneficiaryQuery, BeneficiaryDetails> {

    @Override
    public BeneficiaryDetails convert(InListAddedBeneficiaryQuery in) throws ConversionException {
        /*if (in == null) {
            throw new ConversionException(UPIErrorCode.ILLEGAL_INPUTS);
        }

        if (in.getCustomerRefId() == null) {
            throw new ConversionException(UPIErrorCode.INVALID_USER);
        }*/

        //in.setCustomerRefId();
        BeneficiaryDetails param = new BeneficiaryDetails();
        param.setUserId(UpiSDKContext.getInstance().getUserId());

        return param;
    }
}
