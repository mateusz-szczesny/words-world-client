package pl.politechnika.szczesny.words_world_client.models;


import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;


public class Language {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("is_subscribed")
    private Boolean isSubscribed;
    @SerializedName("language_code")
    private String languageCode;

    public Language() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        isSubscribed = subscribed;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
