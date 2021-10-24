package com.example.capstondesign.ui.profile.notice.innotice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstondesign.R;
import com.example.capstondesign.ui.board.BoardFragment;
import com.example.capstondesign.ui.board.inboard.InBoard;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.example.capstondesign.model.CommentProfileCountTask;
import com.example.capstondesign.model.Comment_Item;
import com.example.capstondesign.model.DeleteCommentTask;
import com.example.capstondesign.model.Profile;
import com.example.capstondesign.network.ProfileService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CommentAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private Activity mActivity;
    Profile profile = LoginAcitivity.profile;
    public static String nick, name, email, number;
    String nickname;
    String strurl;
    public static ArrayList<Comment_Item> arr;
    private int pos;
    private BoardFragment ma;
    CommentProfileCountTask commentProfileCountTask;
    //	private Typeface myFont;
    public CommentAdapter(Context mContext, Activity mActivity, BoardFragment mc, ArrayList<Comment_Item> arr_item) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.arr = arr_item;
        this.ma = mc;
    }
    @Override
    public int getCount() {
        return arr.size();
    }
    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            int res = 0;
            res = R.layout.comment_item;
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(res, parent, false);
        }
        pos = position;
        if(arr.size() != 0){
            //getNick();

            nick = arr.get(pos).getNick();

            commentProfileCountTask = new CommentProfileCountTask();
            ImageView profile = convertView.findViewById(R.id.imageView1);


            try {
                //
                //String a = profileTask.substringBetween(result1, "number:", "/");

                Log.d("TEST", number);
                if (number.equals("-1")) {
                    strurl = "http://13.124.75.92:8080/king.png";
                    Log.d("NUM0", strurl);
                } else {
                    strurl = "http://13.124.75.92:8080/upload/" + email + number + ".jpg";
                    Log.d("NUM", strurl);
                }
                Picasso.get().load(Uri.parse(strurl)).into(profile);
            } catch (Exception e) {
                e.printStackTrace();
                Picasso.get().load(Uri.parse("http://13.124.75.92:8080/king.png")).into(profile);
            }
            TextView nickname_text = (TextView)convertView.findViewById(R.id.nickname_text);
            nickname_text.setText(arr.get(pos).getNick());
            TextView content_text = (TextView)convertView.findViewById(R.id.content_text);
            content_text.setText(arr.get(pos).getComment());
            Button delete_btn = (Button)convertView.findViewById(R.id.delete_btn);
            delete_btn.setOnClickListener(this);
            delete_btn.setTag(pos+"");

        }
        return convertView;
    }
    public void onClick(View v){
        DeleteCommentTask deleteCommentTask = new DeleteCommentTask();
        final int tag = Integer.parseInt(v.getTag().toString());
        Log.d("FFFFFFFF", nickname);
        Log.d("AAAAAA", arr.get(tag).getNick());
        Log.d("TAG", String.valueOf(tag));
        switch(v.getId()){
            case R.id.delete_btn:
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(mActivity);
                alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {

                        String result = null;
                        try {
                            if(arr.get(tag).getNick().equals(nickname)) {
                                result = deleteCommentTask.execute(arr.get(tag).getNick(), InBoard.title , arr.get(tag).getComment(), arr.get(tag).getTime()).get();
                                if (result.contains("delete")) {
                                    deleteArr(tag);
                                    Toast.makeText(mContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(mContext, "본인의 댓글만 삭제할 수 있습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                alertDlg.setNegativeButton("아니오", null);
                alertDlg.setTitle("경고문");
                alertDlg.setMessage("삭제하시겠습니까?");
                alertDlg.show();
                break;
        }
    }

    public void deleteArr(int p) {
        arr.remove(p);
        notifyDataSetChanged();
    }

//    void getNick() {
//        ProfileService profileService = new ProfileService();
//        try {
//            String result = profileService.execute(profile.getName(), profile.getEmail()).get();
//            nickname = profileService.substringBetween(result, "nickname:", "/");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }


}