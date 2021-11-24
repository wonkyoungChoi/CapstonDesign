package com.example.capstondesign.ui.profile.inwatchlist.mywatchlist;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.capstondesign.R;

import android.os.Build;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstondesign.ui.groupbuying.Groupbuying;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.MyViewHolder> {
    static OnInterestClickListener mListener = null;
    static OnItemClickListener mListener1 = null;
    public static String nick;
    static String mynick1;
    public static Groupbuying groupbuying;


    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(WatchlistAdapter.OnItemClickListener listener) {
        mListener1 = listener;
    }

    public interface OnInterestClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnInterestClickListener(WatchlistAdapter.OnInterestClickListener listener) {
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
            imageView = (ImageView) itemView.findViewById(R.id.buyimage);

            interest_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if(mListener1 != null) {
                            click_area = groupbuyingList.get(pos).getArea();
                            click_nickname = groupbuyingList.get(pos).getNick();
                            click_title = groupbuyingList.get(pos).getTitle();
                            click_text = groupbuyingList.get(pos).getText();
                            click_time = groupbuyingList.get(pos).getTime();
                            mListener1.onItemClick(v, pos);
                        }
                    }
                }
            });





        }

        public ImageView getInterest_btn() {
            return interest_btn;
        }
    }

    public static List<Groupbuying> groupbuyingList;
    public WatchlistAdapter(List<Groupbuying> items) { groupbuyingList = items; }
    public static String click_nickname, click_title, click_text, click_area, click_time;

    @NonNull
    @Override
    public WatchlistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.groupbuying_list_item, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("position", String.valueOf(position));
        //getNick();

        holder.setIsRecyclable(false);

        groupbuying = groupbuyingList.get(position);

//        holder.getInterest_btn().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onItemClick(v, position);
//                //getNick();
//                String title = WatchlistActivity.watchlist.get(position).getTitle();
//                String nick = WatchlistActivity.watchlist.get(position).getNick();
//                String text = WatchlistActivity.watchlist.get(position).getText();
//                String price = WatchlistActivity.watchlist.get(position).getPrice();
//                String area = WatchlistActivity.watchlist.get(position).getArea();
//
//                Log.d("관심목록 클릭", area);
//                AddWatchlistTask addWatchlistTask = new AddWatchlistTask();
//                try {
//                    String result = addWatchlistTask.execute(mynick1, title, text , price , area, nick, WatchlistActivity.watchlist.get(position).getTime()).get();
//                    Log.d("결과", result);
//                    if(result.contains("추가")) {
//                        holder.getInterest_btn().setImageResource(R.drawable.interest_aft);
//                        Log.d("추가", result);
//                    } else if(result.contains("삭제")){
//                        holder.getInterest_btn().setImageResource(R.drawable.interest_prv);
//                        //하트 흰색
//                        Log.d("삭제", result);
//                    }
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });


        Log.d("WATCHNICK", groupbuyingList.get(position).getWatchnick());

        if(groupbuyingList.get(position).getWatchnick().contains(mynick1 + ",")) {
            Log.d("등록", "등록");
            holder.interest_btn.setImageResource(R.drawable.interest_aft);
            holder.title.setText(groupbuyingList.get(position).getTitle());
            holder.price.setText(groupbuyingList.get(position).getPrice() + "원");
            holder.headCount.setText(groupbuyingList.get(position).getHeadcount());
            holder.nowCount.setText(groupbuyingList.get(position).getNowCount());
            holder.area.setText(groupbuyingList.get(position).getArea());

            String get = groupbuyingList.get(position).getImage_url();
            Log.d("getPhoto", get);
            Picasso.get().load(Uri.parse(get)).into(holder.imageView);

        }

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

//    public static void getNick() {
//        Profile profile = LoginAcitivity.profile;
//        ProfileService profileService = new ProfileService();
//        try {
//            String result = profileService.execute(profile.getName(), profile.getEmail()).get();
//            mynick1 = profileService.substringBetween(result, "nickname:", "/");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }



}

