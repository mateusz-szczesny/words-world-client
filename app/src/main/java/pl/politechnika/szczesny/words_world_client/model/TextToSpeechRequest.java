package pl.politechnika.szczesny.words_world_client.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import pl.politechnika.szczesny.words_world_client.helper.ConstHelper;

import static pl.politechnika.szczesny.words_world_client.helper.ConstHelper.DEFAULT_LANGUAGE_VOICE_CODE;

public class TextToSpeechRequest {

    @SerializedName("input")
    public InputText input;
    @SerializedName("voice")
    public VoiceType voice;
    @SerializedName("audioConfig")
    public AudioConfig audioConfig;

    public TextToSpeechRequest(String inputText, String language) {
        this.input = new InputText(inputText);
        this.voice = new VoiceType(language);
        this.audioConfig = new AudioConfig();
    }

    class InputText implements Serializable {
        @SerializedName("text")
        public String text;

        public InputText(String text) {
            this.text = text;
        }
    }

    class VoiceType implements Serializable {
        public String languageCode;

        public VoiceType(String language) {
            this.languageCode = ConstHelper.langName2langVoiceCode.get(language) != null ?
                    ConstHelper.langName2langVoiceCode.get(language) :
                    DEFAULT_LANGUAGE_VOICE_CODE;

        }
    }

    class AudioConfig {
        @SerializedName("audioEncoding")
        public String audioEncoding;

        public AudioConfig() {
            this.audioEncoding = "OGG_OPUS";
        }
    }
}
