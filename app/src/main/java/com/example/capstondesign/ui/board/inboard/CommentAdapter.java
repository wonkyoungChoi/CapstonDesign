package com.example.capstondesign.ui.board.inboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.CommentListItemBinding;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    public List<Comment> items = new ArrayList<>();
    public String nick;
    int code;

    private CommentListItemBinding mBinding;
    public InBoardViewModel model = new InBoardViewModel();

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

        Log.d("===comment", comment.getId());
        Log.d("===comment", comment.getTime());
        mBinding.nick.setText(comment.getNick());
        mBinding.text.setText(comment.getComment());

        try {
            if(getResponseCode(comment.getEmail()) == 404) {
                Picasso.get().load(R.drawable.king).into(mBinding.imageView1);
            } else {
                Picasso.get().load(comment.getEmail()).into(mBinding.imageView1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mBinding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click", "CLick");
                if(LoginAcitivity.profile.getNickname().equals(items.get(position).getNick())) {
                    Log.d("CHECKC", "CHECKC");
                    new AlertDialog.Builder(holder.itemView.getContext())
                            .setTitle("댓글을 삭제하시겠습니까?")
                            .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("itemsId", items.get(position).getId());
                                    model.deleteComment(items.get(position).getId(), items.get(position).getTime());
                                    items.remove(position);
                                    Toast.makeText(holder.itemView.getContext(), "댓글 삭제 완료", Toast.LENGTH_SHORT).show();
                                    CommentAdapter.this.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                } else {
                    Toast.makeText(holder.itemView.getContext(), "본인의 댓글만 삭제할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public int getResponseCode(String urlString) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    URL u = new URL (urlString);
                    HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection ();
                    huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
                    huc.connect () ;
                    code = huc.getResponseCode() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
        return code;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setComment(ArrayList<Comment> commentArrayList) {
        items = commentArrayList;
    }



}
