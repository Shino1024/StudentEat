package net.linuxutopia.studenteat.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.linuxutopia.studenteat.R;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class UploadDialogFragment extends DialogFragment {

    private MaterialProgressBar progressBar;

    private DisplayMetrics displayMetrics;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(
                R.layout.upload_dialog_layout,
                container,
                false
        );

        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        progressBar = inflatedView.findViewById(R.id.upload_dialog_progress_bar);
        progressBar.getLayoutParams().width = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                (int) (displayMetrics.widthPixels * 0.5),
                displayMetrics
        );

        return inflatedView;
    }

    public MaterialProgressBar getProgressBar() {
        return progressBar;
    }
}
