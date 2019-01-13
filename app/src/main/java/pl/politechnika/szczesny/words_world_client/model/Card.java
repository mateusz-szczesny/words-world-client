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
    @SerializedName("black_list")
    private String[] blackList;

    public Card(String keyWord, long cardEfficiency, String difficulty, String[] blackList) {
        this.keyWord = keyWord;
        this.cardEfficiency = cardEfficiency;
        this.difficulty = difficulty;
        this.blackList = blackList;
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
}
