package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.BalanceEnquiry;
import com.rssoftware.upiint.schema.UserType;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.BalanceEnquiryRequest;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class BalanceEnquiryRequestConverter implements Converter<BalanceEnquiryRequest, BalanceEnquiry> {
    @Override
    public BalanceEnquiry convert(BalanceEnquiryRequest in) throws ConversionException {
        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }

        BalanceEnquiry balanceEnquiry = new BalanceEnquiry();
        balanceEnquiry.setBank_name(in.getBank_name());
        balanceEnquiry.setNickname(in.getNickName());
        balanceEnquiry.setUserType(UserType.PERSON);
        balanceEnquiry.setNotes("Balance Enquiry");
        balanceEnquiry.setMobileNumber(in.getMobileNumber());
        balanceEnquiry.setBankAcNumber(in.getBankAcNumber());
       balanceEnquiry.setAccType(in.getAccType());
        balanceEnquiry.setOwnerId(UpiSDKContext.getInstance().getUserId());
        balanceEnquiry.setVpa(UpiSDKContext.getInstance().getUserDefaultVpa());
        if (in.getCredAllowed() != null)
            balanceEnquiry.setCredAllowed(in.getCredAllowed());
        if (in.getiStateListener() != null)
            balanceEnquiry.setiStateListener(in.getiStateListener());

        return balanceEnquiry;
    }
}
