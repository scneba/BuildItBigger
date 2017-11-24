package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;


import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * Created by Neba
 */
public class EndpointsAsyncTaskTest extends InstrumentationTestCase {
    private static  CountDownLatch signal = new CountDownLatch(1);
    private static String response = null;
    private  EndpointsAsyncTask endpointsAsyncTas=null;

    public void testAsync() throws Throwable {
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                 endpointsAsyncTas= new EndpointsAsyncTask(true, null, null, signal);
                endpointsAsyncTas.execute();
            }
        });

        //10 seconds should be enough to pull jokes
        signal.await(10, TimeUnit.SECONDS);

        //my jokes are names joke1,joke2.....
        assertNotNull(endpointsAsyncTas.getResponse());
        assertEquals(endpointsAsyncTas.getResponse().trim().substring(0, 4), "joke");
    }
    public static void setResponseAndCountDown(String string){
        response=string;
        signal.countDown();
    }
}