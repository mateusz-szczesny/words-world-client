package pl.politechnika.szczesny.words_world_client.service;


import okhttp3.ResponseBody;
import pl.politechnika.szczesny.words_world_client.model.Language;
import pl.politechnika.szczesny.words_world_client.model.TextToSpeechRequest;
import pl.politechnika.szczesny.words_world_client.model.TranslatedVoice;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
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

    @POST("v1beta1/text:synthesize")
    @Headers({"Content-Type: application/json"})
    Call<TranslatedVoice> synthesize(
            @Query("key") String key,
            @Body TextToSpeechRequest textToSpeechRequest
    );
}
