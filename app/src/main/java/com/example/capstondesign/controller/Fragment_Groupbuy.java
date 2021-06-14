package com.example.capstondesign.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.view.GroupBuyingAdapter;
import com.example.capstondesign.model.GroupBuyingTask;
import com.example.capstondesign.model.Groupbuying;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Fragment_Groupbuy extends Fragment {

    public String mynick, nick, title, text, area, price, headCount, nowCount, watchlist;
    ImageView buysearch;
    ImageView buynotify;
    public static Uri image;
    Profile profile = LoginAcitivity.profile;

    public static GroupBuyingAdapter groupBuyingAdapter;
    GroupBuyingTask groupBuyingTask;
    public static ArrayList<Groupbuying> groupbuying = new ArrayList<>();;
    int position;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Groupbuy() {
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
    public static Fragment_Groupbuy newInstance(String param1, String param2) {
        Fragment_Groupbuy fragment = new Fragment_Groupbuy();
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
        View v = inflater.inflate(R.layout.fragment_buy, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.rv);

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getNick();

        //GALLERY(); // 허가
        groupbuying.clear();
        groupBuyingTask = new GroupBuyingTask();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        groupBuyingAdapter = new GroupBuyingAdapter(groupbuying);


        recyclerView.setAdapter(groupBuyingAdapter);

        groupBuyingAdapter.setOnItemClickListener(new GroupBuyingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                nick = groupbuying.get(pos).getNick();
                title = groupbuying.get(pos).getTitle();
                text = groupbuying.get(pos).getText();
                area = groupbuying.get(pos).getArea();
                price = groupbuying.get(pos).getPrice();
                headCount = groupbuying.get(pos).getHeadcount();
                nowCount = groupbuying.get(pos).getNowCount();

                getPosition(position);
                Intent intent = new Intent(getActivity(), BuySubMain.class);
                intent.putExtra("price", price);
                intent.putExtra("title", title);
                intent.putExtra("text", text);
                intent.putExtra("nick", nick);
                intent.putExtra("area", area);
                intent.putExtra("headcount", headCount);
                intent.putExtra("nowcount", nowCount);
                startActivity(intent);
            }
        });



        //관심목록 클릭
        groupBuyingAdapter.setOnInterestClickListener(new GroupBuyingAdapter.OnInterestClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                Toast.makeText(getContext(), "관심목록 버튼 클릭", Toast.LENGTH_SHORT).show();
            }
        });





        buysearch = (ImageView)v.findViewById(R.id.buysearch);
        buynotify = (ImageView)v.findViewById(R.id.buynotify);

        buysearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이동
            }
        });

        buynotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이동
            }
        });




        final Button addButton = v.findViewById(R.id.addbutton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), add_GroupBuying.class);
                startActivity(intent);
            }
        });
        return v;
    }

    private void getPosition(int position) {
        this.position = position;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_search:
//                Toast.makeText(this.getContext(), "hi", Toast.LENGTH_LONG).show();
//                return true;
//            case R.id.action_seemore:
//                Toast.makeText(this.getContext(), "hi2", Toast.LENGTH_LONG).show();
//                return true;
            case R.id.action_board1:
                Intent intent = new Intent(getContext(), FreeBoard.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
/*
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_STRING && resultCode == RESULT_OK && data != null) {
            if(data.getStringExtra("Input_Image") != null) {
                Uri selectImage = Uri.parse(data.getStringExtra("Input_Image"));
                Log.d("SELECTED", String.valueOf(selectImage));
                boardAdapter.add(new Board(data.getStringExtra("Input_Text"), data.getStringExtra("Input_Title"), selectImage));
                image = selectImage;

            } else boardAdapter.add(new Board(data.getStringExtra("Input_Text"), data.getStringExtra("Input_Title")));

            title = data.getStringExtra("Input_Title");
            text = data.getStringExtra("Input_Text");
            boardAdapter.notifyDataSetChanged();
        }
    }
    */
    /*
    void GALLERY() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
        }
        else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(getContext(), "갤러리 접근 권한이 필요합니다", Toast.LENGTH_SHORT).show();
        }
        else {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, GALLERY_PERMISSON);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == GALLERY_PERMISSON) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "갤러리 접근 권한 승인됨", Toast.LENGTH_SHORT).show();
            }
        }
    }

     */

    void getNick() {
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            mynick = profileTask.substringBetween(result, "nickname:", "/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
