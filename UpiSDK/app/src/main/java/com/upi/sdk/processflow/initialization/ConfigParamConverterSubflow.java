package com.upi.sdk.processflow.initialization;

import com.rssoftware.upiint.schema.ConfigElement;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.InputConfigParam;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by NeeloyG on 5/19/2016.
 */

public class ConfigParamConverterSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        InputConfigParam qry = (InputConfigParam)o;

        ConfigElement prm = null;

        try {
            prm = Converters.getConverter(InputConfigParam.class, ConfigElement.class).convert(qry);
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null);
        }

        if (prm == null)
            chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
        else
            chain.doNext(prm);
    }
}
