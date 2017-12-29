package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.DishCategory;

import java.util.ArrayList;

public class AddNewRecipeFragment extends Fragment {

    LinearLayout newRecipeLayoutContainer;
    ImageView photo;
    TextView nameView;
    TextView descriptionView;
    TextView difficultySpinnerLabel;
    Spinner difficultySpinner;
    TextView categorySpinnerLabel;
    Spinner categorySpinner;

    DisplayMetrics displayMetrics;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.new_recipe,
                container,
                false);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setTitle(R.string.new_recipe_action_bar_title);
        }

        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        newRecipeLayoutContainer =
                inflatedView.findViewById(R.id.new_recipe_container);
        ScrollView.LayoutParams layoutParams =
                (ScrollView.LayoutParams) newRecipeLayoutContainer.getLayoutParams();
        int calculatedSideMarginSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.widthPixels * 0.006f,
                displayMetrics);
        int calculatedTopMarginSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.heightPixels * 0.006f,
                displayMetrics);
        layoutParams.setMargins(
                calculatedSideMarginSize,
                calculatedTopMarginSize,
                calculatedSideMarginSize,
                calculatedTopMarginSize
        );
        newRecipeLayoutContainer.setLayoutParams(layoutParams);

        photo = inflatedView.findViewById(R.id.new_recipe_photo);
        photo.requestLayout();
        photo.getLayoutParams().height = (int) (displayMetrics.heightPixels * 0.3f);

        difficultySpinnerLabel = inflatedView.findViewById(R.id.new_recipe_difficulty_spinner_label);
        difficultySpinnerLabel.setText(getResources().getText(
                R.string.new_recipe_difficulty_spinner_hint));
        difficultySpinnerLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                displayMetrics.heightPixels * 0.01f);

        difficultySpinner = inflatedView.findViewById(R.id.new_recipe_difficulty_spinner);
        fillUpDifficultySpinner();

        categorySpinnerLabel = inflatedView.findViewById(R.id.new_recipe_category_spinner_label);
        categorySpinnerLabel.setText(getResources().getText(
                R.string.new_recipe_category_spinner_hint));
        categorySpinnerLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                displayMetrics.heightPixels * 0.01f);

        categorySpinner = inflatedView.findViewById(R.id.new_recipe_category_spinner);
        fillUpCategorySpinner();

        //

        return inflatedView;
    }

    private void fillUpDifficultySpinner() {
        ArrayList<String> difficultyData = new ArrayList<>();
        for (Difficulty difficulty : Difficulty.values()) {
            difficultyData.add(getResources().getString(difficulty.getStringResource()));
        }
        ArrayAdapter<String> difficultyDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                difficultyData
        );
        difficultyDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyDataAdapter);
        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), getResources().getText(Difficulty.values()[i].getStringResource()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fillUpCategorySpinner() {
        ArrayList<String> categoryData = new ArrayList<>();
        for (DishCategory category : DishCategory.values()) {
            categoryData.add(getResources().getString(category.getStringResource()));
        }
        ArrayAdapter<String> categoryDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                categoryData
        );
        categoryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryDataAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}
