package com.example.capstondesign.ui.board.inboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.bulletin.board.DeleteBoardService;
import com.example.capstondesign.network.bulletin.board.comment.AddCommentService;
import com.example.capstondesign.network.bulletin.board.comment.DeleteCommentService;
import com.example.capstondesign.repository.CommentRepository;

import java.io.IOException;


public class InBoardViewModel extends ViewModel {

    CommentRepository repository = new CommentRepository();
    public LiveData<Comment> comment = repository._comment;
    AddCommentService addCommentService = new AddCommentService();;

    DeleteCommentService deleteCommentService = new DeleteCommentService();
    DeleteBoardService deleteBoardService = new DeleteBoardService();

    public void loadComment() {
        repository.commentRepository();
    }

    public LiveData<Comment> getAll() {
        return comment;
    }

    public void addComment (Comment comment) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    addCommentService.execute(comment.getId(), comment.getNick(), comment.getComment(), comment.getTime(), comment.getEmail());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void deleteComment(String id, String time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    deleteCommentService.execute(id, time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void deleteBoard(String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    deleteBoardService.execute(id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
