package com.example.capstondesign.ui.chatting.inchattingroom;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.capstondesign.databinding.ActivityInChattingroomBinding;
import com.example.capstondesign.ui.chatting.ChattingRoomViewModel;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class InChattingRoom extends AppCompatActivity {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference();
    ChattingAdapter chatAdapter;
    List<ChattingData> chatlist = new ArrayList<>();
    ChattingData chatData;

    private static final String TAG = "Fragment_chatting";

    ChildEventListener childEventListener;
    public static String key;
    String mynick, othernick;
    String roomName;
    Boolean check;
    TextView chat_nick_name;

    ActivityInChattingroomBinding binding;
    ChattingRoomViewModel model;


    @Override
    public void onPause() {
        myRef.child("Chat").child(roomName).removeEventListener(childEventListener);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInChattingroomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(ChattingRoomViewModel.class);

        initRecyclerView();

        Intent intent = getIntent();
        mynick = intent.getStringExtra("mynick");
        othernick = intent.getStringExtra("othernick");

        Log.d("Mynick", mynick);
        Log.d("Othernick", othernick);

        binding.chatNickName.setText(othernick);

        //방 이름을 미리 저장해둠
        check = intent.getExtras().getBoolean("check");
        //인텐트를 통해 가져온 Boolean 값을 통해 myname인지 othername인지 체크 후
        //othername일 경우 파이어채팅방 이름을 다시 othername으로 바꿔줌
        // mynick과 othernick의 채팅방이 두개로 만들어지면 안되기 때문
        if(!check) {
            roomName = mynick + "&" +  othernick;
        } else {
            roomName = othernick + "&" + mynick;
        }

        Log.d("===RoomName", roomName);

        chatlist.clear();

        Log.d("DATABASE", String.valueOf(database.getReference()));

        //채팅을 불러오는 부분
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                chatData = dataSnapshot.getValue(ChattingData.class);
                chatAdapter.addChat(chatData);
                binding.recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.child("Chat").child(roomName).addChildEventListener(childEventListener);



        //메시지 보내기 버튼 클릭
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = myRef.push().getKey();
                String msg = binding.EditTextChat.getText().toString();
                ChattingData chat = new ChattingData(mynick , msg, key, LoginAcitivity.profile.getEmail(), LoginAcitivity.profile.getName());
                Log.d("SEND", mynick + "msg" + msg);
                //Realtime Database의 Chat 안의 name안의 key에 값을 저장
                //key는 보내기 할 때마다 값이 변경되므로 값이 중복 저장되지 않음
                myRef.child("Chat").child(roomName).child(key).setValue(chat);
                //마지막 메시지를 데이터베이스에 업데이트 하는 Task
                model.updateLastMessage(msg, mynick, othernick);
                if(chatAdapter.chatData.size()>1) {
                    binding.recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
                }
                binding.EditTextChat.setText(null);
            }
        });


        binding.chatExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView() {
        chatAdapter = new ChattingAdapter();
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(chatAdapter);

    }
}

