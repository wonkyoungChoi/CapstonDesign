package com.example.capstondesign.network.method;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class AsyncTaskExecutor<Params> {
    public static final String TAG = "AsyncTaskRunner";

    private static final Executor THREAD_POOL_EXECUTOR =
            new ThreadPoolExecutor(5, 128, 1,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>());

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String start(String jsp_url, String sendMsg) throws IOException {
        String str, receiveMsg = null;
        URL url = new URL("http://172.121.251.102:8080/" + jsp_url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestMethod("POST");
        OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
        osw.write(sendMsg);
        osw.flush();
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder buffer = new StringBuilder();
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
