package net.linuxutopia.studenteat.models;

public class RecentCardModel {

    private String photo;
    private String title;
    private String author;
    private Double rating;
    private Integer minutes;
    private Difficulty difficulty;

    public RecentCardModel(String photo,
                           String title,
                           String author,
                           Double rating,
                           Integer minutes,
                           Difficulty difficulty) {
        this.photo = photo;
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.minutes = minutes;
        this.difficulty = difficulty;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
