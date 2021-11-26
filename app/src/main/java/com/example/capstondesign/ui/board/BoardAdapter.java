package com.example.capstondesign.ui.board;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.capstondesign.databinding.BoardListItemBinding;
import com.example.capstondesign.ui.board.inboard.InBoardActivity;
import com.example.capstondesign.ui.groupbuying.Groupbuying;
import com.example.capstondesign.ui.groupbuying.GroupbuyingFragment;
import com.example.capstondesign.ui.groupbuying.GroupbuyingViewModel;
import com.squareup.picasso.Picasso;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.MyViewHolder> {
    public List<Board> items = new ArrayList<>();
    public String nick;

    private BoardListItemBinding mBinding;



    public class MyViewHolder extends RecyclerView.ViewHolder{
        BoardListItemBinding bind;
        public MyViewHolder(BoardListItemBinding binding) {
            super(binding.getRoot());
            bind = binding;
        }
    }

    @NonNull
    @Override
    public BoardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = BoardListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(mBinding);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        holder.setIsRecyclable(false);
        Board board = items.get(position);


        mBinding.nick.setText(board.getNick());
        mBinding.title.setText(board.getTitle());
        mBinding.text.setText(board.getText());

        mBinding.layoutBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("===Click", "CLICK");
                Intent intent = new Intent(mBinding.layoutBoard.getContext(), InBoardActivity.class);
                Integer id = board.getId();
                String nick =  board.getNick();
                String title = board.getTitle();
                String text = board.getText();
                String time = board.getTime();
                String imageUrl = board.getImage();
                String email = board.getEmail();

                intent.putExtra("id", id);
                intent.putExtra("nick", nick);
                intent.putExtra("title", title);
                intent.putExtra("text", text);
                intent.putExtra("time", time);
                intent.putExtra("image", imageUrl);
                intent.putExtra("email", email);

                mBinding.layoutBoard.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setBoard(ArrayList<Board> boardArrayList) {
        items = boardArrayList;
    }



}

