package com.example.capstondesign.network.bulletin.groupbuying;

import android.util.Log;

import com.example.capstondesign.model.Profile;
import com.example.capstondesign.network.method.DownloadUrl;
import com.example.capstondesign.ui.groupbuying.Groupbuying;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.profile.myactivity.mygroupbuying.MyGroupBuyingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LoadGroupBuyingService {
    DownloadUrl service = new DownloadUrl();
    public String download() throws IOException {
        return service.downloadUrl("groupbuyingjson.jsp");
    }
}


