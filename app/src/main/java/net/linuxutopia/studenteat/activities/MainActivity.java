package net.linuxutopia.studenteat.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.fragments.AddNewRecipeFragment;
import net.linuxutopia.studenteat.fragments.FilterSortFragment;
import net.linuxutopia.studenteat.fragments.HelpFragment;
import net.linuxutopia.studenteat.fragments.LoadingDialogFragment;
import net.linuxutopia.studenteat.fragments.RecentsFragment;
import net.linuxutopia.studenteat.fragments.RecipeCardsFragment;
import net.linuxutopia.studenteat.models.RecipeDetailsModel;
import net.linuxutopia.studenteat.utils.AppCompatActivityHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private FirebaseAuth auth = FirebaseAuth.getInstance();

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

        drawerLayout = findViewById(R.id.navigation_drawer);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(Gravity.START);
                switch (item.getItemId()) {
                    case R.id.navigation_drawer_recent_recipes:
                        AppCompatActivityHelper.loadFragment(getFragmentManager(),
                                new RecentsFragment());
                        break;

                    case R.id.navigation_drawer_add_new_recipe:
                        AppCompatActivityHelper.loadFragment(getFragmentManager(),
                                new AddNewRecipeFragment());
                        break;

                    case R.id.navigation_drawer_filter_and_sort:
                        AppCompatActivityHelper.loadFragment(getFragmentManager(),
                                new FilterSortFragment());
                        break;

                    case R.id.navigation_drawer_my_recipes:
                        loadMyRecipesFragment();
                        break;

                    case R.id.navigation_drawer_display_help:
                        AppCompatActivityHelper.loadFragment(getFragmentManager(),
                                new HelpFragment());
                        break;

                    case R.id.navigation_drawer_sign_out:
                        AuthUI
                                .getInstance()
                                .signOut(getApplicationContext())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                R.string.logged_out_message,
                                                Toast.LENGTH_SHORT
                                        ).show();
                                        finish();
                                    }
                                });
                        break;

                    default:
                        break;
                }
                return false;
            }
        });

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
                AppCompatActivityHelper.loadFragment(getFragmentManager(),
                        new FilterSortFragment());
                return true;

            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void loadMyRecipesFragment() {
        DatabaseReference recipeReference = database.getReference("recipes");
        recipeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<RecipeDetailsModel> myRecipes = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RecipeDetailsModel recipe = snapshot.getValue(RecipeDetailsModel.class);
                    if (recipe.getAuthorId().equals(auth.getCurrentUser().getUid())) {
                        myRecipes.add(recipe);
                    }
                }
                RecipeCardsFragment myRecipesFragment = new RecipeCardsFragment();
                myRecipesFragment.setRecipesToDisplay(myRecipes);
                Bundle resourceBundle = new Bundle();
                resourceBundle.putInt("titleResource", R.string.my_results_action_bar_title);
                myRecipesFragment.setArguments(resourceBundle);
                AppCompatActivityHelper.loadFragment(getFragmentManager(), myRecipesFragment);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                fragment.onImageLoaded(imageFiles.get(0));
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
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case AddNewRecipeFragment.REQUEST_CODE_OPEN_CAMERA:
                AddNewRecipeFragment fragment = (AddNewRecipeFragment) getFragmentManager().findFragmentByTag("ADD_NEW_RECIPE_FRAGMENT");
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    fragment.openCamera();
                } else {
                    fragment.deniedOpenCameraPermissions();
                }
                break;
            default:
                break;
        }
    }
}
