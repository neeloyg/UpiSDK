package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.ListParm;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InListLinkAccountQueryByMobile;
import com.upi.sdk.errors.ConversionException;

/**
 * Created by JayantaC on 26-04-2016.
 */
public class ListBankAccConverterByMobile implements Converter<InListLinkAccountQueryByMobile, ListParm> {

    @Override
    public ListParm convert(InListLinkAccountQueryByMobile in) throws ConversionException {
        /*if (in == null) {
            throw new ConversionException(UPIErrorCode.ILLEGAL_INPUTS);
        }

        if (in.getCustomerRefId() == null) {
            throw new ConversionException(UPIErrorCode.INVALID_USER);
        }*/

        //in.setCustomerRefId();
        ListParm param = new ListParm();
//        param.setParmType(UserEntityType.ACCOUNT);
        param.setOwnerId(UpiSDKContext.getInstance().getUserId());
        param.setIfsc(in.getIfsc());

        return param;
    }
}
