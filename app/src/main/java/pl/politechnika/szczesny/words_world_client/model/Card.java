package pl.politechnika.szczesny.words_world_client.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Card implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("key_word")
    private String keyWord;
    @SerializedName("card_efficiency")
    private double cardEfficiency;
    @SerializedName("difficulty")
    private String difficulty;
    @SerializedName("owner")
    private String owner;
    @SerializedName("black_list")
    private String[] blackList;
    @SerializedName("times_shown")
    private Integer timesPlayed;
    @SerializedName("language")
    private Language language;

    public Card() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String[] getBlackList() {
        return blackList;
    }

    public void setBlackList(String[] blackList) {
        this.blackList = blackList;
    }

    public double getCardEfficiency() {
        return cardEfficiency;
    }

    public void setCardEfficiency(double cardEfficiency) {
        this.cardEfficiency = cardEfficiency;
    }

    public Integer getTimesPlayed() {
        return timesPlayed;
    }

    public void setTimesPlayed(Integer timesPlayed) {
        this.timesPlayed = timesPlayed;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
