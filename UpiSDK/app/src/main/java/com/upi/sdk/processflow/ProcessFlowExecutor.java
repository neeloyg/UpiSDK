package com.upi.sdk.processflow;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by rsadmin on 9/1/17.
 */

public class ProcessFlowExecutor {
    private ThreadPoolExecutor mExecutorThreadPool;
    private final BlockingQueue<Runnable> mDecodeWorkQueue;
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private static int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();
    private static ProcessFlowExecutor _ProcessFlowExecutor;
    static  {
        _ProcessFlowExecutor = new ProcessFlowExecutor();
    }
    private ProcessFlowExecutor(){
        mDecodeWorkQueue = new LinkedBlockingQueue<Runnable>();
        mExecutorThreadPool = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mDecodeWorkQueue){
            @Override
            protected void terminated(){
                super.terminated();
                Log.d("ProcessFlow", "terminated Executor terminated");
            }

            protected void afterExecute(Runnable r, Throwable t){
                super.afterExecute(r,t);
                Log.d("ProcessFlow", "afterExecute Execution completed");
                if(t != null){
                    t.printStackTrace();
                }
            }
        };
        Log.d("Process Flow", "Executors available threads : " + NUMBER_OF_CORES);
    }

    public static void execute(Runnable runnable){
       // Log.d("Neeloy", "ProcessFlowExecuter.execute invoked");
        _ProcessFlowExecutor.mExecutorThreadPool.execute(runnable);
    }
}
