package com.example.capstondesign.ui.groupbuying;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.capstondesign.R;

import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.databinding.GroupbuyingListItemBinding;
import com.example.capstondesign.ui.groupbuying.ingroupbuying.InGroupBuyingActivity;
import com.example.capstondesign.ui.home.login.LoginAcitivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GroupBuyingAdapter extends RecyclerView.Adapter<GroupBuyingAdapter.MyViewHolder> {
    OnInterestClickListener mListener = null;
    public Groupbuying groupbuying;
    private GroupbuyingListItemBinding mBinding;

    public List<Groupbuying> items = new ArrayList<>();



    public interface OnInterestClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnInterestClickListener(GroupBuyingAdapter.OnInterestClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        GroupbuyingListItemBinding bind;

        public MyViewHolder(@NonNull GroupbuyingListItemBinding binding) {
            super(binding.getRoot());
            bind = binding;
        }
    }

    @NonNull
    @Override
    public GroupBuyingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding = GroupbuyingListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(mBinding);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));

        holder.setIsRecyclable(false);
        Groupbuying groupbuying = items.get(position);

        Picasso.get().load(Uri.parse(groupbuying.getImage_url())).into(mBinding.buyimage);

        mBinding.title.setText(groupbuying.getTitle());
        mBinding.price.setText(groupbuying.getPrice() + "원");
        mBinding.headCount.setText(groupbuying.getHeadcount());
        mBinding.nowCount.setText(groupbuying.getNowCount());
        mBinding.area.setText(groupbuying.getArea());
        Log.d("===url", groupbuying.getImage_url());


        if(groupbuying.getCheck()) {
            mBinding.interestBtn.setImageResource(R.drawable.watchlist_add);
        } else {
            mBinding.interestBtn.setImageResource(R.drawable.watchlist_delete);
        }

        groupbuying.setWatchlist_btn(mBinding.interestBtn);

        mBinding.interestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(mBinding.interestBtn, position);
                if(groupbuying.getCheck()) {
                    Toast.makeText(mBinding.getRoot().getContext(), "관심목록 삭제", Toast.LENGTH_SHORT).show();
                    groupbuying.getWatchlist_btn().setImageResource(R.drawable.watchlist_delete);
                    groupbuying.setCheck(false);
                } else {
                    Toast.makeText(mBinding.getRoot().getContext(), "관심목록 추가", Toast.LENGTH_SHORT).show();
                    groupbuying.getWatchlist_btn().setImageResource(R.drawable.watchlist_add);
                    groupbuying.setCheck(true);
                }
                Log.d("===ClickInter", "click");
            }
        });

        mBinding.listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("===Click", "CLICK");

                Intent intent = new Intent(mBinding.listItem.getContext(), InGroupBuyingActivity.class);

                Integer id = groupbuying.getId();
                String nick = groupbuying.getNick();
                String title = groupbuying.getTitle();
                String text = groupbuying.getText();
                String area = groupbuying.getArea();
                String price = groupbuying.getPrice();
                String headCount = groupbuying.getHeadcount();
                String nowCount = groupbuying.getNowCount();
                String watchnick = groupbuying.getWatchnick();
                String pictureCount = groupbuying.getPicture_count();
                String time = groupbuying.getTime();
                String email = groupbuying.getEmail();
                Boolean check = groupbuying.getCheck();

                intent.putExtra("id", id);
                intent.putExtra("nick", nick);
                intent.putExtra("title", title);
                intent.putExtra("text", text);
                intent.putExtra("area", area);
                intent.putExtra("price", price);
                intent.putExtra("headCount", headCount);
                intent.putExtra("nowCount", nowCount);
                intent.putExtra("watchnick", watchnick);
                intent.putExtra("pictureCount", pictureCount);
                intent.putExtra("time", time);
                intent.putExtra("email", email);
                intent.putExtra("check", check);


                mBinding.listItem.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void setBoard(ArrayList<Groupbuying> groupbuyingList) {
        items = groupbuyingList;
    }



}

