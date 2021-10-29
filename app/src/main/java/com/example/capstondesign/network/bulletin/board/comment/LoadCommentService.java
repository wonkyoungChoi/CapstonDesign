package com.example.capstondesign.network.bulletin.board.comment;

import com.example.capstondesign.network.method.DownloadUrl;

import java.io.IOException;

public class LoadCommentService {
    DownloadUrl service = new DownloadUrl();
    public String download() throws IOException {
        return service.downloadUrl("commentjson.jsp");
    }
}

