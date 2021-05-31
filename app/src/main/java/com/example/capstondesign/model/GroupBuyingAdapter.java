package com.example.capstondesign.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.capstondesign.R;
import com.example.capstondesign.model.Board;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;

import java.util.List;

public class GroupBuyingAdapter extends RecyclerView.Adapter<GroupBuyingAdapter.MyViewHolder> {
    static OnItemClickListener mListener = null;
    public static String nick;
    public static Groupbuying groupbuying;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(GroupBuyingAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView price, title, area, headCount, nowCount;
        public ImageView imageView;
        public ImageView interest_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView = (ImageView) itemView.findViewById(R.id.buyimage);
            title = (TextView) itemView.findViewById(R.id.title);
            price = (TextView) itemView.findViewById(R.id.price);
            headCount = (TextView) itemView.findViewById(R.id.headCount);
            nowCount = (TextView) itemView.findViewById(R.id.nowCount);
            area = (TextView) itemView.findViewById(R.id.area);
            interest_btn = (ImageView) itemView.findViewById(R.id.interest_btn);

            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            click_nickname = groupbuyingList.get(pos).nick;
                            click_title = groupbuyingList.get(pos).title;
                            click_text = groupbuyingList.get(pos).text;
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });

             */
        }

        public ImageView getInterest_btn() {
            return interest_btn;
        }
    }

    public static List<Groupbuying> groupbuyingList;
    public GroupBuyingAdapter(List<Groupbuying> items) { groupbuyingList = items; }
    public static String click_nickname, click_title, click_text;

    @NonNull
    @Override
    public GroupBuyingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.groupbuy_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        holder.setIsRecyclable(false);

        groupbuying = groupbuyingList.get(position);

        holder.getInterest_btn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, position);
            }
        });

        /*
        if(groupbuyingList.get(position).image != null) {
            holder.nick.setText(groupbuyingList.get(position).nick);
            holder.imageView.setImageURI(groupbuyingList.get(position).image);
            holder.title.setText(groupbuyingList.get(position).title);
            holder.text.setText(groupbuyingList.get(position).text);
        }
         else {
         */
            holder.title.setText(groupbuyingList.get(position).title);
            holder.price.setText(groupbuyingList.get(position).price);
            holder.headCount.setText(groupbuyingList.get(position).headcount);
            holder.nowCount.setText(groupbuyingList.get(position).nowCount);
            holder.area.setText(groupbuyingList.get(position).area);
            //holder.imageView.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return groupbuyingList.size();
    }


    public Groupbuying getGroupbuying(int position) {
        return groupbuyingList != null ? groupbuyingList.get(position) : null;
    }

    public void addGroupbuying(Groupbuying groupbuying) {
        groupbuyingList.add(groupbuying);
        notifyItemInserted(groupbuyingList.size()-1);
    }



}

