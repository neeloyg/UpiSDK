package com.upi.sdk.processflow;

/**
 * Created by SwapanP on 27-04-2016.
 */
public interface ProcessSubflow {

    void execute(Object o, final ProcessChain chain);
}
