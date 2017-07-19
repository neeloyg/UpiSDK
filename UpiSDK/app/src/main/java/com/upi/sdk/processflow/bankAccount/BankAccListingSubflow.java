package com.upi.sdk.processflow.bankAccount;

import com.rssoftware.upiint.schema.LinkAccountDetails;
import com.rssoftware.upiint.schema.ListParm;
import com.upi.sdk.domain.BankAccountBean;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;
import com.upi.sdk.utils.SDKUtils;

import java.util.List;

/**
 * Created by JayantaC on 28-04-2016.
 */
public class BankAccListingSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        final ListParm param = (ListParm)o;

        UppServices.listBankAccounts(param, new ServiceCallback<LinkAccountDetails[]>(LinkAccountDetails[].class) {
            @Override
            public void onSuccess(LinkAccountDetails[]result) {
                SDKUtils.LIST_ACCOUNT_MASKED.clear();
                for (int i=0;i<result.length;i++){
                    if(result[i].getBankAcnumber()!=null && result[i].getBankAcnumber().length()>6){
                        BankAccountBean bankAccountBean=new BankAccountBean();
                        bankAccountBean.setMaskedbankAcnumber(SDKUtils.getMaskedAccount(result[i].getBankAcnumber()));
                        bankAccountBean.setBankAcnumber(result[i].getBankAcnumber());
                        result[i].setBankAcnumber(bankAccountBean.getMaskedbankAcnumber());
                        SDKUtils.LIST_ACCOUNT_MASKED.add(bankAccountBean);
                    }

                }
                chain.doNext(result);
            }

            @Override
            public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                if (errors!=null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(),null);
                }else{
                    chain.breakChain(SDKErrorCodes.ERR00048.name(),null);
                }
            }
        });
    }
}
