package com.example.capstondesign.ui.board;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.PictureUploadService;
import com.example.capstondesign.network.bulletin.board.AddBoardService;
import com.example.capstondesign.repository.BoardRepository;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BoardViewModel extends ViewModel {
    BoardRepository repository = new BoardRepository();
    public LiveData<Board> board = repository._board;
    AddBoardService addBoardService = new AddBoardService();;

    public void loadBoard() {
        repository.boardRepository();
    }

    public LiveData<Board> getAll() {
        return board;
    }

    public void addBoard (Board board) throws IOException {
        addBoardService.execute(board.getNick(), board.getTitle(), board.getText(), board.getTime());
    }

    public void addPicture(String filename, String sourceFileUri) {
        new PictureUploadService().enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                Log.d("UploadFile", "됐다");
            }
        }, filename, sourceFileUri);
    }



}
