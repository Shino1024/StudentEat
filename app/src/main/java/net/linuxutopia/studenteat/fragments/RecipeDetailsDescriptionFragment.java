package net.linuxutopia.studenteat.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class RecipeDetailsDescriptionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        int layoutResource = getArguments().getInt("layoutResource");
        View root = layoutInflater.inflate(layoutResource,
                viewGroup,
                false);
        Toast.makeText(getActivity(), "DescriptionOnCreateView", Toast.LENGTH_SHORT).show();

        return root;
    }

}
