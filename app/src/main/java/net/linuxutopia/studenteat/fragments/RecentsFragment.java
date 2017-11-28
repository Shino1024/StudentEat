package net.linuxutopia.studenteat.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.linuxutopia.studenteat.R;

public class RecentsFragment extends Fragment {

    OnCardSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.activity_main,
                viewGroup,
                false);
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
