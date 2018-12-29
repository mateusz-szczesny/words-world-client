package pl.politechnika.szczesny.words_world_client.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Token implements Serializable {

    @SerializedName("token")
    private String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return "Token " + token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
