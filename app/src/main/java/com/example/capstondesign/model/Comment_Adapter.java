package com.example.capstondesign.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstondesign.R;
import com.example.capstondesign.controller.Fragment_board;

import java.util.ArrayList;

public class Comment_Adapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private Activity mActivity;
    private ArrayList<Comment_Item> arr;
    private int pos;
    private Fragment_board ma;
    //	private Typeface myFont;
    public Comment_Adapter(Context mContext, Activity mActivity, Fragment_board mc, ArrayList<Comment_Item> arr_item) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.arr = arr_item;
        this.ma = mc;
//		myFont = Typeface.createFromAsset(mContext.getAssets(), "BareunDotum.ttf");
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
        final int tag = Integer.parseInt(v.getTag().toString());
        switch(v.getId()){
            case R.id.delete_btn:
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(mActivity);
                alertDlg.setPositiveButton("예", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        deleteArr(tag);
                        Toast.makeText(mContext, "삭제되었습니다.", Toast.LENGTH_LONG).show();
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
}