package com.upi.sdk.processflow.vpa;

import com.rssoftware.upiint.schema.Vpa;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.InputAddVPA;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by JayantaC on 28-04-2016.
 */
public class AddVpaConverterSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        InputAddVPA input = (InputAddVPA)o;

        Vpa vpa = null;

        try {
            vpa = Converters.getConverter(InputAddVPA.class, Vpa.class).convert(input);
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null);
        }

        if (vpa == null)
            chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
        else
            chain.doNext(vpa);
    }
}
