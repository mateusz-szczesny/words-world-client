package pl.politechnika.szczesny.words_world_client.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import pl.politechnika.szczesny.words_world_client.utils.SessionUtils;
import pl.politechnika.szczesny.words_world_client.models.User;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionUserViewModel extends AndroidViewModel {
    private final MutableLiveData<User> user;

    public SessionUserViewModel(Application application) {
        super(application);
        user = new MutableLiveData<>();

        refreshData();
    }

    private void fetchData (String token) {
        ApiManager.getInstance().fetchUser(token, new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                user.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("SESSION ERROR", t.getMessage());
            }
        });
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void refreshData() {
        String token = SessionUtils.getToken(getApplication());

        if (token != null) {
            fetchData(token);
        }
    }
}
