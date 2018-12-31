package pl.politechnika.szczesny.words_world_client.service;

import java.util.List;

import pl.politechnika.szczesny.words_world_client.model.Language;
import pl.politechnika.szczesny.words_world_client.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataApi {

    @GET("api/users")
    Call<List<User>> getUsersByFilter(
        @Header("Authorization") String token,
        @Query("search") String filter
    );

    @GET("api/languages")
    Call<List<Language>> getLanguages(
        @Header("Authorization") String token
    );

    @POST("api/languages/{id}/subscribe/")
    Call<Void> subscribeLanguage(
        @Header("Authorization") String token,
        @Path("id") long id
    );

    @POST("api/languages/{id}/unsubscribe/")
    Call<Void> unsubscribeLanguage(
            @Header("Authorization") String token,
            @Path("id") long id
    );

    @GET("api/languages/get_subscribed")
    Call<List<Language>> getSubscribedLanguages(
        @Header("Authorization") String token
    );


}
