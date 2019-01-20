package pl.politechnika.szczesny.words_world_client.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import pl.politechnika.szczesny.words_world_client.utils.SessionUtils;
import pl.politechnika.szczesny.words_world_client.models.Card;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCardViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Card>> cards;

    public MyCardViewModel(Application application) {
        super(application);
        cards = new MutableLiveData<>();

        refreshData();
    }

    private void fetchData (String token) {
        ApiManager.getInstance().getMyCards(token, new Callback<List<Card>>() {
            @Override
            public void onResponse(@NonNull Call<List<Card>> call, @NonNull Response<List<Card>> response) {
                if (response.isSuccessful()) {
                    cards.setValue(response.body());
                } else {
                    Log.d("API-VM ERROR", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Card>> call, @NonNull Throwable t) {
                Log.d("API-VM ERROR", t.getMessage());
            }
        });
    }

    public LiveData<List<Card>> getCards() {
        return cards;
    }

    public void refreshData() {
        String token = SessionUtils.getToken(getApplication());

        if (token != null) {
            fetchData(token);
        }
    }
}
