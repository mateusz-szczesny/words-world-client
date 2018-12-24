package pl.politechnika.szczesny.words_world_client.model;

import com.google.gson.annotations.SerializedName;

public class Language {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("is_subscribed")
    private Boolean isSubscribed;

    public Language() {
    }

    public Language(long id, String name, Boolean isSubscribed) {
        this.id = id;
        this.name = name;
        this.isSubscribed = isSubscribed;
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

    @Override
    public String toString() {
        return this.name;
    }
}
