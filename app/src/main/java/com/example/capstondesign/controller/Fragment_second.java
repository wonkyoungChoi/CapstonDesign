package com.example.capstondesign.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.model.ChatAdapter;
import com.example.capstondesign.model.ChatData;
import com.example.capstondesign.model.ChatRoomAdapter;
import com.example.capstondesign.model.ChatRoomData;
import com.example.capstondesign.model.ChatTask;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Fragment_second extends Fragment {
    public static ChatRoomAdapter chatRoomAdapter;
    public static ArrayList<ChatRoomData> chatlist = new ArrayList<>();
    static int clicked_item;
    public static String name;
    Boolean check;
    ChatTask chatTask;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_second() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment_second.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_second newInstance(String param1, String param2) {
        Fragment_second fragment = new Fragment_second();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View v = inflater.inflate(R.layout.fragment_blank_second, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        Button button_add;
        button_add = v.findViewById(R.id.add);

        chatlist.clear();

        chatTask = new ChatTask();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        chatRoomAdapter = new ChatRoomAdapter(chatlist);

        recyclerView.setAdapter(chatRoomAdapter);

        //채팅방을 클릭했을 경우의 이벤트
        chatRoomAdapter.setOnItemClickListener(new ChatRoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                clicked_item = pos;
                //내가 누른 채팅방의 이름과 mynick, othernick의 구별을 하는 boolean값
                name = chatRoomAdapter.getItem(pos).getNickname();
                check = chatRoomAdapter.getItem(pos).getRoom_check();
                Intent intent = new Intent(getContext(), in_chat_room.class);
                intent.putExtra("name", name);
                intent.putExtra("chcek", check);
                startActivity(intent);

            }
        });

        //채팅방 추가 버튼
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), add_chat.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
