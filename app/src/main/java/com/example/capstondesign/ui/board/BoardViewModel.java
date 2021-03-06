package com.example.capstondesign.ui.board;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.UploadService;
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
    AddBoardService addBoardService = new AddBoardService();

    public void loadBoard() {
        repository.boardRepository();
    }

    public LiveData<Board> getAll() {
        return board;
    }

    public void addBoard (Board board) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    addBoardService.execute(board.getNick(), board.getTitle(), board.getText(), board.getTime(), board.getEmail());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void addPicture(String filename, String sourceFileUri) {
        new UploadService().enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                Log.d("UploadFile", "됐다");
            }
        }, filename, sourceFileUri);
    }

    public void loadSearchBoard(String title) {
        repository.BoardSearchRepository(title);
    }

    public LiveData<Board> getSearchBoard() {return repository._searchBoard;}


}
