package pl.politechnika.szczesny.words_world_client.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import pl.politechnika.szczesny.words_world_client.utils.SessionUtils;
import pl.politechnika.szczesny.words_world_client.models.User;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendViewModel extends AndroidViewModel {
    private final MutableLiveData<List<User>> allFriends;
    private final ApiManager apiManager;

    public FriendViewModel(Application application) {
        super(application);
        apiManager = ApiManager.getInstance();
        allFriends = new MutableLiveData<>();

        refreshData(getApplication());
    }

    private void fetchData(String token) {
        apiManager.getFriends(token, new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                allFriends.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                allFriends.setValue(new ArrayList<User>());
            }
        });
    }

    public LiveData<List<User>> getFriends() {
        return allFriends;
    }

    public void refreshData(Application application) {
        String token = SessionUtils.getToken(getApplication());

        if (token != null) {
            fetchData(token);
        }
    }
}
