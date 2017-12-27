package net.linuxutopia.studenteat.models;

import net.linuxutopia.studenteat.R;

public enum Difficulty {

    BANAL(R.string.difficulty_banal),
    EASY(R.string.difficulty_easy),
    MEDIUM(R.string.difficulty_medium),
    HARD(R.string.difficulty_hard),
    EXTREME(R.string.difficulty_extreme);

    private final int difficultyResource;

    Difficulty(int difficultyResource) {
        this.difficultyResource = difficultyResource;
    }

    public int getStringResource() {
        return this.difficultyResource;
    }

}
