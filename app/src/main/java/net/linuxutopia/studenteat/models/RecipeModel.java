package net.linuxutopia.studenteat.models;

import java.util.ArrayList;

public class RecipeModel {

    private String id;
    private String name;
    private String author;
    private String description;
    private DishCategory dishCategory;
    private Difficulty difficulty;
    private ArrayList<IngredientModel> ingredients;
    private ArrayList<StepModel> steps;
    private int minutes;
    private double rating;
    private double price;
    private int size;
    private int favorited;
    private int cooked;

    public RecipeModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DishCategory getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(DishCategory dishCategory) {
        this.dishCategory = dishCategory;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public ArrayList<IngredientModel> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientModel> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<StepModel> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<StepModel> steps) {
        this.steps = steps;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getFavorited() {
        return favorited;
    }

    public void setFavorited(int favorited) {
        this.favorited = favorited;
    }

    public int getCooked() {
        return cooked;
    }

    public void setCooked(int cooked) {
        this.cooked = cooked;
    }
}
