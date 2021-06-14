package com.example.capstondesign.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.capstondesign.R;
import com.example.capstondesign.controller.LoginAcitivity;

import android.os.Build;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.model.Groupbuying;

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.MyViewHolder> {
    static OnItemClickListener mListener = null;
    public static String nick;
    public static Groupbuying groupbuying;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(WatchlistAdapter.OnItemClickListener listener) {
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

    public static List<Groupbuying> watchlistList;
    public WatchlistAdapter(List<Groupbuying> items) { watchlistList = items; }
    public static String click_nickname, click_title, click_text;

    @NonNull
    @Override
    public WatchlistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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



        groupbuying = watchlistList.get(position);

        if(groupbuying.getWatchnick().contains(LoginAcitivity.profile.getNickname() + ",")) {

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

            holder.title.setText(watchlistList.get(position).getTitle());
            holder.price.setText(watchlistList.get(position).getPrice());
            holder.headCount.setText(watchlistList.get(position).getHeadcount());
            holder.nowCount.setText(watchlistList.get(position).getNowCount());
            holder.area.setText(watchlistList.get(position).getArea());
            //holder.imageView.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return watchlistList.size();
    }


    public Groupbuying getWatchlist(int position) {
        return watchlistList != null ? watchlistList.get(position) : null;
    }

    public void addWatchlist(Groupbuying groupbuying) {
        watchlistList.add(groupbuying);
        notifyItemInserted(watchlistList.size()-1);
    }



}

