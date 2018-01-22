package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.adapters.RecipeDetailsStepsAdapter;
import net.linuxutopia.studenteat.models.StepModel;

import java.util.ArrayList;

public class RecipeDetailsStepsFragment extends Fragment {

    private ArrayList<StepModel> steps;

    private DisplayMetrics displayMetrics;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             @Nullable ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        int layoutResource = getArguments().getInt("layoutResource");
        View inflatedView = layoutInflater.inflate(layoutResource,
                viewGroup,
                false);

        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        RecyclerView recyclerView =
                inflatedView.findViewById(R.id.recipe_details_steps_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecipeDetailsStepsAdapter adapter = new RecipeDetailsStepsAdapter(steps);
        adapter.setDisplayMetrics(displayMetrics);
        recyclerView.setAdapter(adapter);

        return inflatedView;
    }

    public void setSteps(ArrayList<StepModel> steps) {
        this.steps = steps;
    }
}
