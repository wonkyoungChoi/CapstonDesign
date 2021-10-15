package com.example.capstondesign.ui.board;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.FragmentBoardBinding;
import com.example.capstondesign.ui.board.inboard.InBoard;
import com.example.capstondesign.ui.board.search.SearchBoard;
import com.example.capstondesign.model.BoardTask;

import java.util.ArrayList;

public class BoardFragment extends Fragment {

    public String nick, title, text;

    public static Uri image;

    public static BoardAdapter boardAdapter;
    BoardTask boardTask;
    public ArrayList<Board> board = new ArrayList<>();;
    int position;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BoardFragment() {
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
    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
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

        FragmentBoardBinding binding = FragmentBoardBinding.inflate(inflater, container, false);



        View v = binding.getRoot();

        BoardViewModel model = new ViewModelProvider(this).get(BoardViewModel.class);

        model.loadBoard();

        model.getAll().observe(getViewLifecycleOwner(), board -> {
            boardAdapter = new BoardAdapter(board.list);
        });
        //진행중


        //GALLERY(); // 허가
        board.clear();
        boardTask = new BoardTask();
        binding.recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);



        binding.recyclerView.setAdapter(boardAdapter);

        binding.boardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchBoard.class);
                startActivity(intent);
            }
        });


        boardAdapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                nick = BoardAdapter.click_nickname;
                title = BoardAdapter.click_title;
                text = BoardAdapter.click_text;
                getPosition(position);
                Intent intent = new Intent(getContext(), InBoard.class);
                intent.putExtra("title", title);
                intent.putExtra("text", text);
                intent.putExtra("nick", nick);
                startActivity(intent);
            }
        });



        final Button addButton = v.findViewById(R.id.addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "CLICK", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), AddBoard.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void getPosition(int position) {
        this.position = position;
    }

}