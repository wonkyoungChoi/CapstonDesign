package com.example.capstondesign.ui.chatting.inchattingroom;

import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.databinding.ChatItemBinding;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChattingAdapter extends RecyclerView.Adapter<ChattingAdapter.MyViewHolder> {
    public String nick, name, email;
    int code;

    public List<ChattingData> chatData = new ArrayList<>();

    private ChatItemBinding mBinding;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ChatItemBinding bind;
        public MyViewHolder(ChatItemBinding binding) {
            super(binding.getRoot());
            bind = binding;
        }
    }

    @NonNull
    @Override
    public ChattingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = ChatItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(mBinding);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        holder.setIsRecyclable(false);

        ChattingData chat = chatData.get(position);

        int i = 0;

        String strurl;
        strurl = "http://121.162.202.209:8080/test/" + chat.getEmail() +  ".jpg";
        try {
            i = getResponseCode(strurl);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mBinding.TextViewOthernickname.setText(chat.getNickname());
        //DTO

        if(chat.getNickname()!=null && chat.getNickname().equals(LoginAcitivity.profile.getNickname())) {
            try {
                if(i == 404) {
                    strurl = "http://121.162.202.209:8080/test/king.png";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("URL", strurl);

            mBinding.otherImage.setVisibility(View.GONE);
            mBinding.TextViewMymsg.setVisibility(View.VISIBLE);
            mBinding.TextViewMymsg.setText(chat.getMessage());
        } else {
            name = chat.getName();
            email = chat.getEmail();
            Log.d("CHATADAPTER", name);
            try {
                if(i == 404) {
                    strurl = "http://121.162.202.209:8080/test/king.png";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("URL", strurl);
            mBinding.otherchatprofile.setVisibility(View.VISIBLE);
            Picasso.get().load(Uri.parse(strurl)).into(mBinding.otherImage);

            mBinding.otherImage.setVisibility(View.VISIBLE);
            mBinding.TextViewOthermsg.setVisibility(View.VISIBLE);
            mBinding.TextViewOthermsg.setText(chat.getMessage());
            mBinding.TextViewOthernickname.setVisibility(View.VISIBLE);
            mBinding.TextViewOthernickname .setText(chat.getNickname());
        }

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
        return chatData.size();
    }


    public ChattingData getChat(int position) {
        return chatData != null ? chatData.get(position) : null;
    }

    public void addChat(ChattingData chat) {
        chatData.add(chat);
        notifyItemInserted(chatData.size()-1);
    }



}
