package net.linuxutopia.studenteat.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipeDetailsIngredientsFragment extends Fragment {

    DisplayMetrics displayMetrics;

    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        int layoutResource = getArguments().getInt("layoutResource");
        View inflatedView = layoutInflater.inflate(layoutResource,
                viewGroup,
                false);

        displayMetrics = new DisplayMetrics();

        return inflatedView;
    }

}
