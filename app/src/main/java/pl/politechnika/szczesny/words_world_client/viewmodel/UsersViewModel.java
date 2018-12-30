package pl.politechnika.szczesny.words_world_client.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import pl.politechnika.szczesny.words_world_client.model.Token;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.service.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pl.politechnika.szczesny.words_world_client.helper.SharedPrefHelper.getTokenFormSP;

public class UsersViewModel extends AndroidViewModel {
    private final MutableLiveData<List<User>> allUsers;
    private final ApiManager apiManager;

    public UsersViewModel(Application application) {
        super(application);
        apiManager = ApiManager.getInstance();
        allUsers = new MutableLiveData<>();
        Token token = getTokenFormSP(application);

        if (token != null)
            fetchData(token, "");
    }

    private void fetchData (Token token, String filter) {
        apiManager.getUsersByFilter(token, filter, new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                allUsers.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                allUsers.setValue(new ArrayList<User>());
            }
        });
    }

    public LiveData<List<User>> getUsers() {
        return allUsers;
    }

    public void refreshData(Application application, String filter) {
        Token token = getTokenFormSP(application);

        if (token != null) {
            fetchData(token, filter);
        }
    }
}
