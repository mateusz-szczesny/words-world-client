package pl.politechnika.szczesny.words_world_client.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import pl.politechnika.szczesny.words_world_client.helper.SessionHelper;
import pl.politechnika.szczesny.words_world_client.model.Card;
import pl.politechnika.szczesny.words_world_client.service.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCardsViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Card>> cards;

    public MyCardsViewModel(Application application) {
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
                    Log.d("API-VM ERROR", response.errorBody().toString());
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
        String token = SessionHelper.getToken(getApplication());

        if (token != null) {
            fetchData(token);
        }
    }
}
