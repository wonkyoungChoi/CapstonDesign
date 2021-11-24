package com.example.capstondesign.ui.groupbuying;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.FragmentGroupbuyingBinding;
import com.example.capstondesign.databinding.GroupbuyingListItemBinding;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.board.BoardAdapter;
import com.example.capstondesign.ui.board.BoardViewModel;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.groupbuying.search.SearchGroupBuying;

import java.io.IOException;
import java.util.ArrayList;

public class GroupbuyingFragment extends Fragment {

    public String mynick, nick, title, text, area, price, headCount, nowCount, watchnick;
    ImageView buysearch;
    public static Uri image;
    Profile profile = LoginAcitivity.profile;
    public static int position;

    public GroupBuyingAdapter adapter;

    public static ArrayList<Groupbuying> groupbuying = new ArrayList<>();

    FragmentGroupbuyingBinding binding;
    GroupbuyingViewModel model;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GroupbuyingFragment() {
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
    public static GroupbuyingFragment newInstance(String param1, String param2) {
        GroupbuyingFragment fragment = new GroupbuyingFragment();
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
    public void onResume() {
        model.loadGroupbuying();
        Log.d("===onResume", "1");
        super.onResume();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGroupbuyingBinding.inflate(inflater, container, false);
        View v = binding.getRoot();


        model = new ViewModelProvider(this).get(GroupbuyingViewModel.class);

        initRecyclerView();
        observeGroupbuyingResult();
        observeWatchlistResult();



        //관심목록 클릭
        adapter.setOnInterestClickListener(new GroupBuyingAdapter.OnInterestClickListener() {
            @Override
            public void onItemClick(View v, int pos) throws IOException {
                Toast.makeText(getContext(), "관심목록 버튼 클릭", Toast.LENGTH_SHORT).show();
                model.addWatchlist(LoginAcitivity.profile.getNickname(), adapter.items.get(pos).getTime());
            }
        });

        binding.buysearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchGroupBuying.class);
                startActivity(intent);
            }
        });

        binding.addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddGroupBuying.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void observeGroupbuyingResult() {
        model.getAll().observe(getViewLifecycleOwner(), groupbuying -> {
            adapter.setBoard(groupbuying.list);
            adapter.notifyDataSetChanged();
        });
    }

    private void observeWatchlistResult() {
        model.getResult().observe(getViewLifecycleOwner(), result -> {
            if(result.contains("추가")) {
                GroupbuyingListItemBinding.bind(getView()).interestBtn.setImageResource(R.drawable.interest_aft);
                Log.d("추가", result);
            } else if(result.contains("삭제")){
                GroupbuyingListItemBinding.bind(getView()).interestBtn.setImageResource(R.drawable.interest_prv);
                //하트 흰색
                Log.d("삭제", result);
            }
        });
    }

    private void initRecyclerView() {
        adapter = new GroupBuyingAdapter();
        binding.rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.rv.setLayoutManager(layoutManager);
        binding.rv.setAdapter(adapter);
    }


}
