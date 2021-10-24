package com.example.capstondesign.ui.board;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.bulletin.board.AddBoardTask;
import com.example.capstondesign.network.login.LoginService;
import com.example.capstondesign.repository.BoardRepository;
import java.io.IOException;

public class BoardViewModel extends ViewModel {
    BoardRepository repository = new BoardRepository();
    public MutableLiveData<Board> board = repository._board;
    AddBoardTask addBoardTask = new AddBoardTask();;

    public void loadBoard() {
        repository.boardRepository();
    }

    public LiveData<Board> getAll() {
        return board;
    }

    public void addBoard (Board board) {
        addBoardTask.execute(board.getNick(), board.getTitle(), board.getText(), board.getTime());
    }

}
