package com.upi.sdk.processflow.bankAccount;

import com.rssoftware.upiint.schema.LinkAccount;
import com.upi.sdk.converters.Converters;
import com.upi.sdk.domain.InputDeleteBankAccount;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;

/**
 * Created by JayantaC on 28-04-2016.
 */
public class DeleteBankAccConverterSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        InputDeleteBankAccount input = (InputDeleteBankAccount)o;
        LinkAccount linkAcc = null;

        try {
            linkAcc = Converters.getConverter(InputDeleteBankAccount.class, LinkAccount.class).convert(input);
        } catch (ConversionException e) {
            chain.breakChain(e.getErrorCode().name(),null);
        }

        if (linkAcc == null)
            chain.breakChain(SDKErrorCodes.ERR00098.name(),null);
        else
            chain.doNext(linkAcc);
    }
}
