package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.LinkAccount;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InputDeleteBankAccount;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;

import java.math.BigInteger;

/**
 * Created by JayantaC on 26-04-2016.
 */
public class DeleteBankAccConverter implements Converter<InputDeleteBankAccount, LinkAccount> {

    @Override
    public LinkAccount convert(InputDeleteBankAccount in) throws ConversionException {
        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }

//        if (in.getCustomerRefId() == null) {
//            throw new ConversionException(UPIErrorCode.INVALID_USER);
//        }

        if (in.getNickName() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00102);
        }

        LinkAccount linkAccount = new LinkAccount();
        linkAccount.setUserId(UpiSDKContext.getInstance().getUserId());
        linkAccount.setNickName(in.getNickName());
        linkAccount.setLinkAccId(new BigInteger(in.getLinkAccId()));

        return linkAccount;
    }
}
