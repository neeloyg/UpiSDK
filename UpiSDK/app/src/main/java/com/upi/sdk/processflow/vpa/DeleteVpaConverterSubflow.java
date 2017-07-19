package com.upi.sdk.processflow.vpa;

import com.rssoftware.upiint.schema.Vpa;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.InputDeleteVPA;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by JayantaC on 28-04-2016.
 */
public class DeleteVpaConverterSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {
;
        InputDeleteVPA input = (InputDeleteVPA)o;
        Vpa _vpa = null;

        try {
            _vpa = Converters.getConverter(InputDeleteVPA.class, Vpa.class).convert(input);
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null);
        }

        if (_vpa == null)
            chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
        else
            chain.doNext(_vpa);
    }
}
