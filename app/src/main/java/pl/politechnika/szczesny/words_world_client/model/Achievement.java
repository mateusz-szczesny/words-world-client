package pl.politechnika.szczesny.words_world_client.model;

import com.google.gson.annotations.SerializedName;

public class Achievement {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("level")
    private String level;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBadgeIcon() {
        return badgeIcon;
    }

    public void setBadgeIcon(String badgeIcon) {
        this.badgeIcon = badgeIcon;
    }
}


