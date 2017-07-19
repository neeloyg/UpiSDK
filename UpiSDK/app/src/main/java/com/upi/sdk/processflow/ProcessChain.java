package com.upi.sdk.processflow;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.upi.sdk.domain.APICallback;
import com.upi.sdk.services.ServiceCallback;
import com.upi.sdk.services.WebServiceStatus;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SwapanP on 27-04-2016.
 */
public class ProcessChain {

    private List<ProcessSubflow> chain;
    private volatile int currentIndex;
    private volatile Object flowObject;
    private APICallback apiCallback;
    private ServiceCallback serviceCallback;
    private Handler handler = new Handler(Looper.getMainLooper());
    ProcessChain() {
        chain = new LinkedList<>();
        currentIndex = -1;
        flowObject = null;
        apiCallback = null;
        serviceCallback = null;
    }

    ProcessChain(Object flowObject, APICallback callback) {
        chain = new LinkedList<>();
        currentIndex = -1;
        this.flowObject = flowObject;
        this.apiCallback = callback;
    }

    ProcessChain(Object flowObject, ServiceCallback callback) {
        chain = new LinkedList<>();
        currentIndex = -1;
        this.flowObject = flowObject;
        this.serviceCallback = callback;
    }

    void add(ProcessSubflow subflow) {
        chain.add(subflow);
        Log.d("Process Flow", subflow.getClass().getName() + " added to process flow.");
    }

    void start() {
        Log.d("Process Flow", "Starting process flow.");
        doNext(flowObject);
    }

    public void doNext(final Object o) {
        ProcessFlowExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("Process Flow", "Executor Invoked");
                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                currentIndex++;
                flowObject = o;
                if (currentIndex < chain.size()) {
                    Log.d("Process Flow", "Executing current subflow - index: " + currentIndex + ", name: " + chain.get(currentIndex)
                            .getClass().getName());
                    chain.get(currentIndex).execute(flowObject, ProcessChain.this);
                } else {
                    Log.d("Process Flow", "Ending Process flow.");
                    if (apiCallback != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                apiCallback.onSuccess(o);
                            }
                        });
                        //apiCallback.onSuccess(o);
                        Log.d("Process Flow", "Process flow ended for API Callback.");
                    } else if (serviceCallback != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                serviceCallback.onSuccess(o);
                            }
                        });
                        //serviceCallback.onSuccess(o);
                        Log.d("Process Flow", "Process flow ended for Service Callback.");
                    } else {
                        Log.d("Process Flow", "Process flow ended");
                    }

                }
            }
        });

    }

    public void breakChain(final String sdkErrorCode, final String upiErrorCode) {
        if (sdkErrorCode != null) {
            Log.d("Process Flow", "Ending Process flow for API Callback with sdkerror " + sdkErrorCode);
        } else if (upiErrorCode != null) {
            Log.d("Process Flow", "Ending Process flow for API Callback with upierror" + upiErrorCode);

        }

        if (apiCallback != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    apiCallback.onFailure(sdkErrorCode,upiErrorCode);
                }
            });
            //apiCallback.onFailure(errorCode);
        }
        Log.d("Process Flow", "Process flow ended for API Callback.");
    }

    public void breakChainOnSuccess(final Object o) {
        Log.d("Process Flow", "Ending Process flow");
        if (apiCallback != null) {
            Log.d("Process Flow", "Ending Process flow for API Callback with success.");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    apiCallback.onSuccess(o);
                }
            });
            //apiCallback.onSuccess(o);
            Log.d("Process Flow", "Process flow ended for API Callback with success.");
            return;
        }

        if (serviceCallback != null) {
            Log.d("Process Flow", "Ending Process flow for Service Callback with success.");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    serviceCallback.onSuccess(o);
                }
            });
            //serviceCallback.onSuccess(o);
            Log.d("Process Flow", "Process flow ended for Service Callback with success.");
            return;
        }

    }

    public void breakChain(final WebServiceStatus status, final List<Error> errors) {
        if (errors != null && !errors.isEmpty()) {
            Log.d("Process Flow", "Ending process flow for Service Callback with error " + errors.get(0).getMessage());
        }
        if (serviceCallback != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    serviceCallback.onError(status, errors);
                }
            });
            //serviceCallback.onError(status, errors);
        }

        Log.d("Process Flow", "Process flow ended for Service Callback.");
    }

}
