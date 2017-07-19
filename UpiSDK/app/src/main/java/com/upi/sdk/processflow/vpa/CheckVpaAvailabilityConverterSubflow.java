package com.upi.sdk.processflow.vpa;

import com.rssoftware.upiint.schema.UserValidation;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.InputcheckVPAAvailability;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by JayantaC on 28-04-2016.
 */
public class CheckVpaAvailabilityConverterSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        InputcheckVPAAvailability input = (InputcheckVPAAvailability)o;

        UserValidation ValParm = null;

        try {
            ValParm = Converters.getConverter(InputcheckVPAAvailability.class, UserValidation.class).convert(input);
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null);
        }

        if (ValParm == null)
            chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
        else
            chain.doNext(ValParm);
    }
}
