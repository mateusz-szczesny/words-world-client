package pl.politechnika.szczesny.words_world_client.service;


import okhttp3.ResponseBody;
import pl.politechnika.szczesny.words_world_client.model.TranslatedVoice;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TranslateService {

    @POST("language/translate/v2/")
    Call<ResponseBody> translate(
            @Query("q") String query,
            @Query("target") String target,
            @Query("source") String source,
            @Query("key") String key
    );
}
