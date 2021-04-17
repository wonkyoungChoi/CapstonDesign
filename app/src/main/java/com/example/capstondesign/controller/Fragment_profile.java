package com.example.capstondesign.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.capstondesign.R;
import com.example.capstondesign.model.ChatAdapter;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.model.ProfileTask;

import java.util.concurrent.ExecutionException;

public class Fragment_profile extends Fragment {
    String name, phone_num, email , nickname, password, gender;

    Profile profile = LoginAcitivity.profile;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_profile() {
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
    public static Fragment_profile newInstance(String param1, String param2) {
        Fragment_profile fragment = new Fragment_profile();
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
        TextView nameTv, phone_numTv, emailTv, nicknameTv, passwordTv, genderTv;
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        nameTv = v.findViewById(R.id.name);
        phone_numTv = v.findViewById(R.id.phone_num);
        emailTv = v.findViewById(R.id.email);
        nicknameTv = v.findViewById(R.id.nickname);
        passwordTv = v.findViewById(R.id.password);
        genderTv = v.findViewById(R.id.gender);

        Log.d("EMAIL", profile.getEmail());
        Log.d("NAME", profile.getName());
        //프로필을 불러오는 Task를 통해 프로필 값들을 입력함
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();

            name = profileTask.substringBetween(result, "name:", "/");
            profile.setName(name);
            nameTv.setText(name);

            phone_num = profileTask.substringBetween(result, "phone_num:", "/");
            profile.setPhone_num(phone_num);
            phone_numTv.setText(phone_num);

            email = profileTask.substringBetween(result, "email:", "/");
            profile.setEmail(email);
            emailTv.setText(email);

            nickname = profileTask.substringBetween(result, "nickname:", "/");
            profile.setNickname(nickname);
            nicknameTv.setText(nickname);
            ChatAdapter.nick = nickname;

            password = profileTask.substringBetween(result, "password:", "/");
            profile.setPassword(password);
            passwordTv.setText(password);

            gender = profileTask.substringBetween(result, "gender:", "/");
            profile.setGender(gender);
            genderTv.setText(gender);


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return v;
    }

}
