package pl.politechnika.szczesny.words_world_client.taboo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Card implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("key_word")
    @Expose
    private String keyWord;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("black_list")
    @Expose
    private String[] blackList;

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
}
