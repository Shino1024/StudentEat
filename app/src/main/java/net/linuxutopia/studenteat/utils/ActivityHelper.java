package net.linuxutopia.studenteat.utils;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.linuxutopia.studenteat.R;

public class ActivityHelper {

    public static void loadFragment(AppCompatActivity activity,
                                    Fragment fragment) {
        activity.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static void loadFragment(AppCompatActivity activity,
                                    Fragment fragment,
                                    @Nullable Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        activity.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static void loadFragment(AppCompatActivity activity,
                                    Fragment fragment,
                                    @Nullable Bundle bundle,
                                    String tag) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        activity.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

}
