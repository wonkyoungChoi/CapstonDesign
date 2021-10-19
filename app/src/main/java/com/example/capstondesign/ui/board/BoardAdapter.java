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
    public List<Board> items;
    public String nick;

    private BoardListItemBinding mBinding;


    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(BoardAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(BoardListItemBinding binding) {
            super(binding.getRoot());
            binding.layoutBoard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            Intent intent = new Intent(binding.layoutBoard.getContext(), InBoard.class);
                            String nick = items.get(pos).getNick();
                            String title = items.get(pos).getTitle();
                            String text = items.get(pos).getText();
                            String time = items.get(pos).getTime();

                            intent.putExtra("nick", nick);
                            intent.putExtra("title", title);
                            intent.putExtra("text", text);
                            intent.putExtra("time", time);

                            binding.layoutBoard.getContext().startActivity(intent);
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
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

        if(items.get(position).getImage() != null) {
            mBinding.nick.setText(board.getNick());
            mBinding.title.setText(board.getTitle());
            mBinding.text.setText(board.getText());
        } else {
            mBinding.nick.setText(board.getNick());
            mBinding.title.setText(board.getTitle());
            mBinding.text.setText(board.getText());
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

    public void setBoard(ArrayList<Board> boardArrayList) {
        items = boardArrayList;
        notifyDataSetChanged();
    }



}

