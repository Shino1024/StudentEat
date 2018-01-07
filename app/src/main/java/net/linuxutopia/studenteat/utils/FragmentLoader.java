package net.linuxutopia.studenteat.utils;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import net.linuxutopia.studenteat.R;

public class FragmentLoader {

    public FragmentLoader() {

    }

    private void loadFragment(Fragment fragment, @Nullable Bundle bundle) {
        fragment.setArguments(bundle);
        fragment.getActivity().getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
