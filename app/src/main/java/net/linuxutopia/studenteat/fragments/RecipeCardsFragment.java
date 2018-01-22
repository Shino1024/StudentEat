package net.linuxutopia.studenteat.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.adapters.RecipeCardAdapter;
import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.RecipeDetailsModel;
import net.linuxutopia.studenteat.utils.AppCompatActivityHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeCardsFragment extends Fragment {

    private ArrayList<RecipeDetailsModel> recipes;

    private RecyclerView recyclerView;
    private RecipeCardAdapter adapter;

    private View inflatedView;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DisplayMetrics displayMetrics = new DisplayMetrics();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        inflatedView = inflater.inflate(
                R.layout.recipe_cards_list,
                container,
                false
        );

        int titleStringResource = getArguments().getInt("titleResource");
        AppCompatActivityHelper.setBackButtonAndTitle(getActivity(),
                true,
                titleStringResource);

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        prepareRecyclerWithAdapter();

        return inflatedView;
    }

    public void setRecipesToDisplay(ArrayList<RecipeDetailsModel> recipes) {
        this.recipes = recipes;
    }

    private void prepareRecyclerWithAdapter() {
        Map<Difficulty, Integer> difficultyMap = new HashMap<>();
        difficultyMap.put(Difficulty.BANAL, ResourcesCompat.getColor(getResources(), R.color.difficulty_banal, null));
        difficultyMap.put(Difficulty.EASY, ResourcesCompat.getColor(getResources(), R.color.difficulty_easy, null));
        difficultyMap.put(Difficulty.MEDIUM, ResourcesCompat.getColor(getResources(), R.color.difficulty_medium, null));
        difficultyMap.put(Difficulty.HARD, ResourcesCompat.getColor(getResources(), R.color.difficulty_hard, null));
        difficultyMap.put(Difficulty.EXTREME, ResourcesCompat.getColor(getResources(), R.color.difficulty_extreme, null));

        adapter = new RecipeCardAdapter(recipes);
        adapter.setCardHeight((int) (displayMetrics.heightPixels * 0.3f));
        adapter.setDifficultyMap(difficultyMap);
        adapter.setDisplayMetrics(displayMetrics);

        recyclerView = inflatedView.findViewById(R.id.custom_recipes_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
