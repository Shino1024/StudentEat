package net.linuxutopia.studenteat.utils;

import android.app.Fragment;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.fragments.RecipeDetailsDescriptionFragment;
import net.linuxutopia.studenteat.fragments.RecipeDetailsFragment;
import net.linuxutopia.studenteat.fragments.RecipeDetailsIngredientsFragment;
import net.linuxutopia.studenteat.fragments.RecipeDetailsRatingFragment;
import net.linuxutopia.studenteat.fragments.RecipeDetailsStepsFragment;
import net.linuxutopia.studenteat.models.RecipeDetailsModel;

public class RecipeDetailsFragmentFactory {

    private RecipeDetailsModel recipeDetailsModel;

    public static final int FRAGMENT_COUNT = 4;

    public Fragment getFragment(int position) {
        switch (position) {
            case 0:
                RecipeDetailsDescriptionFragment descriptionFragment =
                        new RecipeDetailsDescriptionFragment();
                descriptionFragment.setRecipeDetailsModel(recipeDetailsModel);
                return descriptionFragment;

            case 1:
                RecipeDetailsIngredientsFragment ingredientsFragment =
                        new RecipeDetailsIngredientsFragment();
                ingredientsFragment.setIngredients(recipeDetailsModel.getIngredients());
                return ingredientsFragment;

            case 2:
                RecipeDetailsStepsFragment stepsFragment =
                        new RecipeDetailsStepsFragment();
                stepsFragment.setSteps(recipeDetailsModel.getSteps());
                return stepsFragment;

            case 3:
                RecipeDetailsRatingFragment ratingFragment =
                        new RecipeDetailsRatingFragment();
                ratingFragment.setRecipeId(recipeDetailsModel.getId());
                return ratingFragment;

            default:
                return null;
        }
    }

    public int getLayoutResource(int position) {
        switch (position) {
            case 0:
                return R.layout.recipe_details_description;
            case 1:
                return R.layout.recipe_details_ingredients;
            case 2:
                return R.layout.recipe_details_steps;
            case 3:
                return R.layout.recipe_details_rating;
            default:
                return -1;
        }
    }

    public CharSequence getTitleResource(int position) {
        switch (position) {
            case 0:
                return "Description";
            case 1:
                return "Ingredients";
            case 2:
                return "Steps";
            case 3:
                return "Rating";
            default:
                return "error";
        }
    }

    public void setRecipeDetailsModel(RecipeDetailsModel recipeDetailsModel) {
        this.recipeDetailsModel = recipeDetailsModel;
    }
}
