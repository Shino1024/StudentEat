package net.linuxutopia.studenteat.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import net.linuxutopia.studenteat.R;

public class AppCompatActivityHelper {

    // TODO: getFragmentManager or getSupportFragmentManager.
    public static void loadFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static void loadFragment(FragmentManager fragmentManager,
                                    Fragment fragment,
                                    @Nullable Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public static void loadFragment(FragmentManager fragmentManager,
                                    Fragment fragment,
                                    @Nullable Bundle bundle,
                                    String tag) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    public static void setBackButtonAndTitle(Activity activity,
                                             int stringResourceID) {
        ActionBar supportActionBar = ((AppCompatActivity) activity).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(stringResourceID);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // TODO: Try to port displayLoadingDialog to here.
//    private void displayLoadingDialog(LoadingDialogFragment loadingDialogFragment) {
//        loadingDialogFragment.show(loadingDialogFragment.getActivity().getSupportFragmentManager(),
//                "LOADING_DIALOG");
//        loadingDialogFragment.setCancelable(false);
//    }
}
