package pl.politechnika.szczesny.words_world_client.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Achievement {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("condition")
    private String condition;
    @SerializedName("font_awesome_icon")
    private String badgeIcon;

    public Achievement() {
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getBadgeIcon() {
        return badgeIcon;
    }

    public void setBadgeIcon(String badgeIcon) {
        this.badgeIcon = badgeIcon;
    }

    @NonNull
    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", condition='" + condition + '\'' +
                ", badgeIcon='" + badgeIcon + '\'' +
                '}';
    }
}


