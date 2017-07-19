package com.upi.sdk.converters;

import com.rssoftware.upiint.schema.AddUpdateUserLimit;
import com.rssoftware.upiint.schema.BalanceEnquiry;
import com.rssoftware.upiint.schema.BeneficiaryDetails;
import com.rssoftware.upiint.schema.ConfigElement;
import com.rssoftware.upiint.schema.LinkAccount;
import com.rssoftware.upiint.schema.ListParm;
import com.rssoftware.upiint.schema.PaymentAuthRequest;
import com.rssoftware.upiint.schema.PaymentRequest;
import com.rssoftware.upiint.schema.UserValidation;
import com.rssoftware.upiint.schema.Vpa;
import com.upi.sdk.domain.BalanceEnquiryRequest;
import com.upi.sdk.domain.InListAddedBeneficiaryQuery;
import com.upi.sdk.domain.InListLinkAccountQuery;
import com.upi.sdk.domain.InListLinkAccountQueryByMobile;
import com.upi.sdk.domain.InListVPAQuery;
import com.upi.sdk.domain.InPayRequest;
import com.upi.sdk.domain.InputAddBankAccount;
import com.upi.sdk.domain.InPaymentAuthorization;
import com.upi.sdk.domain.InputAddUpdateUserLimit;
import com.upi.sdk.domain.InputAddVPA;
import com.upi.sdk.domain.InputBeneficiary;
import com.upi.sdk.domain.InputConfigParam;
import com.upi.sdk.domain.InputDeleteBankAccount;
import com.upi.sdk.domain.InputDeleteBeneficiary;
import com.upi.sdk.domain.InputDeleteVPA;
import com.upi.sdk.domain.InputUpdateBeneficiary;
import com.upi.sdk.domain.InputUpdateVPA;
import com.upi.sdk.domain.InputcheckVPAAvailability;
import com.upi.sdk.domain.OutPayRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SwapanP on 26-04-2016.
 */
public class Converters {
    private static Map<String, Converter> converterMap = null;
    static {
        converterMap = new HashMap<>();
        //Pay Request
        converterMap.put(createConversionKey(InPayRequest.class, PaymentRequest.class), new PayRequestConverter());
        converterMap.put(createConversionKey(InPaymentAuthorization.class, PaymentAuthRequest.class), new PayAuthRequestConverter());
        //Bank Account
        converterMap.put(createConversionKey(InputDeleteBankAccount.class, LinkAccount.class), new DeleteBankAccConverter());
        converterMap.put(createConversionKey(InListLinkAccountQuery.class, ListParm.class), new ListBankAccConverter());
        converterMap.put(createConversionKey(InputAddBankAccount.class, LinkAccount.class), new AddBankAccConverter());
        converterMap.put(createConversionKey(BalanceEnquiryRequest.class, BalanceEnquiry.class), new BalanceEnquiryRequestConverter());
        converterMap.put(createConversionKey(InListLinkAccountQueryByMobile.class, ListParm.class), new ListBankAccConverterByMobile());
        converterMap.put(createConversionKey(InputAddUpdateUserLimit.class, AddUpdateUserLimit.class), new AddUpdateUserLimitConverter());
        //VPA
        converterMap.put(createConversionKey(InListVPAQuery.class, ListParm.class), new VpaListQueryConverter());
        converterMap.put(createConversionKey(InputDeleteVPA.class, Vpa.class), new DeleteVpaConverter());
        converterMap.put(createConversionKey(InputUpdateVPA.class, Vpa.class), new UpdateVpaConverter());
        converterMap.put(createConversionKey(InputAddVPA.class, Vpa.class), new AddVpaConverter());
        converterMap.put(createConversionKey(InputcheckVPAAvailability.class, UserValidation.class), new UserValidationConverter());

        //Beneficiary
        converterMap.put(createConversionKey(InputBeneficiary.class, BeneficiaryDetails.class), new AddBeneficiaryConverter());
        converterMap.put(createConversionKey(InputUpdateBeneficiary.class, BeneficiaryDetails.class), new UpdateBeneficiaryConverter());
        converterMap.put(createConversionKey(InputDeleteBeneficiary.class, BeneficiaryDetails.class), new DeleteBeneficiaryConverter());
        converterMap.put(createConversionKey(InListAddedBeneficiaryQuery.class, BeneficiaryDetails.class), new ListBeneficiaryConverter());

        //Fetch Configuration
        converterMap.put(createConversionKey(InputConfigParam.class, ConfigElement.class), new ConfigParamConverter());



    }

    private static String createConversionKey(Class<?> inClass, Class<?> outClass) {
        return inClass.getName() + "|" + outClass.getName();
    }

    public static <I,O> Converter<I,O> getConverter(Class<I> inClass, Class<O> outClass) {
        String conversionKey = createConversionKey(inClass, outClass);
        return converterMap == null || !converterMap.containsKey(conversionKey) ?
                null : converterMap.get(conversionKey);
    }
}
