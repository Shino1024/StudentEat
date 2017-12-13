package net.linuxutopia.studenteat.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.linuxutopia.studenteat.R;

public class RecipeDetailsIngredientsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View root = layoutInflater.inflate(R.layout.recipe_details_ingredients,
                viewGroup,
                false);

        return root;
    }

}
