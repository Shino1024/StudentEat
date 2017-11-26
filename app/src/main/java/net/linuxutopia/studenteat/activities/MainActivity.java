package net.linuxutopia.studenteat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.RecentsAdapter;
import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.RecentCardModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent fromIntent = getIntent();
        Boolean done = fromIntent.getBooleanExtra("splashDone", false);
        if (!done) {
            Intent splashIntent = new Intent(this, SplashActivity.class);
            startActivity(splashIntent);
            finish();
        }

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recents_list);
        //recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<RecentCardModel> dataSet = new ArrayList<>();
        dataSet.add(new RecentCardModel("lol", "buleczki 1", "mama", 4.5, 30, Difficulty.BANAL));
        dataSet.add(new RecentCardModel("jakies zdjecie", "buleczki 2", "tata", 6.5, 10, Difficulty.EASY));
        dataSet.add(new RecentCardModel("znowu cos", "cebula janusz", "grazyna", 1.0, 100, Difficulty.MEDIUM));
        dataSet.add(new RecentCardModel("kyrie elejson", "chryste elejson", "jarek", 10.0, 300, Difficulty.HARD));
        dataSet.add(new RecentCardModel("cos strasznego", "lepiej nie mowic", "nie wiadomo", 9.2, 200, Difficulty.EXTREME));

        adapter = new RecentsAdapter(dataSet);
        recyclerView.setAdapter(adapter);
    }
}
