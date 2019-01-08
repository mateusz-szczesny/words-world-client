package pl.politechnika.szczesny.words_world_client.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import pl.politechnika.szczesny.words_world_client.helper.SessionHelper;
import pl.politechnika.szczesny.words_world_client.model.Token;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.service.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper.getTokenFormSP;

public class SessionUserViewModel extends AndroidViewModel {
    private final MutableLiveData<User> user;

    public SessionUserViewModel(Application application) {
        super(application);
        user = new MutableLiveData<>();
        Token token = getTokenFormSP(application);

        if (token != null)
            fetchData(token);
    }

    private void fetchData (Token token) {
        ApiManager.getInstance().fetchUser(token, new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                user.setValue(response.body());
                SessionHelper.updateUserData(getApplication());
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.d("INTERNAL ERROR", "CANNOT FETCH USER DATA");
            }
        });
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void refreshData(Application application) {
        Token token = getTokenFormSP(application);

        if (token != null) {
            fetchData(token);
        }
    }
}
