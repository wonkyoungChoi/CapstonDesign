package com.example.capstondesign.controller;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.example.capstondesign.R;
import com.example.capstondesign.view.ChatAdapter;
import com.example.capstondesign.model.ChatRoomData;
import com.example.capstondesign.model.ChatTask;
import com.example.capstondesign.model.ChattingRoomTask;


public class add_chat extends AppCompatActivity {
    Button add, cancel;
    EditText chattingroom, othernick;
    String my_room_name,other_room_name, other_nick;
    String mynick = ChatAdapter.nick;
    String message;
    ChattingRoomTask chattingRoomTask = new ChattingRoomTask();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_chat);

        chattingroom = findViewById(R.id.chatting_room_name);
        othernick = findViewById(R.id.other_nick);

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //상대방의 채팅방 이름은 내 닉네임
                other_room_name = mynick;
                other_nick = othernick.getText().toString();

                //내 채팅방의 이름은 상대방의 닉네임
                my_room_name = other_nick;

                //채팅방의 값을 데이터베이스에 저장하는 Task
                chattingRoomTask.execute(mynick, other_nick, my_room_name, other_room_name, message);
                ChatRoomData chatRoomData = new ChatRoomData(other_nick, "", true);
                ChatTask.chatlist.add(chatRoomData);
                Fragment_chatting.chatRoomAdapter.notifyDataSetChanged();

//                Intent intent = new Intent(getApplicationContext(), Fragment_main.class);
//                intent.putExtra("chatNum", 3);
//                startActivity(intent);
                finish();
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
