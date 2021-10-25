package com.example.capstondesign.ui.board;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.bulletin.board.AddBoardService;
import com.example.capstondesign.repository.BoardRepository;
import java.io.IOException;

public class BoardViewModel extends ViewModel {
    BoardRepository repository = new BoardRepository();
    public LiveData<Board> board = repository._board;
    AddBoardService addBoardService = new AddBoardService();;

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

    public void addBoard (String nick, String title, String text, String time) {
        addBoardService.execute(nick, title, text, time);
    }

}
