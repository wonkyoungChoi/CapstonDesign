package com.example.capstondesign.network.method;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUrl {
    public String downloadUrl(String url) throws IOException {
        String s = null;
        byte[] buffer = new byte[10000];
        InputStream iStream = null;
        try {
            URL url1 = new URL("http://172.121.251.102:8080/" + url);
            HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();
            iStream.read(buffer);
            s = new String(buffer);
        } catch (Exception e) {
            Log.d("Exception download", e.toString());
        } finally {
            if(iStream != null) {
                iStream.close();
            } else
                Log.d("iStreamNull", "iStreamNull");
        }
        return s;
    }
}
