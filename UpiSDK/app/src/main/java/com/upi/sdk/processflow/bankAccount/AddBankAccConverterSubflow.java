package com.upi.sdk.processflow.bankAccount;

import com.rssoftware.upiint.schema.LinkAccount;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.InputAddBankAccount;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by JayantaC on 28-04-2016.
 */
public class AddBankAccConverterSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        InputAddBankAccount input = (InputAddBankAccount)o;

        LinkAccount linkAcc = null;

        try {
            linkAcc = Converters.getConverter(InputAddBankAccount.class, LinkAccount.class).convert(input);
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null);
        }

        if (linkAcc == null)
            chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
        else
            chain.doNext(linkAcc);
    }
}
