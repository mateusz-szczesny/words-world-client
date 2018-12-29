package pl.politechnika.szczesny.words_world_client.service;

import pl.politechnika.szczesny.words_world_client.model.Token;
import pl.politechnika.szczesny.words_world_client.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("auth")
    @FormUrlEncoded
    Call<Token> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("signup")
    @FormUrlEncoded
    Call<Token> register(
            @Field("email") String email,
            @Field("password") String password,
            @Field("username") String username
    );

    @GET("api/users/me")
    Call<User> getUserData(
            @Header("Authorization") String token
    );
}
