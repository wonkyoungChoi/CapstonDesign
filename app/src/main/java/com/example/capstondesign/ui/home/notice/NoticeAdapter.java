package com.example.capstondesign.ui.home.notice;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.capstondesign.databinding.BoardListItemBinding;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.board.inboard.InBoardActivity;
import com.example.capstondesign.ui.home.notice.innotice.NoticeInside;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {
    public List<Notice> items = new ArrayList<>();
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
    public NoticeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = BoardListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoticeAdapter.MyViewHolder(mBinding);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(NoticeAdapter.MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        holder.setIsRecyclable(false);
        Notice notice = items.get(position);


        mBinding.nick.setText(notice.getNick());
        mBinding.title.setText(notice.getTitle());
        mBinding.text.setText(notice.getText().replace("\\n", System.getProperty("line.separator")));

        mBinding.layoutBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("===Click", "CLICK");
                Intent intent = new Intent(mBinding.layoutBoard.getContext(), NoticeInside.class);
                Integer id = notice.getId();
                String nick =  notice.getNick();
                String title = notice.getTitle();
                String text = notice.getText().replace("\\n", System.getProperty("line.separator"));


                intent.putExtra("id", id);
                intent.putExtra("nick", nick);
                intent.putExtra("title", title);
                intent.putExtra("text", text);


                mBinding.layoutBoard.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setNotice(ArrayList<Notice> list) {
        items = list;
    }



}
