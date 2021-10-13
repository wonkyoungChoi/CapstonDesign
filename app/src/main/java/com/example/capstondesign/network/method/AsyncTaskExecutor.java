package com.example.capstondesign.network.method;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class AsyncTaskExecutor<Params> {
    public static final String TAG = "AsyncTaskRunner";

    private static final Executor THREAD_POOL_EXECUTOR =
            new ThreadPoolExecutor(5, 128, 1,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean mIsInterrupted = false;

    protected void onPreExecute(){}
    protected abstract String doInBackground(Params... params) throws IOException;
    protected void onPostExecute(){}
    protected void onCancelled() {}
    public MutableLiveData<String> result = new MutableLiveData<>();

    @SafeVarargs
    public final void execute(Params... params) {
        THREAD_POOL_EXECUTOR.execute(() -> {
            try {
                checkInterrupted();
                mHandler.post(this::onPreExecute);

                checkInterrupted();
                result.postValue(doInBackground(params));

                checkInterrupted();
                mHandler.post(this::onPostExecute);
            } catch (InterruptedException ex) {
                mHandler.post(this::onCancelled);
            } catch (Exception ex) {
                Log.e(TAG, "executeAsync: " + ex.getMessage() + "\n");
            }
        });
    }

    private void checkInterrupted() throws InterruptedException {
        if (isInterrupted()){
            throw new InterruptedException();
        }
    }

    public void cancel(boolean mayInterruptIfRunning){
        setInterrupted(mayInterruptIfRunning);
    }

    public boolean isInterrupted() {
        return mIsInterrupted;
    }

    public void setInterrupted(boolean interrupted) {
        mIsInterrupted = interrupted;
    }

    public String start(String jsp_url, String sendMsg) throws IOException {
        String str, receiveMsg = null;
        URL url = new URL(jsp_url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestMethod("POST");
        OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
        osw.write(sendMsg);
        osw.flush();
        if (conn.getResponseCode() == conn.HTTP_OK) {
            InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuffer buffer = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            receiveMsg = buffer.toString();

        } else {
            Log.i("통신 결과", conn.getResponseCode() + "에러");
        }
        return receiveMsg;
    }
}
