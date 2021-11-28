package com.example.capstondesign.ui.profile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.ChangeNickDialogBinding;
import com.example.capstondesign.databinding.FragmentProfileBinding;
import com.example.capstondesign.ui.MainFragment;
import com.example.capstondesign.ui.Profile;
import com.example.capstondesign.ui.groupbuying.GroupBuyingAdapter;
import com.example.capstondesign.ui.groupbuying.Groupbuying;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.ui.profile.myactivity.myboard.MyBoardActivity;
import com.example.capstondesign.ui.profile.myactivity.mygroupbuying.MyGroupBuyingActivity;
import com.example.capstondesign.ui.profile.setting.SettingActivity;
import com.example.capstondesign.ui.profile.setting.changepassword.InChangePasswordActivity;
import com.facebook.login.LoginManager;
import com.kakao.sdk.user.UserApiClient;
import com.nhn.android.naverlogin.OAuthLogin;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class FragmentProfile extends Fragment {
    String nickname;

    int login = LoginAcitivity.login;
    String count;

    FragmentProfileBinding binding;

    int PICK_IMAGE_REQUEST = 1;
    public WatchlistAdapter adapter;
    public ArrayList<Groupbuying> groupbuying = new ArrayList<>();
    Uri file = null;

    Boolean check = false;

    ProfileViewModel model;
    ChangeNickDialog dialog;

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
        String url = "http://121.162.202.209:8080/test/" + LoginAcitivity.profile.getEmail() + ".jpg";
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

        initText(LoginAcitivity.profile);

        model.loadCountActivity(LoginAcitivity.profile.getNickname());
        observeCountNum();

        observeNickResult();

        model.loadWatchlist();
        observeMyWatchlistResult();
        initWatchlistRecyclerView();

        //프로필 더보기
        binding.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.email.setVisibility(View.VISIBLE);
                binding.phoneNum.setVisibility(View.VISIBLE);
                binding.gender.setVisibility(View.VISIBLE);
                binding.viewProfile.setVisibility(View.GONE);
                binding.reduceProfile.setVisibility(View.VISIBLE);
            }
        });

        //프로필 더보기 줄이기
        binding.reduceProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.email.setVisibility(View.GONE);
                binding.phoneNum.setVisibility(View.GONE);
                binding.gender.setVisibility(View.GONE);
                binding.reduceProfile.setVisibility(View.GONE);
                binding.viewProfile.setVisibility(View.VISIBLE);
            }
        });

        //내 프로필 사진
        binding.myinfoimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "프로필 이미지 변경"), PICK_IMAGE_REQUEST);
            }
        });

        binding.editNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeNickDialog();
            }
        });

        //관심목록 클릭
        adapter.setOnInterestClickListener(new WatchlistAdapter.OnInterestClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                model.addWatchnick(LoginAcitivity.profile.getEmail(), adapter.items.get(pos).getTime());
            }
        });

        //내가 작성한 공동구매
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

        //내가 작성한 자유 게시글
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            ProgressDialog dialog = ProgressDialog.show(getContext(), "", "Loading...",
                    true);
            dialog.show();

            dialog.dismiss();

            file = data.getData();
            String filename = LoginAcitivity.profile.getEmail() + ".jpg";
            String sourceFileUri = "/data/data/com.example.capstondesign/files/" + LoginAcitivity.profile.getEmail() + ".jpg";

            try {
                InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                // Log.d(TAG, String.valueOf(bitmap));
                Toast.makeText(getContext(), "프로필 이미지 선택" , Toast.LENGTH_SHORT).show();

                try {
                    InputStream ins = getActivity().getContentResolver().openInputStream(file);
                    // "/data/data/패키지 이름/files/copy.jpg" 저장
                    FileOutputStream fos = getActivity().openFileOutput(filename, 0);


                    Log.d("에러 찾기", "여기서?4");

                    byte[] buffer = new byte[1024 * 100];

                    while (true) {
                        int data1 = ins.read(buffer);
                        if (data1 == -1) {
                            break;
                        }

                        fos.write(buffer, 0, data1);
                    }

                    ins.close();
                    fos.close();

                    model.addProfile(filename, sourceFileUri);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                            binding.myinfoimage.setImageBitmap(img);
                        }
                    }, 2500); // 3000 milliseconds delay



                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("IOException", e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private void initText(Profile profile) {
        binding.nickname.setText(profile.getNickname());
        binding.email.setText(profile.getEmail());
        binding.phoneNum.setText(profile.getPhone_num());
        binding.gender.setText(profile.getGender());
    }


    private void observeCountNum() {
        model.getcountNum().observe(getViewLifecycleOwner(), number -> {
            setClass(number);
        });
    }

    private void setClass(String number) {
        int a = number.indexOf(":");
        count =  number.substring(a+1);
        int i = Integer.parseInt(count.trim());

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
    }


    private void profileLoad(String url) {
        try {
            if (getResponseCode(url) == 404) {
                url = "http://121.162.202.209:8080/test/king.png";
            } else {
                url = "http://121.162.202.209:8080/test/" + LoginAcitivity.profile.getEmail() + ".jpg";
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


    private void ChangeNickDialog() {
        dialog = new ChangeNickDialog(getContext(), new ChangeNickDialogClickListener() {
            @Override
            public void onPositiveClick() {
                if(check) {
                    model.ChangeNick(dialog.binding.cNick.getText().toString(), LoginAcitivity.profile.getNickname());
                    LoginAcitivity.profile.setNickname(dialog.binding.cNick.getText().toString());
                    binding.nickname.setText(dialog.binding.cNick.getText().toString());
                    Toast.makeText(getContext(), "닉네임 변경완료", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "닉네임 중복 체크를 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNegativeClick() {

            }

            @Override
            public void onCheckClick() throws IOException {
                model.loadNickCheck(dialog.binding.cNick.getText().toString());
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    private void observeNickResult() {
        model.getNickResult().observe(getViewLifecycleOwner(), result -> {
            if (result.contains("sameNick")) {
                Toast.makeText(getContext(), "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show();
            } else {
                check = true;
                Toast.makeText(getContext(), "사용가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void logout () {
        //네이버 로그인시 login 값은 2
        if (login == 1) {
            //카카오 로그인시 login 값은 1
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

    private void observeMyWatchlistResult() {
        model.getMyWatchlistActivity().observe(getViewLifecycleOwner(), watchlist -> {
            adapter.setWatchlist(watchlist.getList());
            adapter.notifyDataSetChanged();
        });
    }

    private void initWatchlistRecyclerView() {
        groupbuying.clear();
        adapter = new WatchlistAdapter();
        binding.watchlistRv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        binding.watchlistRv.setLayoutManager(layoutManager);
        binding.watchlistRv.setAdapter(adapter);
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


