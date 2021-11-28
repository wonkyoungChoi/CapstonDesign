package com.example.capstondesign.ui.chatting;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.databinding.ChattingRoomItemBinding;
import com.example.capstondesign.ui.chatting.inchattingroom.InChattingRoom;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ChattingRoomAdapter extends RecyclerView.Adapter<ChattingRoomAdapter.MyViewHolder> {
    public String nick, name, email;
    public ArrayList<ChattingRoomData> items = new ArrayList<>();
    public String number;
    String strurl;
    int code;

    private ChattingRoomItemBinding mBinding;


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ChattingRoomItemBinding bind;
        public MyViewHolder(ChattingRoomItemBinding binding) {
            super(binding.getRoot());
            bind = binding;
        }

    }

    @NonNull
    @Override
    public ChattingRoomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = ChattingRoomItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(mBinding);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        int i = 0;

        holder.setIsRecyclable(false);
        ChattingRoomData chat = items.get(position);

        mBinding.msgLast.setText(chat.getMessage());
        mBinding.roomName.setText(chat.getOthernick());

        String url = "http://183.96.240.182:8080/test/" + chat.getOtheremail() + ".jpg";

        try {
            i = getResponseCode(url);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(i == 404) {
            url = "http://183.96.240.182:8080/test/king.png";
            Picasso.get().load(url).into(mBinding.otherImage);
        } else {
            Picasso.get().load(Uri.parse(url)).into(mBinding.otherImage);
        }

        mBinding.room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("===Click", "CLICK");
                Intent intent = new Intent(mBinding.room.getContext(), InChattingRoom.class);
                String mynick =  chat.getMynick();
                String othernick = chat.getOthernick();
                String msg = chat.getMessage();
                String myemail = chat.getMyemail();
                String otheremail = chat.getOtheremail();

                intent.putExtra("mynick", mynick);
                intent.putExtra("othernick", othernick);
                intent.putExtra("msg", msg);
                intent.putExtra("myemail", myemail);
                intent.putExtra("otheremail", otheremail);
                intent.putExtra("check", chat.isCheck());

                mBinding.room.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
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


    public ChattingRoomData getChat(int position) {
        return items != null ? items.get(position) : null;
    }

    public void setChattingRoom(ArrayList<ChattingRoomData> chattingArrayList) {
        items = chattingArrayList;
    }

}
