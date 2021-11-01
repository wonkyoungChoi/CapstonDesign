package com.example.capstondesign.ui.board.inboard;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.databinding.CommentListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    public List<Comment> items = new ArrayList<>();
    public String nick;

    private CommentListItemBinding mBinding;
    public InBoardViewModel model;

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
