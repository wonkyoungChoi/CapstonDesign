package com.example.capstondesign.ui.board;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.capstondesign.repository.BoardRepository;
import java.io.IOException;

public class BoardViewModel extends ViewModel {
    BoardRepository repository = new BoardRepository();
    private final LiveData<Board> board = repository._board;

    public void loadBoard() {
        try {
            repository.loadBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LiveData<Board> getAll() {
        return board;
    }

}
