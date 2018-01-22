package net.linuxutopia.studenteat.utils;

import net.linuxutopia.studenteat.models.RecipeDetailsModel;
import net.linuxutopia.studenteat.models.SortType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecipeComparator {

    public static void sortRecipesBy(ArrayList<RecipeDetailsModel> recipes, SortType sortType) {
        switch (sortType) {
            case SIZE:
                Collections.sort(recipes, sizeComparator);
                break;

            case TIME:
                Collections.sort(recipes, timeComparator);
                break;

            case PRICE:
                Collections.sort(recipes, priceComparator);
                break;

            case COOKED:
                Collections.sort(recipes, cookedNumberComparator);
                break;

            case RATING:
                Collections.sort(recipes, ratingComparator);
                break;

            case FAVORITES:
                Collections.sort(recipes, favoriteNumberComparator);
                break;

            case NOTHING:
                break;

            default:
                break;
        }
    }

    private static Comparator<RecipeDetailsModel> timeComparator = new Comparator<RecipeDetailsModel>() {
        @Override
        public int compare(RecipeDetailsModel recipeDetailsModel, RecipeDetailsModel t1) {
            Integer minutes0 = recipeDetailsModel.getMinutes();
            Integer minutes1 = t1.getMinutes();
            return minutes0.compareTo(minutes1);
        }
    };

    private static Comparator<RecipeDetailsModel> sizeComparator = new Comparator<RecipeDetailsModel>() {
        @Override
        public int compare(RecipeDetailsModel recipeDetailsModel, RecipeDetailsModel t1) {
            Integer size0 = recipeDetailsModel.getSize();
            Integer size1 = t1.getSize();
            return size0.compareTo(size1);
        }
    };

    private static Comparator<RecipeDetailsModel> priceComparator = new Comparator<RecipeDetailsModel>() {
        @Override
        public int compare(RecipeDetailsModel recipeDetailsModel, RecipeDetailsModel t1) {
            Double price0 = recipeDetailsModel.getPrice();
            Double price1 = t1.getPrice();
            return price0.compareTo(price1);
        }
    };

    private static Comparator<RecipeDetailsModel> ratingComparator = new Comparator<RecipeDetailsModel>() {
        @Override
        public int compare(RecipeDetailsModel recipeDetailsModel, RecipeDetailsModel t1) {
            Double rating0 = recipeDetailsModel.getRating();
            Double rating1 = t1.getRating();
            return rating1.compareTo(rating0);
        }
    };

    private static Comparator<RecipeDetailsModel> cookedNumberComparator = new Comparator<RecipeDetailsModel>() {
        @Override
        public int compare(RecipeDetailsModel recipeDetailsModel, RecipeDetailsModel t1) {
            Integer cookedNumber0 = recipeDetailsModel.getCooked();
            Integer cookedNumber1 = t1.getCooked();
            return cookedNumber1.compareTo(cookedNumber0);
        }
    };

    private static Comparator<RecipeDetailsModel> favoriteNumberComparator = new Comparator<RecipeDetailsModel>() {
        @Override
        public int compare(RecipeDetailsModel recipeDetailsModel, RecipeDetailsModel t1) {
            Integer favoriteNumber0 = recipeDetailsModel.getFavorite();
            Integer favoriteNumber1 = t1.getFavorite();
            return favoriteNumber1.compareTo(favoriteNumber0);
        }
    };
}
