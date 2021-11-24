package com.example.capstondesign.ui.chatting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.capstondesign.databinding.FragmentChattingBinding;
import com.example.capstondesign.ui.board.BoardAdapter;

public class ChattingFragment extends Fragment {
    public ChattingRoomAdapter chattingRoomAdapter;
    static int clicked_item;
    public static String name;
    Boolean check;
    ChattingRoomViewModel model;
    FragmentChattingBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChattingFragment() {
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
    public static ChattingFragment newInstance(String param1, String param2) {
        ChattingFragment fragment = new ChattingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        model.loadChattingRoom();
        Log.d("===onResume", "1");
        super.onResume();
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
        model = new ViewModelProvider(this).get(ChattingRoomViewModel.class);

        binding = FragmentChattingBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        Log.d("===onCreate", "1");

        initRecyclerView();
        observeChattingRoomResult();

//        //채팅방을 클릭했을 경우의 이벤트
//        chattingRoomAdapter.setOnItemClickListener(new ChattingRoomAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View v, int pos) {
//                clicked_item = pos;
//                //내가 누른 채팅방의 이름과 mynick, othernick의 구별을 하는 boolean값
//                name = chattingRoomAdapter.getItem(pos).getNickname();
//                check = chattingRoomAdapter.getItem(pos).getRoom_check();
//                Intent intent = new Intent(getContext(), InChattingRoom.class);
//                intent.putExtra("name", name);
//                intent.putExtra("check", check);
//                startActivity(intent);
//
//            }
//
//            final String[] items = {"채팅방 이름 설정", "나가기"};
//            @Override
//            public void onItemLongClick(View v, int pos) {
//                setAlertDialog(items, getContext());
//            }
//        });


        // Inflate the layout for this fragment
        return v;
    }

    public void setAlertDialog(String[] items, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("채팅방 설정");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Toast.makeText(context, "채팅방 이름 설정 클릭", Toast.LENGTH_SHORT).show();
                } else if (which == 1) {
                    Toast.makeText(context, "나가기 클릭", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void initRecyclerView() {
        chattingRoomAdapter = new ChattingRoomAdapter();
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(chattingRoomAdapter);
    }

    private void observeChattingRoomResult() {
        model.getAll().observe(getViewLifecycleOwner(), chattingRoom -> {
            Log.d("===observeChattingRoom", "result");
            chattingRoomAdapter.setChattingRoom(chattingRoom.getList());
            chattingRoomAdapter.notifyDataSetChanged();
        });
    }

}
