package com.example.capstondesign.ui.groupbuying;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.capstondesign.network.bulletin.groupbuying.AddGroupbuyingService;
import com.example.capstondesign.repository.GroupbuyingRepository;
import java.io.IOException;

public class GroupbuyingViewModel extends ViewModel {
    GroupbuyingRepository repository = new GroupbuyingRepository();
    public LiveData<Groupbuying> groupbuying = repository._groupbuying;
    AddGroupbuyingService addGroupbuyingService = new AddGroupbuyingService();;

    public void loadGroupbuying() {
        try {
            repository.loadGroupbuying();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LiveData<Groupbuying> getAll() {
        return groupbuying;
    }

    //들어갈 내용 다시 정의해야함
    public void addGroupbuying (String nick, String title, String text, String time) {
        addGroupbuyingService.execute(nick, title, text, time);
    }
}
