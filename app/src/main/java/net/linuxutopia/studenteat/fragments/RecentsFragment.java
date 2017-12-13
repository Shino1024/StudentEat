package net.linuxutopia.studenteat.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View inflatedView = layoutInflater.inflate(R.layout.recents_list,
                viewGroup,
                false);

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

    public interface OnCardSelectedListener {
        public void onCardSelected(String id);
    }

}
