package net.linuxutopia.studenteat.models;

import android.app.Fragment;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.fragments.RecipeDetailsDescriptionFragment;
import net.linuxutopia.studenteat.fragments.RecipeDetailsIngredientsFragment;
import net.linuxutopia.studenteat.fragments.RecipeDetailsStepsFragment;

public class RecipeDetailsFragmentFactory {

    final int FRAGMENT_COUNT = 3;

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
                //return Resources.getSystem().getString(R.string.recipe_details_description_title);
            case 1:
                return "Ingredients";
                //return Resources.getSystem().getString(R.string.recipe_details_ingredients_title);
            case 2:
                return "Steps";
                //return Resources.getSystem().getString(R.string.recipe_details_steps_title);
            default:
                return "error";
        }
    }

    public int getFragmentCount() {
        return FRAGMENT_COUNT;
    }

}
