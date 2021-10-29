package com.example.capstondesign.ui.groupbuying;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.capstondesign.network.bulletin.groupbuying.UploadFileAsyncGroupBuying;
import com.example.capstondesign.network.bulletin.groupbuying.AddGroupbuyingService;
import com.example.capstondesign.repository.GroupbuyingRepository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class GroupbuyingViewModel extends ViewModel {
    GroupbuyingRepository repository = new GroupbuyingRepository();
    public LiveData<Groupbuying> groupbuying = repository._groupbuying;
    AddGroupbuyingService addGroupbuyingService = new AddGroupbuyingService();;

    public void loadGroupbuying() {
        repository.groupbuyingRepository();
    }

    public LiveData<Groupbuying> getAll() {
        return groupbuying;
    }

    //들어갈 내용 다시 정의해야함
    public void addGroupbuying (Groupbuying groupbuying) {
        addGroupbuyingService.execute(
                groupbuying.getNick(), groupbuying.getTitle(), groupbuying.getText(), groupbuying.getPrice(), groupbuying.getHeadcount(),
                groupbuying.getNowCount(), groupbuying.getArea(), groupbuying.getWatchnick(), groupbuying.getTitle().hashCode() + groupbuying.getTime() + ".jpg", groupbuying.getTime(), groupbuying.getNumber());
    }

    public void addPicture(Context context, Uri[] fileGroupBuying, String title, String time) {
        for (Uri uri : fileGroupBuying) {
            try {
                InputStream ins = context.getContentResolver().openInputStream(uri);
                // "/data/data/패키지 이름/files/copy.jpg" 저장
                Log.d("에러 찾기", "여기서?3");

                FileOutputStream fos = context.openFileOutput(title.hashCode() + time + ".jpg", 0);
                Log.d("countadd", title.hashCode() + "");

                Log.d("에러 찾기", "여기서?4");

                byte[] buffer = new byte[1024 * 100];

                while (true) {
                    int data = ins.read(buffer);
                    if (data == -1) {
                        break;
                    }
                    fos.write(buffer, 0, data);
                }

                ins.close();
                fos.close();

                new UploadFileAsyncGroupBuying().execute();
                Log.d("UploadFile", "됬다");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("IOException", e.getMessage());
            }
        }
    }

    Uri[] fileGroupBuying;
    String number;
    public void addPictureResult(Intent data, Context context , ImageView addPhoto) {

        try {
            Log.e("Data" , data.toString());
            //Log.e("Data" , data.getData().toString());
            Log.d("possible", "여기서?");
            //file = data.getData();
            ClipData clipData = data.getClipData();


            if(clipData.getItemCount() > 1 && clipData.getItemCount() < 9) {

                Log.d("count", Integer.toString(clipData.getItemCount()));
                fileGroupBuying = new Uri[clipData.getItemCount()];
                number = Integer.toString(clipData.getItemCount());
//                        result = groupBuyingCountTask.execute(profile.getName(), titlestr, Integer.toString(clipData.getItemCount())).get(); // 숫자 넣기 파일 길이도 넣어야 돼
                for(int i = 0; i < clipData.getItemCount(); i++) {
                    fileGroupBuying[i] = clipData.getItemAt(i).getUri();
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = context.getContentResolver().openInputStream(clipData.getItemAt(i).getUri());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    addPhoto.setImageBitmap(img);

                }
            } else {
                fileGroupBuying = new Uri[1];
//                        result = groupBuyingCountTask.execute(profile.getName(), titlestr, Integer.toString(1)).get();
                fileGroupBuying[0] = data.getData();
                number = "1";

                InputStream in = context.getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                // 이미지 표시
                addPhoto.setImageBitmap(img);
            }
        } catch (Exception e) {
            e.printStackTrace();

            fileGroupBuying = new Uri[1];
            number = "1";
//                    try {
//                        result = groupBuyingCountTask.execute(profile.getName(), titlestr, "1").get();
//                        Log.d("resultCheck", result);
//                    } catch (ExecutionException executionException) {
//                        executionException.printStackTrace();
//                    } catch (InterruptedException interruptedException) {
//                        interruptedException.printStackTrace();
//                    }
            fileGroupBuying[0] = data.getData();

            InputStream in = null;
            try {
                in = context.getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            Bitmap img = BitmapFactory.decodeStream(in);
            try {
                in.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            // 이미지 표시
            addPhoto.setImageBitmap(img);
        }
    }

    public String getNumber() {
        return number;
    }

    public Uri[] getFileGroupBuying() {
        return fileGroupBuying;
    }

}
