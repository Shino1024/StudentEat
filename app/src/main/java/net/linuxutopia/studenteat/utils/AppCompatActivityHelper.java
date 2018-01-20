package net.linuxutopia.studenteat.utils;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import net.linuxutopia.studenteat.R;

public class AppCompatActivityHelper {

    // TODO: getFragmentManager or getSupportFragmentManager.
    public static void loadFragment(Fragment fragment) {
        AppCompatActivity activity = ((AppCompatActivity) fragment.getActivity());
        activity.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static void loadFragment(Fragment fragment,
                                    @Nullable Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        AppCompatActivity activity = ((AppCompatActivity) fragment.getActivity());
        activity.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static void loadFragment(Fragment fragment,
                                    @Nullable Bundle bundle,
                                    String tag) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        AppCompatActivity activity = ((AppCompatActivity) fragment.getActivity());
        activity.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    public static void setBackButtonAndTitle(Activity activity, int stringResourceID) {
        ActionBar supportActionBar = ((AppCompatActivity) activity).getSupportActionBar();
        FragmentManager supportFragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();
        if (supportActionBar != null) {
            supportActionBar.setTitle(stringResourceID);
            if (supportFragmentManager.getBackStackEntryCount() > 0) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                supportActionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }
}
