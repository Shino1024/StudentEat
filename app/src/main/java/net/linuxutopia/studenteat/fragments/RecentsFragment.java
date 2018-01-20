package net.linuxutopia.studenteat.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

public class RecentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecipeCardAdapter adapter;

    // TODO: Auto-fill all objects where possible to avoid NullPointerException, like here!!!
    private ArrayList<RecipeDetailsModel> recipes = new ArrayList<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             @Nullable ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View inflatedView = layoutInflater.inflate(R.layout.recents_list,
                viewGroup,
                false);

        setHasOptionsMenu(true);

        AppCompatActivityHelper.setBackButtonAndTitle(getActivity(),
                R.string.recents_action_bar_title);

        displayLoadingDialog();
        downloadRecipes();

        recyclerView = inflatedView.findViewById(R.id.recents_list_recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        adapter = new RecipeCardAdapter(recipes);

        Map<Difficulty, Integer> difficultyMap = new HashMap<>();
        difficultyMap.put(Difficulty.BANAL, ResourcesCompat.getColor(getResources(), R.color.difficulty_banal, null));
        difficultyMap.put(Difficulty.EASY, ResourcesCompat.getColor(getResources(), R.color.difficulty_easy, null));
        difficultyMap.put(Difficulty.MEDIUM, ResourcesCompat.getColor(getResources(), R.color.difficulty_medium, null));
        difficultyMap.put(Difficulty.HARD, ResourcesCompat.getColor(getResources(), R.color.difficulty_hard, null));
        difficultyMap.put(Difficulty.EXTREME, ResourcesCompat.getColor(getResources(), R.color.difficulty_extreme, null));

        adapter.setCardHeight((int) (displayMetrics.heightPixels * 0.3f));
        adapter.setDifficultyMap(difficultyMap);
        adapter.setDisplayMetrics(displayMetrics);

        // TODO: Might be the cause of something weird...
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);

        final FloatingActionButton floatingActionButton =
                inflatedView.findViewById(R.id.add_new_recipe_button);
        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) floatingActionButton.getLayoutParams();
        layoutParams.setMargins(0,
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        (int) (displayMetrics.widthPixels * 0.02f),
                        displayMetrics),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        (int) (displayMetrics.heightPixels * 0.02f),
                        displayMetrics));
        floatingActionButton.setLayoutParams(layoutParams);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                    .replace(R.id.fragment_container, new AddNewRecipeFragment(), "ADD_NEW_RECIPE_FRAGMENT")
                    .addToBackStack(null)
                    .commit();
                floatingActionButton.setOnClickListener(null);
            }
        });

        return inflatedView;
    }

    private void displayLoadingDialog() {
        //
    }

    private void downloadRecipes() {
        DatabaseReference recipesReference = database.getReference("recipes");
        recipesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // TODO: Asynchronous downloading will finish AFTER setting up the adapter.
                    RecipeDetailsModel recipeDetailsModel = snapshot.getValue(RecipeDetailsModel.class);
                    recipes.add(recipeDetailsModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO: Handle this somehow...
                Toast.makeText(
                        getActivity(),
                        "couldn't download recipes' details",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.action_bar_options, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

}
