package pl.politechnika.szczesny.words_world_client.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TranslatedVoice implements Serializable {

    @SerializedName("audioContent")
    @Expose
    private String audioContent;

    public String getAudioContent() {
        return audioContent;
    }

    public void setAudioContent(String audioContent) {
        this.audioContent = audioContent;
    }
}
