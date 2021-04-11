package com.example.capstondesign.model;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.controller.Fragment_second;
import com.example.capstondesign.controller.in_chat_room;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    public static String nick;
    private static final String TAG = "ChatAdapter";
    public static ChatData chat;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView TextView_nickname;
        public TextView TextView_msg;
        public CardView myprofileImage, otherprofileImage;
        public ImageView myImage, otherImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView_nickname = itemView.findViewById(R.id.TextView_nickname);
            TextView_msg = itemView.findViewById(R.id.TextView_msg);
            myprofileImage = itemView.findViewById(R.id.mychatprofile);
            otherprofileImage = itemView.findViewById(R.id.otherchatprofile);
            myImage = itemView.findViewById(R.id.myImage);
            otherImage = itemView.findViewById(R.id.otherImage);

        }
    }

    public static List<ChatData> chatData;
    public ChatAdapter(List<ChatData> items) { chatData = items; }


    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        holder.setIsRecyclable(false);

        chat = chatData.get(position);

        holder.TextView_nickname.setText(chat.getNickname());
        holder.TextView_msg.setText(chat.getMessage()); //DTO
        //Picasso.get().load(Uri.parse(chat.getProfilePic())).into(holder.myImage);
        //Picasso.get().load(Uri.parse(chat.getProfilePic())).into(holder.otherImage);

        if(chat.getNickname()!=null && chat.getNickname().equals(nick)) {

            holder.myprofileImage.setVisibility(View.VISIBLE);
            holder.otherprofileImage.setVisibility(View.GONE);
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        } else {
            holder.myprofileImage.setVisibility(View.GONE);
            holder.otherprofileImage.setVisibility(View.VISIBLE);
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        }

    }

    @Override
    public int getItemCount() {
        return chatData.size();
    }


    public ChatData getChat(int position) {
        return chatData != null ? chatData.get(position) : null;
    }

    public void addChat(ChatData chat) {
        chatData.add(chat);
        notifyItemInserted(chatData.size()-1);
    }



}
