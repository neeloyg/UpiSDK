package com.upi.sdk.converters;

import android.util.Log;

import com.rssoftware.upiint.schema.Payee;
import com.rssoftware.upiint.schema.Payer;
import com.rssoftware.upiint.schema.PaymentRequest;
import com.upi.sdk.core.UPPSDKConstants;
import com.upi.sdk.core.UpiSDKContext;
import com.upi.sdk.domain.InPayRequest;
import com.upi.sdk.domain.UpiPayee;
import com.upi.sdk.domain.UpiPayer;
import com.upi.sdk.errors.ConversionException;
import com.upi.sdk.errors.SDKErrorCodes;

import java.math.BigDecimal;

/**
 * Created by SwapanP on 26-04-2016.
 */
public class PayRequestConverter implements Converter<InPayRequest, PaymentRequest> {

    @Override
    public PaymentRequest convert(InPayRequest in) throws ConversionException {
        if (in == null) {
            throw new ConversionException(SDKErrorCodes.ERR00098);
        }

        if (in.getPayer() == null) {
            throw new ConversionException(SDKErrorCodes.ERR00108);
        }

        if (in.getPayees() == null
                || in.getPayees().isEmpty()) {
            throw new ConversionException(SDKErrorCodes.ERR00109);
        }

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setChannelCode(UPPSDKConstants.CURRENT_CHANNEL_TYPE);
        paymentRequest.setOwnerId(UpiSDKContext.getInstance().getUserId());
        paymentRequest.setNote(in.getNote());
        paymentRequest.setTxnType(in.getTxn_type());
        paymentRequest.setMinAmount(in.getMinAmount());


        if(in.getCredAllowed()!=null){
            paymentRequest.setCredAllowed(in.getCredAllowed());
        }

        if(in.getValidUpto()!=null)
        paymentRequest.setValidupto(in.getValidUpto());

        if(in.getiStateListener()!=null)
            paymentRequest.setiStateListener(in.getiStateListener());

        //For Pay Request...
        if(in.getTxn_type().equalsIgnoreCase("PAY")){
            if(in.getBank_name()!=null && in.getBank_name().length()>0)
                paymentRequest.setBank_name(in.getBank_name());
            if(in.getRefId()!=null && in.getRefId().length()>0)
                paymentRequest.setRefId(in.getRefId());
            Payer payer = new Payer();
            UpiPayer inPayer = in.getPayer();
            payer.setPayerAcVpa(inPayer.getVirtualPaymentAddress());
            payer.setPayerAcNickName(inPayer.getAcNickName());
            //Demasking should be added...........
            payer.setBankAcNumber(in.getBankAcNumber());
            payer.setPayerAmt(inPayer.getAmount());
            payer.setPayerAmtCurrency(inPayer.getCurrency());
            paymentRequest.setPayer(payer);

            BigDecimal totalAmount = new BigDecimal(0);
            for (UpiPayee inPayee : in.getPayees()) {
                Payee payee = new Payee();
                payee.setPayeeAcVpa(inPayee.getVirtualPaymentAddress());
                payee.setName(inPayee.getName());
                payee.setPayeeAmt(inPayee.getAmount());
                payee.setPayeeAmtCurrency(inPayee.getCurrency());
                totalAmount = totalAmount.add(payee.getPayeeAmt());
                paymentRequest.getPayees().add(payee);
            }

            Log.d("Pay Req Converter", "Total Amount:" + totalAmount.toPlainString());
            Log.d("Pay Req Converter", "Payer Amount:" + payer.getPayerAmt().toPlainString());
            paymentRequest.setTxnTotalamt(Double.parseDouble(String.valueOf(totalAmount)));
            if (totalAmount.compareTo(payer.getPayerAmt()) != 0) {
                throw new ConversionException(SDKErrorCodes.ERR00110);
            }
        }else{
            //For collect Request...............
            Payer payer = new Payer();
            UpiPayer inPayer = in.getPayer();
            payer.setPayerAcVpa(inPayer.getVirtualPaymentAddress());
            payer.setPayerAcNickName(inPayer.getAcNickName());
            payer.setPayerAmt(inPayer.getAmount());
            if(inPayer.getCurrency()!=null)
            payer.setPayerAmtCurrency(inPayer.getCurrency());
            paymentRequest.setPayer(payer);

            BigDecimal totalAmount = new BigDecimal(0);
                Payee payee = new Payee();
                payee.setPayeeAcVpa(in.getPayees().get(0).getVirtualPaymentAddress());
                payee.setPayeeAcNickName(in.getPayees().get(0).getAcNickName());
                payee.setPayeeAmt(in.getPayees().get(0).getAmount());
                if(in.getPayees().get(0)!=null)
                payee.setPayeeAmtCurrency(in.getPayees().get(0).getCurrency());
                totalAmount = totalAmount.add(payee.getPayeeAmt());
                paymentRequest.getPayees().add(payee);

            Log.d("Collect Req Converter", "Total Amount:" + totalAmount.toPlainString());
            Log.d("Collect Req Converter", "Payer Amount:" + payer.getPayerAmt().toPlainString());
            paymentRequest.setTxnTotalamt(Double.parseDouble(String.valueOf(totalAmount)));
            if (totalAmount.compareTo(payer.getPayerAmt()) != 0) {
                throw new ConversionException(SDKErrorCodes.ERR00110);
            }
        }



        return paymentRequest;
    }
}
