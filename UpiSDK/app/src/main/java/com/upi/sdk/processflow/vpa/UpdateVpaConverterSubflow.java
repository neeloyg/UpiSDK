package com.upi.sdk.processflow.vpa;

import com.rssoftware.upiint.schema.Vpa;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.InputUpdateVPA;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by JayantaC on 28-04-2016.
 */
public class UpdateVpaConverterSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        InputUpdateVPA input = (InputUpdateVPA)o;

        Vpa _vpa = null;

        try {
            _vpa = Converters.getConverter(InputUpdateVPA.class, Vpa.class).convert(input);
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null);
        }

        if (_vpa == null)
            chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
        else
            chain.doNext(_vpa);
    }
}
