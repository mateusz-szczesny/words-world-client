package pl.politechnika.szczesny.words_world_client.receiver;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import pl.politechnika.szczesny.words_world_client.models.RandomWord;
import pl.politechnika.szczesny.words_world_client.network.ApiManager;
import pl.politechnika.szczesny.words_world_client.utils.NotificationHelper;
import pl.politechnika.szczesny.words_world_client.utils.SessionUtils;
import pl.politechnika.szczesny.words_world_client.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WODTService extends IntentService {
    public WODTService() {
        super("WODT");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        final NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        String token = SessionUtils.getToken(getApplication());
        ApiManager.getInstance().getRandomWord(token, new Callback<RandomWord>() {
            @Override
            public void onResponse(@NonNull Call<RandomWord> call, @NonNull Response<RandomWord> response) {
                if (response.isSuccessful()) {
                    RandomWord word = response.body();
                    if (word != null) {
                        String title = "Słowo na dziś! | ";
                        String message = "Wyraz ten pochodzi z języka "
                            + Utils.returnFlagEmojiForLanguage(word.getLanguage())
                            + ", sprawdz co oznacza!";
                        notificationHelper.createNotification(
                                title + word.getWord(),
                                message,
                                word.getWord());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RandomWord> call, @NonNull Throwable t) {
                Log.d("API ERROR", t.getMessage());
            }
        });
    }
}
