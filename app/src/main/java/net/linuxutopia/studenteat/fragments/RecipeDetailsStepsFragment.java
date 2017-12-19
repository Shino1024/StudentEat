package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.linuxutopia.studenteat.R;

public class RecipeDetailsStepsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View root = layoutInflater.inflate(R.layout.recipe_details_steps,
                viewGroup,
                false);

        return root;
    }

}
