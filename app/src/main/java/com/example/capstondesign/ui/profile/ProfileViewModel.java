package com.example.capstondesign.ui.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.UploadService;
import com.example.capstondesign.network.profile.CountActivityService;
import com.example.capstondesign.repository.BoardRepository;
import com.example.capstondesign.ui.board.Board;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProfileViewModel extends ViewModel {
    MutableLiveData<String> countNum = new MutableLiveData<>();
    BoardRepository repository = new BoardRepository();
    public LiveData<Board> board = repository._board;


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

    public LiveData<String> getcountNum() {
        return countNum;
    }

    public void loadBoard() {
        repository.myActivityBoardRepository();
    }

    public LiveData<Board> getMyBoardActivity() {
        return board;
    }


}
