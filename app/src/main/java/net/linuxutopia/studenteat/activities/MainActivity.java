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

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.fragments.AddNewRecipeFragment;
import net.linuxutopia.studenteat.fragments.FilterSortFragment;
import net.linuxutopia.studenteat.fragments.HelpFragment;
import net.linuxutopia.studenteat.fragments.RecentsFragment;
import net.linuxutopia.studenteat.utils.AppCompatActivityHelper;

import java.io.File;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

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
                        AppCompatActivityHelper.loadFragment(new RecentsFragment());
                        break;
                    case R.id.navigation_drawer_add_new_recipe:
                        AppCompatActivityHelper.loadFragment(new AddNewRecipeFragment());
                        break;
                    case R.id.navigation_drawer_filter_and_sort:
                        AppCompatActivityHelper.loadFragment(new FilterSortFragment());
                        break;
                    case R.id.navigation_drawer_my_recipes:
//                        AppCompatActivityHelper.loadFragment(new RecentsFragment());
                        break;
                    case R.id.navigation_drawer_display_help:
                        AppCompatActivityHelper.loadFragment(new HelpFragment());
                        break;
                    case R.id.navigation_drawer_sign_out:
                        AuthUI
                                .getInstance()
                                .signOut(getApplicationContext())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
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
                AppCompatActivityHelper.loadFragment(new FilterSortFragment());
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
