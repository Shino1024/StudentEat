package net.linuxutopia.studenteat.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.adapters.RecentsAdapter;
import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.RecentCardModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecentsFragment extends Fragment {

    OnCardSelectedListener listener;

    private RecyclerView recyclerView;
    private RecentsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             @Nullable ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View inflatedView = layoutInflater.inflate(R.layout.recents_list,
                viewGroup,
                false);

        setHasOptionsMenu(true);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setTitle(R.string.recents_action_bar_title);
        }

        recyclerView = inflatedView.findViewById(R.id.recents_list_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<RecentCardModel> dataSet = new ArrayList<>();
        dataSet.add(new RecentCardModel("aaaaa", "lol", "jakis przepis", "mama", 4.5, 30, Difficulty.BANAL));
        dataSet.add(new RecentCardModel("bbbbb", "jakies zdjecie", "w ogole jakies bardzo dlugie nie wiem jak bardzo buleczki 2", "tata", 6.5, 10, Difficulty.EASY));
        dataSet.add(new RecentCardModel("ccccc", "znowu cos", "ziemniaki", "janusz oraz grazynka", 1.0, 2000, Difficulty.MEDIUM));
        dataSet.add(new RecentCardModel("ddddd", "i znowu jakis przepis", "bardzo dziwny przepis", "konstantynopolitanczykiewiczowna konstantynopolitanczykowianeczka", 10.0, 300, Difficulty.HARD));
        dataSet.add(new RecentCardModel("eeeee", "cos strasznego", "lepiej nie mowic", "nie wiadomo", 9.2, 200, Difficulty.EXTREME));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        adapter = new RecentsAdapter(dataSet);

        Map<Difficulty, Integer> difficultyMap = new HashMap<>();
        difficultyMap.put(Difficulty.BANAL, ResourcesCompat.getColor(getResources(), R.color.difficulty_banal, null));
        difficultyMap.put(Difficulty.EASY, ResourcesCompat.getColor(getResources(), R.color.difficulty_easy, null));
        difficultyMap.put(Difficulty.MEDIUM, ResourcesCompat.getColor(getResources(), R.color.difficulty_medium, null));
        difficultyMap.put(Difficulty.HARD, ResourcesCompat.getColor(getResources(), R.color.difficulty_hard, null));
        difficultyMap.put(Difficulty.EXTREME, ResourcesCompat.getColor(getResources(), R.color.difficulty_extreme, null));

        adapter.setCardHeight((int) (displayMetrics.heightPixels * 0.3f));
        adapter.setDifficultyMap(difficultyMap);
        adapter.setDisplayMetrics(displayMetrics);

        recyclerView.setAdapter(adapter);

        FloatingActionButton floatingActionButton =
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
                Toast.makeText(getActivity(), "buka", Toast.LENGTH_SHORT).show();
                getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                    .replace(R.id.fragment_container, new AddNewRecipeFragment())
                    .addToBackStack(null)
                    .commit();
            }
        });

        return inflatedView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnCardSelectedListener) {
            listener = (OnCardSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
            + " must implement OnCardSelectedListener.");
        }
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

    public interface OnCardSelectedListener {
        public void onCardSelected(String id);
    }

}
