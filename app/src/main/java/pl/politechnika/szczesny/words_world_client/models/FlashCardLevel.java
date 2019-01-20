package pl.politechnika.szczesny.words_world_client.models;

public class FlashCardLevel {
    private String level;
    private Language language;

    public FlashCardLevel(String level, Language language) {
        this.level = level;
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
