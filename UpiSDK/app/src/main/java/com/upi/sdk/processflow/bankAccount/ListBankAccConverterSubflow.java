package com.upi.sdk.processflow.bankAccount;

import com.rssoftware.upiint.schema.ListParm;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.InListLinkAccountQuery;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by JayantaC on 28-04-2016.
 */
public class ListBankAccConverterSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        InListLinkAccountQuery qry = (InListLinkAccountQuery)o;

        ListParm prm = null;

        try {
            prm = Converters.getConverter(InListLinkAccountQuery.class, ListParm.class).convert(qry);
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null);
        }

        if (prm == null)
            chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
        else
            chain.doNext(prm);
    }
}
