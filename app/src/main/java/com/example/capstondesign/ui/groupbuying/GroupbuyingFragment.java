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
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.capstondesign.databinding.FragmentGroupbuyingBinding;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.groupbuying.search.SearchGroupBuying;
import java.util.ArrayList;

public class GroupbuyingFragment extends Fragment {

    public String mynick, nick, title, text, area, price, headCount, nowCount, watchnick;
    ImageView buysearch;
    public static Uri image;
    Profile profile = LoginAcitivity.profile;
    public static int position;

    public static GroupBuyingAdapter groupBuyingAdapter;

    public static ArrayList<Groupbuying> groupbuying = new ArrayList<>();

    FragmentGroupbuyingBinding binding;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGroupbuyingBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //getNick();

        groupbuying.clear();

        binding.rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.rv.setLayoutManager(layoutManager);

        groupBuyingAdapter = new GroupBuyingAdapter(groupbuying);


        binding.rv.setAdapter(groupBuyingAdapter);

        groupBuyingAdapter.setOnItemClickListener(new GroupBuyingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                position = pos;
//                GroupBuyingTimejsonTask.position = pos + 1;
                nick = groupbuying.get(pos).getNick();
                title = groupbuying.get(pos).getTitle();
                text = groupbuying.get(pos).getText();
                area = groupbuying.get(pos).getArea();
                price = groupbuying.get(pos).getPrice();
                headCount = groupbuying.get(pos).getHeadcount();
                nowCount = groupbuying.get(pos).getNowCount();
                watchnick = groupbuying.get(pos).getWatchnick();
                Log.d("onItemClick", Integer.toString(pos));

                getPosition(position);
//                Intent intent = new Intent(getActivity(), InGroupBuying.class);
//                intent.putExtra("price", price);
//                intent.putExtra("title", title);
//                intent.putExtra("text", text);
//                intent.putExtra("nick", nick);
//                intent.putExtra("area", area);
//                intent.putExtra("headcount", headCount);
//                intent.putExtra("nowcount", nowCount);
//                intent.putExtra("watchnick", watchnick);
//                //intent.putExtra("count", pos);
//                startActivity(intent);
            }
        });

        //관심목록 클릭
        groupBuyingAdapter.setOnInterestClickListener(new GroupBuyingAdapter.OnInterestClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                Toast.makeText(getContext(), "관심목록 버튼 클릭", Toast.LENGTH_SHORT).show();
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

    private void getPosition(int position) {
        this.position = position;
    }

//    void getNick() {
//        ProfileService profileService = new ProfileService();
//        try {
//            String result = profileService.execute(profile.getName(), profile.getEmail()).get();
//            mynick = profileService.substringBetween(result, "nickname:", "/");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }

}
