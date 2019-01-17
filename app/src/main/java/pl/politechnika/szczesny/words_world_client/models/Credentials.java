package pl.politechnika.szczesny.words_world_client.models;

import com.google.gson.annotations.SerializedName;

public class Credentials {
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
}
