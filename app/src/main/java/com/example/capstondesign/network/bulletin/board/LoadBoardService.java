package com.example.capstondesign.network.bulletin.board;

import com.example.capstondesign.network.method.DownloadUrl;

import java.io.IOException;

public class LoadBoardService {
    DownloadUrl service = new DownloadUrl();
    public String download() throws IOException {
        return service.downloadUrl("boardjson.jsp");
    }
}