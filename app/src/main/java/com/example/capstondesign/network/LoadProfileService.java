package com.example.capstondesign.network;

import com.example.capstondesign.network.method.DownloadUrl;

import java.io.IOException;

public class LoadProfileService {
    DownloadUrl service = new DownloadUrl();
    public String download() throws IOException {
        return service.downloadUrl("profilejson.jsp");
    }
}
