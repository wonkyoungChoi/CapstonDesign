package com.example.capstondesign.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Profile;

public class Fragment_first extends Fragment {
    String name;
    String gender;
    String email;
    String birthday;
    int login = LoginAcitivity.login;

    Profile profile = LoginAcitivity.profile;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_first() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment_first.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_first newInstance(String param1, String param2) {
        Fragment_first fragment = new Fragment_first();
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
        TextView nameTv, genderTv, birthTv, emailTv;
        View v = inflater.inflate(R.layout.fragment_blank_first, container, false);

        nameTv = v.findViewById(R.id.nickname);
        genderTv = v.findViewById(R.id.gender);
        emailTv = v.findViewById(R.id.email);
        birthTv = v.findViewById(R.id.birth);

        Log.d("LOGIN!!!!", String.valueOf(login));

        name = profile.getName();
        gender = profile.getGender();
        birthday = profile.getBirthday();
        email = profile.getEmail();

        nameTv.setText(name);
        if(gender.equals("M")||gender.equals("male")) {
            gender = "남성";
        } else {
            gender = "여성";
        }
        genderTv.setText(gender);
        emailTv.setText(email);
        birthTv.setText(birthday);


        return v;
    }
}
