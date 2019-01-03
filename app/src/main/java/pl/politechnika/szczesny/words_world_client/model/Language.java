package pl.politechnika.szczesny.words_world_client.model;


import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Locale;

import pl.politechnika.szczesny.words_world_client.helper.ConstHelper;

import static pl.politechnika.szczesny.words_world_client.helper.ConstHelper.DEFAULT_LANGUAGE_VOICE_CODE;

public class Language {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("is_subscribed")
    private Boolean isSubscribed;

    public Language() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }


}
