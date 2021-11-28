package com.example.capstondesign.ui.home.notice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.repository.NoticeRepository;
import com.example.capstondesign.ui.board.Board;

public class NoticeViewmodel extends ViewModel {
    NoticeRepository repository = new NoticeRepository();

    public void loadNotice() {
        repository.noticeRepository();
    }

    public LiveData<Notice> getAll() {
        return repository._notice;
    }
}
