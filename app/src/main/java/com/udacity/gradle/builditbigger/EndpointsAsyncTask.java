package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.clasence.shu.backend.myApi.MyApi;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

/**
 * Created by Neba on 17-Aug-17.
 */


public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private Activity context;
    private ProgressDialog progressDialog;
    private StartJobActivity startJobActivity=null;

    //define values to help in testing the asynctask

    private CountDownLatch countDownLatch;
    private String response;
    private boolean isTesting=false;

    public EndpointsAsyncTask(boolean isTesting,@Nullable Activity context, @Nullable  StartJobActivity startJobActivity, @Nullable CountDownLatch countDownLatch) {
        this.context = context;
        this.startJobActivity=startJobActivity;
        this.isTesting=isTesting;
        this.countDownLatch = countDownLatch;
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(context.getString(R.string.loading_joke));
    }

    public interface StartJobActivity{
        void startActivy(String result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(!isTesting) {
            progressDialog.show();
            Toast.makeText(context, context.getString(R.string.loading_joke), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //  .setRootUrl("https://gradle-project-177110.appspot.com/_ah/api/")
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }
        try {
            return myApiService.sayHi().execute().getData();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(isTesting){
            response = result;
            //if it is a test, count down the timere and return the server response
            //just a check
            if(countDownLatch!=null) {
                countDownLatch.countDown();
            }
        }else {
            progressDialog.hide();
            if (startJobActivity != null) {
                startJobActivity.startActivy(result);
            }
        }
    }

    public String getResponse(){
        return response;
    }
}
