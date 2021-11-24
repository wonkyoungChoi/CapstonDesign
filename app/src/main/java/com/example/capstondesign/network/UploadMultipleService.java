package com.example.capstondesign.network;

import com.example.capstondesign.network.method.OkhttpNetwork;

import java.io.File;

import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadMultipleService {
    OkhttpNetwork okhttpNetwork = new OkhttpNetwork();
    String url = "fileUpload.jsp";
    RequestBody formbody;

    public void enqueue(Callback callback, String... strings) {
        formbody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", strings[0], RequestBody.create(MultipartBody.FORM, new File(strings[1])))
                .build();
        okhttpNetwork.enQueue(url, formbody, callback);
    }
}
