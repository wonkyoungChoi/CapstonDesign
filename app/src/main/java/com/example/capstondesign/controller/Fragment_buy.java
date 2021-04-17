package com.example.capstondesign.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.model.ChatRoomAdapter;
import com.example.capstondesign.model.ChatRoomData;
import com.example.capstondesign.model.CheckTask;
import com.facebook.login.LoginManager;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

import java.util.ArrayList;

public class Fragment_buy extends Fragment {
    Button logout;
    int login = LoginAcitivity.login;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_buy() {
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
    public static Fragment_buy newInstance(String param1, String param2) {
        Fragment_buy fragment = new Fragment_buy();
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

        logout = v.findViewById(R.id.btn_logout);

        //로그아웃 이벤트
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //네이버 로그인시 login 값은 2
                if(login == 1) {
                    //카카오 로그인시 login 값은 1
                    Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            Intent intent = new Intent(getActivity(), LoginAcitivity.class);
                            startActivity(intent);
                            LoginAcitivity.login = 0;
                            Log.d("LOGOUT", String.valueOf(login));
                        }
                    });
                    getActivity().finish();
                } else if(login == 2) {

                    OAuthLogin mOAuthLoginModule;
                    mOAuthLoginModule = OAuthLogin.getInstance();
                    mOAuthLoginModule.init(
                            getContext()
                            ,getString(R.string.naver_client_id)
                            ,getString(R.string.naver_client_secret)
                            ,getString(R.string.naver_client_name)
                    );
                    mOAuthLoginModule.logout(getContext());
                    Intent intent = new Intent(getActivity(), LoginAcitivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    LoginAcitivity.login = 0;
                    getActivity().finish();
                } else if(login==3) {
                    //페이스북 로그인시 login 값은 3
                    LoginManager.getInstance().logOut();
                    Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginAcitivity.class);
                    startActivity(intent);
                    LoginAcitivity.login = 0;
                    getActivity().finish();
                } else if(login==4) {

                }
            }
        });
        return v;
    }
}
