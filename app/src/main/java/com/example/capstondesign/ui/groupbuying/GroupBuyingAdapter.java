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
import android.widget.ImageView;

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
    static OnInterestClickListener mListener = null;
    static OnItemClickListener mListener1 = null;
    public static String nick;
    static String mynick1;
    public static Groupbuying groupbuying;
    private GroupbuyingListItemBinding mBinding;
    int code;

    public List<Groupbuying> items = new ArrayList<>();


    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(GroupBuyingAdapter.OnItemClickListener listener) {
        mListener1 = listener;
    }

    public interface OnInterestClickListener{
        void onItemClick(View v, int pos) throws IOException;
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

    public static String click_nickname, click_title, click_text, click_area, click_time;

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
        if(groupbuying.getWatchnick().contains(LoginAcitivity.profile.getNickname() + ",")) {
            mBinding.interestBtn.setImageResource(R.drawable.interest_aft);
        } else {
            mBinding.interestBtn.setImageResource(R.drawable.interest_prv);
        }


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


                mBinding.listItem.getContext().startActivity(intent);
            }
        });


    }

    public ImageView getInterest_btn() {
        return mBinding.interestBtn;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void setBoard(ArrayList<Groupbuying> groupbuyingList) {
        items = groupbuyingList;
    }



}

