package com.example.capstondesign.ui.board.inboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.capstondesign.network.bulletin.board.comment.AddCommentService;
import com.example.capstondesign.repository.CommentRepository;


public class CommentViewModel extends ViewModel {

    CommentRepository repository = new CommentRepository();
    public LiveData<Comment> comment = repository._comment;
    AddCommentService addCommentService = new AddCommentService();;

    public void loadComment() {
        repository.commentRepository();
    }

    public LiveData<Comment> getAll() {
        return comment;
    }

    public void addComment (Comment comment) {
        addCommentService.execute(comment.getId(), comment.getNick(), comment.getComment(), comment.getTime());
    }


}
