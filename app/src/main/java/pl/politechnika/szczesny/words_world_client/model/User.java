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
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("following")
    private List<User> followedUsers;
    @SerializedName("achievements")
    private List<Achievement> achievements;
    @SerializedName("selected_languages")
    private List<Language> selectedLanguages;
    @SerializedName("is_friend")
    private Boolean isFriend;

    public User() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public List<Language> getSelectedLanguages() {
        return selectedLanguages;
    }

    public void setSelectedLanguages(List selectedLanguages) {
        this.selectedLanguages = selectedLanguages;
    }

    public Boolean getFriend() {
        return isFriend;
    }

    public void setFriend(Boolean friend) {
        isFriend = friend;
    }

    @Override
    public String toString() {
        return this.username;
    }
}
