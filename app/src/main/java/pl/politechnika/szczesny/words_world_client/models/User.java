package pl.politechnika.szczesny.words_world_client.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    @SerializedName("id")
    private int id;
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
    @SerializedName("taboo_efficiency")
    private double tabooEfficiency;
    @SerializedName("swiped_taboo_cards")
    private Integer totalSwipesTbo;
    @SerializedName("correctly_swiped_taboo_cards")
    private Integer correctSwipesTbo;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<User> getFollowedUsers() {
        return followedUsers;
    }

    public void setFollowedUsers(List<User> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    public List<Language> getSelectedLanguages() {
        return selectedLanguages;
    }

    public void setSelectedLanguages(List<Language> selectedLanguages) {
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

    public double getTabooEfficiency() {
        return tabooEfficiency;
    }

    public void setTabooEfficiency(double tabooEfficiency) {
        this.tabooEfficiency = tabooEfficiency;
    }

    public Integer getTotalSwipesTbo() {
        return totalSwipesTbo;
    }

    public void setTotalSwipesTbo(Integer totalSwipesTbo) {
        this.totalSwipesTbo = totalSwipesTbo;
    }

    public Integer getCorrectSwipesTbo() {
        return correctSwipesTbo;
    }

    public void setCorrectSwipesTbo(Integer correctSwipesTbo) {
        this.correctSwipesTbo = correctSwipesTbo;
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
