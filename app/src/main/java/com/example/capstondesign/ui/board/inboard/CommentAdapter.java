package com.example.capstondesign.ui.board.inboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.BoardListItemBinding;
import com.example.capstondesign.databinding.CommentListItemBinding;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.board.BoardAdapter;
import com.example.capstondesign.ui.board.BoardFragment;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.board.inboard.Comment;
import com.example.capstondesign.model.DeleteCommentTask;
import com.example.capstondesign.model.Profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    public List<Comment> items = new ArrayList<>();
    public String nick;

    private CommentListItemBinding mBinding;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CommentListItemBinding bind;
        public MyViewHolder(CommentListItemBinding binding) {
            super(binding.getRoot());
            bind = binding;
        }
    }

    @NonNull
    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = CommentListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentAdapter.MyViewHolder(mBinding);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(CommentAdapter.MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        holder.setIsRecyclable(false);
        Comment comment = items.get(position);

        mBinding.nick.setText(comment.getNick());
        mBinding.text.setText(comment.getComment());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setComment(ArrayList<Comment> commentArrayList) {
        items = commentArrayList;
    }



}
