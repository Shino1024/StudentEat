package net.linuxutopia.studenteat.fragments;

import android.os.Bundle;
import android.app.Fragment;
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
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        RecyclerView recyclerView =
                inflatedView.findViewById(R.id.recipe_details_ingredients_recyclerview);

        ArrayList<IngredientModel> models = new ArrayList<>();

        MeasureType measureType1;
        measureType1 = MeasureType.CUP;
        IngredientModel m1 = new IngredientModel();
        m1.setAmount(4.30002);
        m1.setMeasureType(measureType1);
        m1.setCost(5.43212);
        m1.setName("boooooooka");

        MeasureType measureType2;
        measureType2 = MeasureType.CL;
        IngredientModel m2 = new IngredientModel();
        m2.setAmount(4.3000);
        m2.setMeasureType(measureType2);
        m2.setCost(5.4);
        m2.setName("bleblebleblebleblebleblebleblbellelbebelbeleb");

        MeasureType measureType3;
        measureType3 = MeasureType.KG;
        IngredientModel m3 = new IngredientModel();
        m3.setAmount(30);
        m3.setMeasureType(measureType3);
        m3.setCost(90000);
        m3.setName("nic ciekawego");

        MeasureType measureType4;
        measureType4 = MeasureType.KG;
        IngredientModel m4 = new IngredientModel();
        m4.setAmount(30);
        m4.setMeasureType(measureType4);
        m4.setCost(90000);
        m4.setName("nic ciekawego");

        MeasureType measureType5;
        measureType5 = MeasureType.KG;
        IngredientModel m5 = new IngredientModel();
        m5.setAmount(30);
        m5.setMeasureType(measureType5);
        m5.setCost(90000);
        m5.setName("nic ciekawego");

        IngredientModel m6 = new IngredientModel();
        m6.setAmount(30);
        m6.setMeasureType(MeasureType.TEASPOON);
        m6.setCost(90000);
        m6.setName("nic ciekawego");

        IngredientModel m7 = new IngredientModel();
        m7.setAmount(30);
        m7.setMeasureType(MeasureType.TEASPOON);
        m7.setCost(90000);
        m7.setName("nic ciekawego");

        IngredientModel m8 = new IngredientModel();
        m8.setAmount(30);
        m8.setMeasureType(MeasureType.TEASPOON);
        m8.setCost(90000);
        m8.setName("nic ciekawego");

        IngredientModel m9 = new IngredientModel();
        m9.setAmount(30);
        m9.setMeasureType(MeasureType.TEASPOON);
        m9.setCost(90000);
        m9.setName("nic ciekawego");

        models.add(m1);
        models.add(m2);
        models.add(m3);
        models.add(m4);
        models.add(m5);
        models.add(m6);
        models.add(m7);
        models.add(m8);
        models.add(m9);
        RecipeDetailsIngredientsAdapter adapter = new RecipeDetailsIngredientsAdapter(models);
        adapter.setDisplayMetrics(displayMetrics);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return inflatedView;
    }

}
