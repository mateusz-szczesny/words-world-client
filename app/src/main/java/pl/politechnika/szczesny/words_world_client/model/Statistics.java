package pl.politechnika.szczesny.words_world_client.model;

import com.google.gson.annotations.SerializedName;

public class Statistics {
    @SerializedName("translated_words")
    public Integer translatedWords;
    @SerializedName("correctly_swiped_taboo_cards")
    public Integer correctlySwipedTabooCards;
}
