package com.example.capstondesign.ui.groupbuying;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.UploadMultipleService;
import com.example.capstondesign.network.bulletin.groupbuying.AddNowCountService;
import com.example.capstondesign.network.bulletin.groupbuying.AddWatchlistService;
import com.example.capstondesign.network.bulletin.groupbuying.AddGroupbuyingService;
import com.example.capstondesign.network.bulletin.groupbuying.DelNowCountService;
import com.example.capstondesign.network.bulletin.groupbuying.DeleteGroupbuyingService;
import com.example.capstondesign.network.chatting.AddChattingRoomService;
import com.example.capstondesign.repository.GroupbuyingRepository;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GroupbuyingViewModel extends ViewModel {
    GroupbuyingRepository repository = new GroupbuyingRepository();

    public LiveData<Groupbuying> groupbuying = repository._groupbuying;
    AddGroupbuyingService addGroupbuyingService = new AddGroupbuyingService();

    public MutableLiveData<String> watchlistResult = new MutableLiveData<>();

    public MutableLiveData<Integer> count = new MutableLiveData<>();


    public void loadGroupbuying() {
        repository.groupbuyingRepository();
    }

    public LiveData<Groupbuying> getAll() {
        return groupbuying;
    }

    public void addGroupbuying (Groupbuying groupbuying) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    addGroupbuyingService.execute(groupbuying.getNick(), groupbuying.getTitle(), groupbuying.getText(), groupbuying.getPrice(),
                            groupbuying.getHeadcount(), groupbuying.getArea(), groupbuying.getPicture_count(), groupbuying.getTime(), groupbuying.getEmail());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void addPicture(String filename, String sourceFileUri) {
        new UploadMultipleService().enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
            }
        }, filename, sourceFileUri);
    }

    public void addCount(String time, String count) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new AddNowCountService().execute(time, count);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void delCount(String time, String count) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new DelNowCountService().execute(time, count);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void addWatchlist(String watchnick, String time) throws IOException {
        new AddWatchlistService().enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                Log.d("UploadFile", "됐다");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            watchlistResult.postValue(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, watchnick, time);
    }

    public LiveData<String> getResult() {
        return watchlistResult;
    }

    public void deleteGroupbuying(String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new DeleteGroupbuyingService().execute(id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void addChattingRoom(String mynick, String othernick, String last_msg, String myemail, String otheremail) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new AddChattingRoomService().execute(mynick, othernick, last_msg, myemail, otheremail);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    int code;

    public String strUrl(String url) {
        try {
            if (getResponseCode(url) == 404) {
                url = "http://192.168.0.15:8080/king.png";
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return url;
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

