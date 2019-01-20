package pl.politechnika.szczesny.words_world_client.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import pl.politechnika.szczesny.words_world_client.utils.SessionUtils;
import pl.politechnika.szczesny.words_world_client.models.Language;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanguageViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Language>> allLanguages;
    private final ApiManager apiManager;

    public LanguageViewModel(Application application) {
        super(application);
        apiManager = ApiManager.getInstance();
        allLanguages = new MutableLiveData<>();

        refreshData(application);
    }

    private void fetchData (String token) {
        apiManager.getLanguages(token, new Callback<List<Language>>() {
            @Override
            public void onResponse(@NonNull Call<List<Language>> call, @NonNull Response<List<Language>> response) {
                allLanguages.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Language>> call, @NonNull Throwable t) {
                allLanguages.setValue(new ArrayList<Language>());
            }
        });
    }

    public LiveData<List<Language>> getLanguages() {
        return allLanguages;
    }

    public void refreshData(Application application) {
        String token = SessionUtils.getToken(getApplication());

        if (token != null) {
            fetchData(token);
        }
    }
}
