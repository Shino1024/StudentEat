package net.linuxutopia.studenteat.utils;

import android.app.Fragment;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.fragments.RecipeDetailsDescriptionFragment;
import net.linuxutopia.studenteat.fragments.RecipeDetailsIngredientsFragment;
import net.linuxutopia.studenteat.fragments.RecipeDetailsStepsFragment;

public class RecipeDetailsFragmentFactory {

    private final int FRAGMENT_COUNT = 3;

    public Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return new RecipeDetailsDescriptionFragment();
            case 1:
                return new RecipeDetailsIngredientsFragment();
            case 2:
                return new RecipeDetailsStepsFragment();
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
            default:
                return "error";
        }
    }

    public int getFragmentCount() {
        return FRAGMENT_COUNT;
    }

}
