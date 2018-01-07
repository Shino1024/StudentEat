package net.linuxutopia.studenteat.activities;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.fragments.AddNewRecipeFragment;
import net.linuxutopia.studenteat.fragments.FilterSortFragment;
import net.linuxutopia.studenteat.fragments.RecentsFragment;

import java.io.File;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

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

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.navigation_drawer);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Snackbar.make(findViewById(R.id.recents_fragment_holder),
                user == null ? "null user" : (user.getDisplayName() == null ? "null dn" : user.getDisplayName()),
                Toast.LENGTH_SHORT).show();

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, new RecentsFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.open_drawer_button:
                DrawerLayout drawerLayout = findViewById(R.id.navigation_drawer);
                drawerLayout.openDrawer(Gravity.START);
                return true;
            case R.id.search_button:
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                        .replace(R.id.fragment_container, new FilterSortFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            case android.R.id.home:
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            AddNewRecipeFragment fragment = (AddNewRecipeFragment) getFragmentManager().findFragmentByTag("ADD_NEW_RECIPE_FRAGMENT");
            @Override
            public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                fragment.setImageLoaded();
                fragment.displayDishPhoto(imageFiles.get(0));
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                fragment.displayDishPhotoLoadingError(e.getLocalizedMessage());
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                switch (source) {
                    case CAMERA:
                        File photoFile = EasyImage.lastlyTakenButCanceledPhoto(MainActivity.this);
                        if (photoFile != null) {
                            photoFile.delete();
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onCardSelected(String id) {
        // TODO: Fill it out. It takes the recipe ID, downloads data and switches Fragments.
    }
}
