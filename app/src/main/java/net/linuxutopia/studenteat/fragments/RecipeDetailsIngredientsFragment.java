package net.linuxutopia.studenteat.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.adapters.RecipeDetailsIngredientsAdapter;
import net.linuxutopia.studenteat.models.IngredientModel;
import net.linuxutopia.studenteat.models.MeasureType;

import java.util.ArrayList;

public class RecipeDetailsIngredientsFragment extends Fragment {

    private View inflatedView;

    private ArrayList<IngredientModel> ingredients;

    private DisplayMetrics displayMetrics;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             @Nullable ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        int layoutResource = getArguments().getInt("layoutResource");
        inflatedView = layoutInflater.inflate(layoutResource,
                viewGroup,
                false);

        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        RecyclerView recyclerView =
                inflatedView.findViewById(R.id.recipe_details_ingredients_recyclerview);

        RecipeDetailsIngredientsAdapter adapter = new RecipeDetailsIngredientsAdapter(ingredients);
        adapter.setDisplayMetrics(displayMetrics);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return inflatedView;
    }

    public void setIngredients(ArrayList<IngredientModel> ingredients) {
        this.ingredients = ingredients;
    }
}
