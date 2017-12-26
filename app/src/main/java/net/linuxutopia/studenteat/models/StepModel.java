package net.linuxutopia.studenteat.models;

public class StepModel {

    private String description;
    private int minutes;

    public StepModel(String description, int minutes) {
        this.description = description;
        this.minutes = minutes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
