package net.linuxutopia.studenteat.models;

public enum Difficulty {

    BANAL("Banal"),
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard"),
    EXTREME("Extreme");

    private final String difficulty;

    Difficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getStringDescription() {
        return this.difficulty;
    }

}
