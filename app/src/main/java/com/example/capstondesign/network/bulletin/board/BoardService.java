package com.example.capstondesign.network.bulletin.board;

import com.example.capstondesign.network.method.DownloadUrl;

import java.io.IOException;

public class BoardService {
    DownloadUrl service = new DownloadUrl();
    public String download() throws IOException {
        return service.downloadUrl("http://172.121.251.102:8080/boardjson.jsp");
    }
}