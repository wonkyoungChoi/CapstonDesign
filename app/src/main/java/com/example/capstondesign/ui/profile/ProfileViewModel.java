package com.example.capstondesign.ui.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.UploadService;
import com.example.capstondesign.network.bulletin.groupbuying.AddWatchlistService;
import com.example.capstondesign.network.profile.CountActivityService;
import com.example.capstondesign.repository.BoardRepository;
import com.example.capstondesign.repository.GroupbuyingRepository;
import com.example.capstondesign.ui.board.Board;
import com.example.capstondesign.ui.groupbuying.Groupbuying;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProfileViewModel extends ViewModel {
    MutableLiveData<String> countNum = new MutableLiveData<>();
    BoardRepository boardRepository = new BoardRepository();
    GroupbuyingRepository groupbuyingRepository = new GroupbuyingRepository();
    public LiveData<Board> board = boardRepository._board;
    public LiveData<Groupbuying> groupbuying = groupbuyingRepository._groupbuying;
    public LiveData<Groupbuying> watchlist = groupbuyingRepository._watchlist;


    public void addProfile(String filename, String sourceFileUri) {
        new UploadService().enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                Log.d("UploadFile", "됐다");
            }
        }, filename, sourceFileUri);
    }

    public void loadCountActivity(String nick) {
        new CountActivityService().enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            countNum.postValue(response.body().string());
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }, nick);
    }

    public void addWatchnick(String watchnick, String time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new AddWatchlistService().execute(watchnick, time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public LiveData<String> getcountNum() {
        return countNum;
    }


    public void loadBoard() {
        boardRepository.myActivityBoardRepository();
    }

    public LiveData<Board> getMyBoardActivity() {
        return board;
    }

    public void loadGroupbuying() {
        groupbuyingRepository.myActivityGroupbuyingRepository();
    }

    public LiveData<Groupbuying> getMyGroupbuyingActivity() {
        return groupbuying;
    }

    public void loadWatchlist() {
        groupbuyingRepository.myActivityWatchlistRepository();
    }

    public LiveData<Groupbuying> getMyWatchlistActivity() {
        return watchlist;
    }


}
