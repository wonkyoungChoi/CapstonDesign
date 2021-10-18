package com.example.capstondesign.ui.board;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.capstondesign.R;
import com.example.capstondesign.databinding.BoardListItemBinding;
import com.example.capstondesign.ui.board.inboard.InBoard;

import android.os.Build;
import android.util.Log;
import android.widget.LinearLayout;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.MyViewHolder> {
    static BoardAdapter.OnItemClickListener mListener = null;
    private List<Board> items = new ArrayList<>();
    public String nick;
    public Board board;


    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(BoardAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        BoardListItemBinding binding;

        public MyViewHolder(BoardListItemBinding bind) {
            super(bind.getRoot());
            binding = bind;



            binding.title.setText();

            binding.layoutBoard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            Intent intent = new Intent(binding.layoutBoard.getContext(), InBoard.class);
                            String click_nickname = items.get(pos).getNick();
                            String click_title = items.get(pos).getTitle();
                            String click_text = items.get(pos).getText();
                            String click_time = items.get(pos).getTime();
                            mListener.onItemClick(v, pos);

                        }
                    }
                }
            });
        }
    }


    public BoardAdapter(List<Board> item) { items = item; }

    @NonNull
    @Override
    public BoardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(BoardListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        holder.setIsRecyclable(false);
        board = items.get(position);

        if(items.get(position).getImage() != null) {
            holder.binding.nick.setText(items.get(position).getNick());
            holder.binding.title.setText(items.get(position).getTitle());
            holder.binding.text.setText(items.get(position).getText());
        } else {
            holder.binding.nick.setText(items.get(position).getNick());
            holder.binding.title.setText(items.get(position).getTitle());
            holder.binding.text.setText(items.get(position).getText());
            //holder.imageView.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public Board getChat(int position) {
        return items != null ? items.get(position) : null;
    }

    public void addBoard(Board board1) {
        items.add(board1);
        notifyItemInserted(items.size()-1);
    }



}

