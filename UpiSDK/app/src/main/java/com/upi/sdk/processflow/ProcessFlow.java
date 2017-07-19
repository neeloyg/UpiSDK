package com.upi.sdk.processflow;

import android.util.Log;

import com.upi.sdk.domain.APICallback;
import com.upi.sdk.services.ServiceCallback;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class ProcessFlow {

    private ProcessChain chain;

    private ProcessFlow(Object o, APICallback callback) {
        chain = new ProcessChain(o, callback);
    }
    private ProcessFlow(Object o, ServiceCallback callback) {
        chain = new ProcessChain(o, callback);
    }

    private ProcessFlow() {
        chain = new ProcessChain();
    }

    public static ProcessFlow create() {
        return new ProcessFlow();
    }

    public static ProcessFlow create(Object o, APICallback callback) {
        return new ProcessFlow(o, callback);
    }

    public static ProcessFlow create(Object o, ServiceCallback callback) {
        return new ProcessFlow(o, callback);
    }

    public ProcessFlow addNext(ProcessSubflow subflow) {
        if (subflow != null) {
            Log.d("Process Flow", "Adding " + subflow.getClass().getName() + " to process flow.");
            this.chain.add(subflow);
        }
        return this;
    }

    public void execute() {
        chain.start();
    }

}
