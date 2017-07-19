package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.UserValidation;
import com.rssoftware.upiint.schema.ValidationType;
import com.upi.sdk.domain.InputcheckVPAAvailability;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;

/**
 * Created by JayantaC on 26-04-2016.
 */
public class UserValidationConverter implements Converter<InputcheckVPAAvailability, UserValidation> {
    @Override
    public UserValidation convert(InputcheckVPAAvailability in) throws ConversionException {
        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }

        if (in.getUserVPA() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00023);
        }

        UserValidation userValParm =new UserValidation();
        userValParm.setValType(ValidationType.UNIQUE_VPA);
        userValParm.setUserVPA(in.getUserVPA());

        return userValParm;
    }
}
