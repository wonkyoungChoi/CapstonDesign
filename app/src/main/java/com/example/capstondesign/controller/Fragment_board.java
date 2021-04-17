package com.example.capstondesign.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Board;
import com.example.capstondesign.model.BoardAdapter;

import java.util.Vector;

import static android.app.Activity.RESULT_OK;

public class Fragment_board extends Fragment {

    private final int GET_STRING = 1;


    Vector<Board> board = null;
    BoardAdapter boardAdapter;
    ListView listView, comment_list;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_board() {
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
    public static Fragment_board newInstance(String param1, String param2) {
        Fragment_board fragment = new Fragment_board();
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
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_STRING && resultCode == RESULT_OK && data != null) {

            boardAdapter.add(new Board(data.getStringExtra("Input_Text"), data.getStringExtra("Input_Title")));
            boardAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);

        board = new Vector<>();

        boardAdapter = new BoardAdapter(getContext(), R.layout.board_layout, board);
        listView = (ListView) v.findViewById(R.id.listView);
        listView.setAdapter(boardAdapter);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View view, int position, long id) {
//                getPosition(position);
//                Intent intent = new Intent(getApplicationContext(), setText.class);
//                startActivityForResult(intent, GET_STRING);
//            }
//        });

        final Button addButton = v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), addBoard.class);
                startActivityForResult(intent, GET_STRING);
            }
        });

        return v;
    }


    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(getContext(), "hi", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_seemore:
                Toast.makeText(getContext(), "hi2", Toast.LENGTH_LONG).show();
                return true;
//            case R.id.action_board1:
//                Intent intent = new Intent(getContext(), FreeBoard.class);
//                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */




}
