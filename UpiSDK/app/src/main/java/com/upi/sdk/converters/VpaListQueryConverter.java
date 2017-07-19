package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.ListParm;
import com.rssoftware.upiint.schema.UserEntityType;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InListVPAQuery;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;


/**
 * Created by JayantaC on 26-04-2016.
 */
public class VpaListQueryConverter implements Converter<InListVPAQuery, ListParm> {

    @Override
    public ListParm convert(InListVPAQuery in) throws ConversionException {
        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }

        ListParm param = new ListParm();
        param.setParmType(UserEntityType.VPA);
        param.setUserId(UpiSDKContext.getInstance().getUserId());
        param.setOwnerId(UpiSDKContext.getInstance().getUserId());
        param.setFetchActiveOnly(in.getFetchActiveVPAOnly());

        return param;
    }
}
