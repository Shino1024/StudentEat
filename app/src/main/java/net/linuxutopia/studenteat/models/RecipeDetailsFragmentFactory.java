package net.linuxutopia.studenteat.models;

import android.support.v4.app.Fragment;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.fragments.RecipeDetailsDescriptionFragment;
import net.linuxutopia.studenteat.fragments.RecipeDetailsIngredientsFragment;

public class RecipeDetailsFragmentFactory {

    final int FRAGMENT_COUNT = 2;

    public Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return new RecipeDetailsDescriptionFragment();
            case 1:
                return new RecipeDetailsIngredientsFragment();
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
            default:
                return -1;
        }
    }

    public CharSequence getTitleResource(int position) {
        switch (position) {
            case 0:
                return "Description";
            case 1:
                return "Title";
            default:
                return "error";
        }
    }

    public int getFragmentCount() {
        return FRAGMENT_COUNT;
    }

}
