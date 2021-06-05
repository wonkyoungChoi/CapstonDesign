package com.example.capstondesign.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstondesign.R;
import com.example.capstondesign.controller.Fragment_board;
import com.example.capstondesign.controller.FreeBoard;
import com.example.capstondesign.controller.LoginAcitivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Comment_Adapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private Activity mActivity;
    Profile profile = LoginAcitivity.profile;
    String nickname;
    private ArrayList<Comment_Item> arr;
    private int pos;
    private Fragment_board ma;
    //	private Typeface myFont;
    public Comment_Adapter(Context mContext, Activity mActivity, Fragment_board mc, ArrayList<Comment_Item> arr_item) {
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
            LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(res, parent, false);
        }
        pos = position;
        if(arr.size() != 0){
            getNick();
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
        switch(v.getId()){
            case R.id.delete_btn:
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(mActivity);
                alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {

                        String result = null;
                        try {
                            result = deleteCommentTask.execute(arr.get(pos).getNick(), FreeBoard.title , arr.get(pos).getComment()).get();
                            if(arr.get(pos).getNick().equals(nickname)) {
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

    void getNick() {
        ProfileTask profileTask = new ProfileTask();
        try {
            String result = profileTask.execute(profile.getName(), profile.getEmail()).get();
            nickname = profileTask.substringBetween(result, "nickname:", "/");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}