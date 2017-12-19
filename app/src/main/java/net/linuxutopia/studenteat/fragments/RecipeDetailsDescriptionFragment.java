package net.linuxutopia.studenteat.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.linuxutopia.studenteat.R;

public class RecipeDetailsDescriptionFragment extends Fragment {

    public RecipeDetailsDescriptionFragment() {
        super();
        Log.d("info", "RDDF created");
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View root = layoutInflater.inflate(R.layout.recipe_details_description,
                viewGroup,
                false);
        Toast.makeText(getActivity(), "DescriptionOnCreateView", Toast.LENGTH_SHORT).show();

        return root;
    }

}
