package net.linuxutopia.studenteat.activities;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.fragments.RecentsFragment;
import net.linuxutopia.studenteat.fragments.RecipeDetailsFragment;

public class MainActivity extends AppCompatActivity implements RecentsFragment.OnCardSelectedListener {

    DrawerLayout drawerLayout;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.navigation_drawer);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, new RecentsFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment, @Nullable Bundle bundle) {
        fragment.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCardSelected(String id) {
        // TODO: Fill it out. It takes the recipe ID, downloads data and switches Fragments.
    }
}
