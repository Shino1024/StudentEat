package net.linuxutopia.studenteat.models;

public class RecipeCardModel {

    // TODO: Add anything else, like ID?
    private String photoDownloadLink;
    private String name;
    private String author;
    private Double rating;
    private Integer minutes;
    private Difficulty difficulty;

    public RecipeCardModel(String photoDownloadLink,
                           String name,
                           String author,
                           Double rating,
                           Integer minutes,
                           Difficulty difficulty) {
        this.photoDownloadLink = photoDownloadLink;
        this.name = name;
        this.author = author;
        this.rating = rating;
        this.minutes = minutes;
        this.difficulty = difficulty;
    }

    public RecipeCardModel() {

    }

    public String getPhotoDownloadLink() {
        return photoDownloadLink;
    }

    public void setPhotoDownloadLink(String photoDownloadLink) {
        this.photoDownloadLink = photoDownloadLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = name;
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
