package pl.politechnika.szczesny.words_world_client.model;

import android.support.annotation.NonNull;

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
    @SerializedName("overall_score")
    private OverallScore overallScore;

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

    public OverallScore getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(OverallScore overallScore) {
        this.overallScore = overallScore;
    }

    @NonNull
    @Override
    public String toString() {
        return this.username;
    }

    @Override
    public int hashCode() {
        return (int) (this.id * 31);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof User) {
            return this.getId() == ((User) obj).getId();
        } else {
            return false;
        }
    }


    public class OverallScore {
        @SerializedName("score__sum")
        private Integer score;

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }
    }
}
