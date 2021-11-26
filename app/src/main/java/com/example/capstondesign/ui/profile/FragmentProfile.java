package com.example.capstondesign.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.FragmentProfileBinding;
import com.example.capstondesign.ui.MainFragment;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.board.BoardViewModel;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.profile.inprofile.InProfileActivity;
import com.example.capstondesign.ui.profile.inwatchlist.mywatchlist.WatchlistActivity;
import com.example.capstondesign.ui.profile.myactivity.MyActivity;
import com.example.capstondesign.ui.profile.myactivity.myboard.MyBoardActivity;
import com.example.capstondesign.ui.profile.myactivity.mygroupbuying.MyGroupBuyingActivity;
import com.example.capstondesign.ui.profile.notice.NoticeActivity;
import com.example.capstondesign.ui.profile.setting.SettingActivity;
import com.example.capstondesign.ui.profile.setting.SettingViewModel;
import com.example.capstondesign.ui.profile.setting.withdraw.InWithdrawActivity;
import com.facebook.login.LoginManager;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class FragmentProfile extends Fragment {
    String nickname;

    int login = LoginAcitivity.login;
    public static String number;
    String count;

    FragmentProfileBinding binding;

    ProfileViewModel model;

    int code;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentProfile() {
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
    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
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
        String url = "http://192.168.0.15:8080/test/" + LoginAcitivity.profile.getEmail() + ".jpg";
        profileLoad(url);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("===Profile", "===Profile");

        model = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        model.loadCountActivity(LoginAcitivity.profile.getNickname());
        observeCountNum();

        binding.nickname.setText(LoginAcitivity.profile.getNickname());

        binding.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InProfileActivity.class);
                startActivity(intent);
            }
        });

        binding.inWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WatchlistActivity.class);
                startActivity(intent);
            }
        });

        binding.inMyGroupbuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyGroupBuyingActivity.class);
                startActivity(intent);
            }
        });


        //세팅 이벤트
        binding.setupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });


        binding.inMyBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyBoardActivity.class);
                startActivity(intent);
            }
        });



        //로그아웃 이벤트
        binding.logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return v;
    }

    private void observeCountNum() {
        model.getcountNum().observe(getViewLifecycleOwner(), number -> {
            Log.d("===count", number);
            int a = number.indexOf(":");
            count =  number.substring(a+1);
            Log.d("===count1", count);
            int i = Integer.parseInt(count.trim());
            Log.d("NICKNAME", count);

            // i가 쓴 게시글 갯수(게시글, 공동구매글 포함한 숫자임)

            if(i < 5) {
                binding.classimage.setImageResource(R.drawable.mbs_b);
                binding.myinfoclass.setText("자취준비생");
            } else if(i < 20) {
                binding.classimage.setImageResource(R.drawable.mbs_c);
                binding.myinfoclass.setText("자취초보");
            } else if(i < 40) {
                binding.classimage.setImageResource(R.drawable.mbs_s);
                binding.myinfoclass.setText("자취중수");
            } else if(i < 60) {
                binding.classimage.setImageResource(R.drawable.mbs_g);
                binding.myinfoclass.setText("자취고수");
            }else {
                binding.classimage.setImageResource(R.drawable.mbs_k);
                binding.myinfoclass.setText("자취왕");
            }
        });
    }


    private void profileLoad(String url) {
        try {
            if (getResponseCode(url) == 404) {
                url = "http://183.96.240.182:8080/test/king.png";
            } else {
                url = "http://183.96.240.182:8080/test/" + LoginAcitivity.profile.getEmail() + ".jpg";
            }
            Picasso.get()
                    .load(Uri.parse(url))
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(binding.myinfoimage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void logout () {
        Log.d("LOGIN", String.valueOf(LoginAcitivity.login));
        //네이버 로그인시 login 값은 2
        if (login == 1) {
            //카카오 로그인시 login 값은 1
            Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            UserApiClient.getInstance().logout(error -> null);
            logoutIntent();
        } else if (login == 2) {

            OAuthLogin mOAuthLoginModule;
            mOAuthLoginModule = OAuthLogin.getInstance();
            mOAuthLoginModule.init(
                    getContext()
                    , getString(R.string.naver_client_id)
                    , getString(R.string.naver_client_secret)
                    , getString(R.string.naver_client_name)
            );
            mOAuthLoginModule.logout(getContext());
            logoutIntent();
        } else if (login == 3) {
            //페이스북 로그인시 login 값은 3
            LoginManager.getInstance().logOut();
            Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            logoutIntent();
        } else if (login == 4) {
            //이메일 로그인시 login 값은 4
            logoutIntent();
        }
    }

    private void logoutIntent() {
        Intent intent = new Intent(getActivity(), MainFragment.class);
        Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        LoginAcitivity.Login = false;
        LoginAcitivity.login = 0;
        getActivity().finish();
    }

    public int getResponseCode(String urlString) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    URL u = new URL (urlString);
                    HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection ();
                    huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
                    huc.connect () ;
                    code = huc.getResponseCode() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
        return code;
    }


}


