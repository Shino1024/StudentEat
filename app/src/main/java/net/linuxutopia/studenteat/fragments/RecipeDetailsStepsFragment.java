package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipeDetailsStepsFragment extends Fragment {

    DisplayMetrics displayMetrics;

    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        int layoutResource = getArguments().getInt("layoutResource");
        View root = layoutInflater.inflate(layoutResource,
                viewGroup,
                false);

        displayMetrics = new DisplayMetrics();

        return root;
    }

}
