package pl.politechnika.szczesny.words_world_client.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    @SerializedName("id")
    private long id;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;

    private Token token;

    @SerializedName("following")
    private List<User> followedUsers;
    @SerializedName("achievements")
    private List<Achievement> achievements;
    @SerializedName("selected_languages")
    private List<Language> selectedLanguages;

    public User() {
    }

    public User(long id, String email, String username, List followedUsers, List achievements, List selectedLanguages) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.followedUsers = followedUsers;
        this.achievements = achievements;
        this.selectedLanguages = selectedLanguages;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public List getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(List followedUsers) {
        this.followedUsers = followedUsers;
    }

    public List getAchievements() {
        return achievements;
    }

    public void setAchievements(List achievements) {
        this.achievements = achievements;
    }

    public List getSelectedLanguages() {
        return selectedLanguages;
    }

    public void setSelectedLanguages(List selectedLanguages) {
        this.selectedLanguages = selectedLanguages;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
