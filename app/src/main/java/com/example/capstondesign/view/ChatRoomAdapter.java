package com.example.capstondesign.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.model.ChatRoomData;

import java.util.ArrayList;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.MyViewHolder> {
    static ChatRoomAdapter.OnItemClickListener mListener = null;
    public static ArrayList<ChatRoomData> items = null;


    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
        void onItemLongClick(View v, int pos);
    }

    public void setOnItemClickListener(ChatRoomAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
    public void setOnItemLongClickListener(ChatRoomAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public ChatRoomData getItem(int position) {return items.get(position);}

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView TextView_msg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.room_name);
            TextView_msg = itemView.findViewById(R.id.msg_last);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemLongClick(v, pos);
                        }
                    }
                    return false;
                }
            });

        }

        @SuppressLint("SetTextI18n")
        public void setItem(ChatRoomData item) {
            name.setText(item.getNickname() + " 님과의 대화");
            TextView_msg.setText(item.getMessage());
        }
    }


    public ChatRoomAdapter(ArrayList<ChatRoomData> item) { items = item; }


    @NonNull
    @Override
    public ChatRoomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatting_room_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        holder.setIsRecyclable(false);

        ChatRoomData chat = items.get(position);
        if(chat.getMessage().equals("null")) {
            chat.setMessage("메시지 없음");
        }
        holder.setItem(chat);
        //Picasso.get().load(Uri.parse(chat.getProfilePic())).into(holder.myImage);
        //Picasso.get().load(Uri.parse(chat.getProfilePic())).into(holder.otherImage);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public ChatRoomData getChat(int position) {
        return items != null ? items.get(position) : null;
    }

    public void addChat(ChatRoomData chat) {
        items.add(chat);
        notifyItemInserted(items.size()-1);
    }



}
