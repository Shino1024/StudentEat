package net.linuxutopia.studenteat.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.adapters.RecipeCardAdapter;
import net.linuxutopia.studenteat.models.RecipeModel;
import net.linuxutopia.studenteat.utils.AppCompatActivityHelper;

import java.util.ArrayList;

public class RecipeCardsFragment extends Fragment {

    private ArrayList<RecipeModel> recipes;

    private RecyclerView recyclerView;
    private RecipeCardAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(
                R.layout.recipe_cards_list,
                container,
                false
        );

        int titleStringResource = getArguments().getInt("titleResource");
        AppCompatActivityHelper.setBackButtonAndTitle(getActivity(), titleStringResource);

        recyclerView = inflatedView.findViewById(R.id.custom_recipes_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return inflatedView;
    }

    public void setRecipesToDisplay(ArrayList<RecipeModel> recipes) {
        this.recipes = recipes;
    }
}
