package com.example.capstondesign.ui.profile;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.R;
import com.example.capstondesign.databinding.SeemoreItemBinding;
import com.example.capstondesign.databinding.WatchlistItemBinding;
import com.example.capstondesign.ui.groupbuying.Groupbuying;
import com.example.capstondesign.ui.groupbuying.ingroupbuying.InGroupBuyingActivity;
import com.example.capstondesign.ui.profile.myactivity.WatchlistActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WatchlistAdapter extends RecyclerView.Adapter {
    OnInterestClickListener mListener = null;
    ArrayList<Groupbuying> items = new ArrayList<>();

    private static int TYPE_WATCHLIST = 1;
    private static int TYPE_SEE_MORE = 2;
    private static int TYPE_NULL = 3;

    private WatchlistItemBinding mBinding;
    private SeemoreItemBinding mBinding2;

    public interface OnInterestClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnInterestClickListener(WatchlistAdapter.OnInterestClickListener listener) {
        mListener = listener;
    }


    private class MyViewHolder extends RecyclerView.ViewHolder{
        WatchlistItemBinding bind;
        public MyViewHolder(WatchlistItemBinding binding) {
            super(binding.getRoot());
            bind = binding;
        }

    }

    private class MySeeMoreHolder extends RecyclerView.ViewHolder{
        SeemoreItemBinding bind;
        public MySeeMoreHolder(SeemoreItemBinding binding) {
            super(binding.getRoot());
            bind = binding;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_SEE_MORE) {
            mBinding2 = SeemoreItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new WatchlistAdapter.MySeeMoreHolder(mBinding2);
        } else {
            mBinding = WatchlistItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new WatchlistAdapter.MyViewHolder(mBinding);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position < 5) {
            return TYPE_WATCHLIST;
        } else if(position == 5){
            return TYPE_SEE_MORE;
        } else {
            return  TYPE_NULL;
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        if(getItemViewType(position) == TYPE_WATCHLIST) {
            Log.d("position", String.valueOf(position));
            Groupbuying groupbuying = items.get(position);

            mBinding.title.setText(groupbuying.getTitle());
            mBinding.text.setText(groupbuying.getText());

            Picasso.get().load(groupbuying.getImage_url()).into(mBinding.buyimage);

            groupbuying.setCheck(true);

            groupbuying.setWatchlist_btn(mBinding.interestBtn);
            mBinding.interestBtn.setImageResource(R.drawable.watchlist_add);

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

            mBinding.watchlistItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("===Click", "CLICK");

                    Intent intent = new Intent(mBinding.watchlistItem.getContext(), InGroupBuyingActivity.class);

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


                    mBinding.watchlistItem.getContext().startActivity(intent);
                }
            });
        } else if(getItemViewType(position) == TYPE_SEE_MORE){
            mBinding2.moreBtn.setImageResource(R.drawable.see_more_btn);
            mBinding2.moreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mBinding.watchlistItem.getContext(), WatchlistActivity.class);
                    mBinding2.moreBtn.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setWatchlist(ArrayList<Groupbuying> groupbuyingArrayList) {
        items = groupbuyingArrayList;
    }

}

