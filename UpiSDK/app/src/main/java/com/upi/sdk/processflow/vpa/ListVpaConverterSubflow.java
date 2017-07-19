package com.upi.sdk.processflow.vpa;

import com.rssoftware.upiint.schema.ListParm;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.InListVPAQuery;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by JayantaC on 28-04-2016.
 */
public class ListVpaConverterSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {
;
        InListVPAQuery qry = (InListVPAQuery)o;
        ListParm lstParam = null;

        try {
            lstParam = Converters.getConverter(InListVPAQuery.class, ListParm.class).convert(qry);
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null);
        }

        if (lstParam == null)
            chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
        else
            chain.doNext(lstParam);
    }
}
