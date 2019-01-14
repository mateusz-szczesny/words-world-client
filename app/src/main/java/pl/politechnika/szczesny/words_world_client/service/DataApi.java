package pl.politechnika.szczesny.words_world_client.service;

import java.util.List;

import pl.politechnika.szczesny.words_world_client.model.Card;
import pl.politechnika.szczesny.words_world_client.model.Credentials;
import pl.politechnika.szczesny.words_world_client.model.Statistics;
import pl.politechnika.szczesny.words_world_client.model.Language;
import pl.politechnika.szczesny.words_world_client.model.User;
import pl.politechnika.szczesny.words_world_client.taboo.TabooCard;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface DataApi {

    @GET("api/users")
    Call<List<User>> getUsersByFilter(
        @Header("Authorization") String token,
        @Query("search") String filter
    );

    @GET("api/users/{id}")
    Call<User> getUserById(
            @Header("Authorization") String token,
            @Path("id") long id
    );

    @GET("api/users/me")
    Call<User> getUserData(
            @Header("Authorization") String token
    );

    @PUT("api/users/me/")
    Call<User> updateUserData(
            @Header("Authorization") String token,
            @Body Credentials credentials
    );

    @POST("api/users/{id}/follow/")
    Call<Void> followUser(
            @Header("Authorization") String token,
            @Path("id") long id
    );

    @POST("api/users/{id}/unfollow/")
    Call<Void> unfollowUser(
            @Header("Authorization") String token,
            @Path("id") long id
    );

    @GET("api/users/followings")
    Call<List<User>> getFollowings(
            @Header("Authorization") String token
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

    @PUT("api/statistics/push/")
    Call<Void> pushUserStatistics(
            @Header("Authorization") String token,
            @Body Statistics statistics
    );

    @GET("api/taboo/cards/random")
    Call<List<Card>> randTabooCards(
            @Header("Authorization") String token,
            @Query("language_id") long languageId,
            @Query("card_count") Integer cardCount
    );

    @GET("/api/taboo/cards")
    Call<List<Card>> getMyCards(
            @Header("Authorization") String token
    );

    @POST("/api/taboo/cards/")
    @FormUrlEncoded
    Call<Void> createNewCard(
            @Header("Authorization") String token,
            @Field("key_word") String keyWord,
            @Field("black_list") String blackList,
            @Field("language") int language
    );
}
