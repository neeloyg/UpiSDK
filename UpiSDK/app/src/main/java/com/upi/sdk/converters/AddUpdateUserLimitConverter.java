package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.AddUpdateUserLimit;
import com.rssoftware.upiint.schema.BeneficiaryDetails;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InListAddedBeneficiaryQuery;
import com.upi.sdk.domain.InputAddUpdateUserLimit;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;

/**
 * Created by NeeloyG on 5/19/2016.
 */

public class AddUpdateUserLimitConverter implements Converter<InputAddUpdateUserLimit, AddUpdateUserLimit> {

    @Override
    public AddUpdateUserLimit convert(InputAddUpdateUserLimit in) throws ConversionException {

        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }
        if (in.getHandler() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }
        if (in.getDailyVol() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }
        if (in.getDailyLimit() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }
        if (in.getWeeklyVol() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }
        if (in.getWeeklyLimit() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }
        if (in.getMonthlyVol() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }
        if (in.getMonthlyLimit() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }


        AddUpdateUserLimit addUpdateUserLimit = new AddUpdateUserLimit();
        addUpdateUserLimit.setUserId(UpiSDKContext.getInstance().getUserId());
        addUpdateUserLimit.setHandler(in.getHandler());
        addUpdateUserLimit.setDailyLimit(in.getDailyLimit());
        addUpdateUserLimit.setDailyVol(in.getDailyVol());
        addUpdateUserLimit.setMonthlyLimit(in.getMonthlyLimit());
        addUpdateUserLimit.setMonthlyVol(in.getMonthlyVol());
        addUpdateUserLimit.setWeeklyLimit(in.getWeeklyLimit());
        addUpdateUserLimit.setWeeklyVol(in.getWeeklyVol());


        return addUpdateUserLimit;

    }
}
