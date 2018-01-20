package net.linuxutopia.studenteat.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.linuxutopia.studenteat.R;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class LoadingDialogFragment extends DialogFragment {

    private MaterialProgressBar progressBar;

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

        progressBar = inflatedView.findViewById(R.id.loading_dialog_progress_bar);

        return inflatedView;
    }

    public MaterialProgressBar getProgressBar() {
        return progressBar;
    }
}