package com.example.capstondesign.network.bulletin.groupbuying;

import com.example.capstondesign.network.method.DownloadUrl;

import java.io.IOException;

public class LoadGroupBuyingService {
    DownloadUrl service = new DownloadUrl();
    public String download() throws IOException {
        return service.downloadUrl("groupbuyingjson.jsp");
    }
}

