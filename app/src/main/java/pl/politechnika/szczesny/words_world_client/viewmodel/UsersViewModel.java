package pl.politechnika.szczesny.words_world_client.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import pl.politechnika.szczesny.words_world_client.utils.SessionUtils;
import pl.politechnika.szczesny.words_world_client.models.User;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersViewModel extends AndroidViewModel {
    private final MutableLiveData<List<User>> allUsers;
    private final ApiManager apiManager;

    public UsersViewModel(Application application) {
        super(application);
        apiManager = ApiManager.getInstance();
        allUsers = new MutableLiveData<>();
    }

    private void fetchData (String token, String filter) {
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

    public void refreshData(String filter) {
        String token = SessionUtils.getToken(getApplication());

        if (token != null) {
            fetchData(token, filter);
        }
    }
}
