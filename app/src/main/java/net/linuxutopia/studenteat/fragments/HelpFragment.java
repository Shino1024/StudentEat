package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.utils.AppCompatActivityHelper;

public class HelpFragment extends Fragment {

    private TextView helpView;

    private View inflatedView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflatedView = inflater.inflate(
                R.layout.help_text,
                container,
                false
        );

        AppCompatActivityHelper.setBackButtonAndTitle(getActivity(),
                true,
                R.string.help_screen_action_bar_title);

        helpView = inflatedView.findViewById(R.id.help_text);

        return inflatedView;
    }
}
