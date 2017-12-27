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
        NestedScrollView.LayoutParams layoutParams =
                (NestedScrollView.LayoutParams) recyclerView.getLayoutParams();
        layoutParams.setMargins((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.widthPixels * 0.004f,
                displayMetrics),
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        displayMetrics.widthPixels * 0.004f,
                        displayMetrics),
                0);
        recyclerView.setLayoutParams(layoutParams);

        ArrayList<IngredientModel> models = new ArrayList<>();

        MeasureType measureType1;
        measureType1 = MeasureType.CUP;
        //print(measureType1);
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
        Log.w("w", (String) getResources().getText(measureType3.getStringResource()));
        IngredientModel m3 = new IngredientModel();
        m3.setAmount(30);
        m3.setMeasureType(measureType3);
        m3.setCost(90000);
        m3.setName("nic ciekawego");

        models.add(m1);
        models.add(m2);
        models.add(m3);
        RecipeDetailsIngredientsAdapter adapter = new RecipeDetailsIngredientsAdapter(models);
        adapter.setDisplayMetrics(displayMetrics);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return inflatedView;
    }

    private void print(MeasureType measureType) {
        Log.d("w", (String) getResources().getText(measureType.getStringResource()));
    }

}
