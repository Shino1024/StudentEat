package net.linuxutopia.studenteat.utils;

import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.DishCategory;
import net.linuxutopia.studenteat.models.IngredientModel;

import java.util.ArrayList;

public class RecipeQueryBuilder {

    private String recipeName;
    private String fullAuthorName;
    private Integer minutes;
    private Integer price;
    private Double rating;
    private Integer size;
    private Difficulty difficulty;
    private ArrayList<DishCategory> categories;
    private ArrayList<IngredientModel> ingredients;
    private String sortType;

    public RecipeQueryBuilder() {

    }

    public RecipeQueryBuilder setRecipeName(String recipeName) {
        this.recipeName = recipeName;
        return this;
    }

    public RecipeQueryBuilder setFullAuthorName(String fullAuthorName) {
        this.fullAuthorName = fullAuthorName;
        return this;
    }

    public RecipeQueryBuilder setMinutes(Integer minutes) {
        this.minutes = minutes;
        return this;
    }

    public RecipeQueryBuilder setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public RecipeQueryBuilder setRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public RecipeQueryBuilder setSize(Integer size) {
        this.size = size;
        return this;
    }

    public RecipeQueryBuilder setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public RecipeQueryBuilder setCategories(ArrayList<DishCategory> categories) {
        this.categories = categories;
        return this;
    }

    public RecipeQueryBuilder setIngredients(ArrayList<IngredientModel> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public RecipeQueryBuilder setSortType(String sortType) {
        this.sortType = sortType;
        return this;
    }

    public void executeQuery() {
        //
    }

//    public S iterate() {
//        //
//    }

}
