package com.upi.sdk.processflow.beneficiary;

import com.rssoftware.upiint.schema.BeneficiaryDetails;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.InListAddedBeneficiaryQuery;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by NeeloyG on 5/19/2016.
 */

public class ListBeneficiaryConverterSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        InListAddedBeneficiaryQuery qry = (InListAddedBeneficiaryQuery)o;

        BeneficiaryDetails prm = null;

        try {
            prm = Converters.getConverter(InListAddedBeneficiaryQuery.class, BeneficiaryDetails.class).convert(qry);
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null);
        }

        if (prm == null)
            chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
        else
            chain.doNext(prm);
    }
}
