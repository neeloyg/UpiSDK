package com.upi.sdk.converters;

import android.text.TextUtils;

import com.rssoftware.upiint.schema.LinkAccount;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InputAddBankAccount;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;

/**
 * Created by JayantaC on 26-04-2016.
 */
public class AddBankAccConverter implements Converter<InputAddBankAccount, LinkAccount> {

    @Override
    public LinkAccount convert(InputAddBankAccount in) throws ConversionException {
        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }

        if (TextUtils.isEmpty(in.getNickName())) {
            throw new ConversionException(SDKErrorCodes.ERR00102);
        }

        if (TextUtils.isEmpty(in.getBankAcNumber())) {
            throw new ConversionException(SDKErrorCodes.ERR00095);
        }

        if (TextUtils.isEmpty(in.getIfsc())) {
            throw new ConversionException(SDKErrorCodes.ERR00104);
        }

        LinkAccount linkAccount = new LinkAccount();
        linkAccount.setUserId(UpiSDKContext.getInstance().getUserId());
        linkAccount.setNickName(in.getNickName());
        linkAccount.setBankAcnumber(in.getBankAcNumber());
        linkAccount.setIfsc(in.getIfsc());
        linkAccount.setMeba(in.isMeba());
        if(in.getAccType()!=null)
        linkAccount.setAccType(in.getAccType());
        if(in.getAccetptedCred()!=null)
            linkAccount.setAccetptedCred(in.getAccetptedCred());
        if(in.getCredDataType()!=null)
            linkAccount.setCredDataType(in.getCredDataType());
        if(in.getCredLength()!=null)
            linkAccount.setCredLength(in.getCredLength());
        if(in.getAtmCredDataType()!=null)
            linkAccount.setAtmCredDataType(in.getAtmCredDataType());
        if(in.getAtmCredLength()!=null)
            linkAccount.setAtmCredLength(in.getAtmCredLength());
        if(in.getOtpCredDataType()!=null)
            linkAccount.setOtpCredDataType(in.getOtpCredDataType());
        if(Integer.toString(in.getOtpCredLength())!=null)
            linkAccount.setOtpCredLength(in.getOtpCredLength());


        return linkAccount;
    }
}
