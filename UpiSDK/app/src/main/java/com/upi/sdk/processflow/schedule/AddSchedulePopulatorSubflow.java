package com.upi.sdk.processflow.schedule;

import com.rssoftware.upiint.schema.Notifications;
import com.upi.sdk.domain.InputNotification;
import com.upi.sdk.domain.InputSchedule;
import com.upi.sdk.domain.Schedule;
import com.upi.sdk.errors.SDKErrorCodes;
import com.upi.sdk.processflow.ProcessChain;
import com.upi.sdk.processflow.ProcessSubflow;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.UppServices;
import com.upi.sdk.services.WebServiceStatus;

import java.util.List;

/**
 * Created by Neeloy on 04-01-2017.
 */
public class AddSchedulePopulatorSubflow implements ProcessSubflow {

    @Override
    public void execute(Object o, final ProcessChain chain) {

        InputSchedule historyQuery = (InputSchedule) o;

        UppServices.addSchedule(historyQuery, new ServiceCallback<Schedule>(Schedule.class) {
            @Override
            public void onSuccess(Schedule result) {
                // TODO We can convert the bean if required
                chain.doNext("Schedule added successfully");
            }

            @Override
            public void onError(WebServiceStatus status, List<com.rssoftware.upiint.schema.Error> errors) {
                if (errors != null && errors.size() > 0) {
                    chain.breakChain(errors.get(0).getErrorCode(), null);
                } else {
                    chain.breakChain(SDKErrorCodes.ERR00000.name(), null);
                }
            }
        });
    }
}
