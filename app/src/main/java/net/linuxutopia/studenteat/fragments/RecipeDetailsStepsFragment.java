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

    DisplayMetrics displayMetrics;

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

        ArrayList<StepModel> steps = new ArrayList<>();

        StepModel s1 = new StepModel();
        s1.setDescription("basjdbuiashdiuhasdiuashuashdwqewew");
        s1.setMinutes(300);
        StepModel s2 = new StepModel();
        s2.setDescription("buka");
        s2.setMinutes(1);
        StepModel s3 = new StepModel();
        s3.setDescription("asjaskjdojiosudonfubeawsodbfwebuodfneoidjioaqoijqwhdjfhsiufhdskfjsldijrwejopfjaeslijd;oskodjjfglgdsfljdlkfjhweihfoiwhjdoihjasljfdsoiAAAAAAAAlisd");
        s3.setMinutes(10);
        steps.add(s1);
        steps.add(s2);
        steps.add(s3);

        RecipeDetailsStepsAdapter adapter = new RecipeDetailsStepsAdapter(steps);
        adapter.setDisplayMetrics(displayMetrics);
        recyclerView.setAdapter(adapter);

        return inflatedView;
    }

}
