package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.Vpa;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InputDeleteVPA;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;

/**
 * Created by JayantaC on 26-04-2016.
 */
public class DeleteVpaConverter implements Converter<InputDeleteVPA, Vpa> {

    @Override
    public Vpa convert(InputDeleteVPA in) throws ConversionException {
        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }


        if (in.getVPAID() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00107);
        }

        Vpa vpa =new Vpa();
        vpa.setOwnerId(UpiSDKContext.getInstance().getUserId());
        vpa.setVpaId(in.getVPAID());
        vpa.setVpa(in.getVpa());
        return vpa;
    }
}
