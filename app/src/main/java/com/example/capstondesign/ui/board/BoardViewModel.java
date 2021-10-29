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
        repository.boardRepository();
    }

    public LiveData<Board> getAll() {
        return board;
    }

    public void addBoard (Board board) {
        addBoardService.execute(board.getNick(), board.getTitle(), board.getText(), board.getTime());
    }



}
