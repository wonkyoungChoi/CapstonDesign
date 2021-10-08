package com.example.capstondesign.network;

import java.io.IOException;

public class BoardService {
    DownloadUrl service = new DownloadUrl();
    public String download() throws IOException {
        return service.downloadUrl("http://13.124.75.92:8080/boardjson.jsp");
    }
}
