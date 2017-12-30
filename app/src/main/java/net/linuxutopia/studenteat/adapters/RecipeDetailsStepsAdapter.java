package net.linuxutopia.studenteat.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.models.StepModel;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeDetailsStepsAdapter
        extends RecyclerView.Adapter<RecipeDetailsStepsAdapter.ViewHolder> {

    private ArrayList<StepModel> steps;

    private DisplayMetrics displayMetrics;

    public RecipeDetailsStepsAdapter(ArrayList<StepModel> steps) {
        this.steps = steps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.step_view_holder, parent, false
        );

        return new RecipeDetailsStepsAdapter.ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        prepareStepHolderView(holder.stepHolderView, position);
        prepareNoView(holder.noView, position);
        prepareDescriptionView(holder.descriptionView, position);
        prepareMinutesView(holder.minutesView, position);
    }

    private void prepareStepHolderView(LinearLayout stepHolderView, int position) {
        stepHolderView.setPadding(0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        displayMetrics.heightPixels * 0.006f,
                        displayMetrics),
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        displayMetrics.heightPixels * 0.006f,
                        displayMetrics));
        if (position % 2 == 0) {
            stepHolderView.setBackgroundColor(
                    stepHolderView.getResources().getColor(R.color.light_black));
        }
    }

    private void prepareNoView(TextView noView, int position) {
        noView.setText(String.format(Locale.getDefault(),
                "%02d",
                position + 1));
//        noView.setTextSize(TypedValue.COMPLEX_UNIT_SP,
//                displayMetrics.heightPixels * 0.02f);
    }

    private void prepareDescriptionView(TextView descriptionView, int position) {
        descriptionView.setText(steps.get(position).getDescription());
//        descriptionView.setTextSize(TypedValue.COMPLEX_UNIT_SP,
//                displayMetrics.heightPixels * 0.01f);
    }

    private void prepareMinutesView(TextView minutesView, int position) {
        minutesView.setText(String.format(
                Locale.getDefault(),
                "%d min",
                steps.get(position).getMinutes()
        ));
//        minutesView.setTextSize(TypedValue.COMPLEX_UNIT_SP,
//                displayMetrics.heightPixels * 0.01f);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void setDisplayMetrics(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout stepHolderView;
        private TextView noView;
        private TextView descriptionView;
        private TextView minutesView;

        ViewHolder(View view) {
            super(view);
            stepHolderView = view.findViewById(R.id.recipe_details_step_holder);
            noView = view.findViewById(R.id.recipe_details_step_no);
            descriptionView = view.findViewById(R.id.recipe_details_step_description);
            minutesView = view.findViewById(R.id.recipe_details_step_minutes);
        }

    }

}
