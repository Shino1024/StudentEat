package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.DishCategory;
import net.linuxutopia.studenteat.models.IngredientModel;
import net.linuxutopia.studenteat.models.RecipeModel;
import net.linuxutopia.studenteat.models.SortType;
import net.linuxutopia.studenteat.utils.RecipeQueryBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FilterSortFragment extends Fragment {

    private EditText recipeNameView;
    private EditText fullNameView;
    private EditText timeView;
    private EditText priceView;
    private EditText ratingView;
    private EditText sizeView;

    private Spinner difficultySpinner;
    private ViewGroup ingredientViewGroup;
    private ArrayList<View> ingredientViews = new ArrayList<>();
    private LinearLayout categoryCheckBoxHolder;
    private ArrayList<CheckBox> categoryCheckBoxes = new ArrayList<>();
    private Button addFilteredIngredientButton;

    private Spinner sortSpinner;

    private Button submitButton;

    private ArrayList<RecipeModel> recipeModels;

    private DisplayMetrics displayMetrics = new DisplayMetrics();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(
                R.layout.sort_and_filter,
                container,
                false
        );

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setTitle(R.string.filter_action_bar_title);
        }

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        recipeNameView = inflatedView.findViewById(R.id.filter_recipe_name_edit);

        fullNameView = inflatedView.findViewById(R.id.filter_full_name_edit);

        timeView = inflatedView.findViewById(R.id.filter_time_edit);

        priceView = inflatedView.findViewById(R.id.filter_price_edit);

        ratingView = inflatedView.findViewById(R.id.filter_rating_edit);

        sizeView = inflatedView.findViewById(R.id.filter_size_edit);

        difficultySpinner = inflatedView.findViewById(R.id.filter_difficulty_spinner);
        fillUpDifficultySpinner();

        ingredientViewGroup = inflatedView.findViewById(R.id.filter_ingredient_view_holder);

        prepareCheckBoxes(inflatedView);

        addFilteredIngredientButton = inflatedView.findViewById(R.id.filter_add_ingredient_button);
        addFilteredIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewIngredient();
            }
        });

        sortSpinner = inflatedView.findViewById(R.id.sort_spinner);

        submitButton = inflatedView.findViewById(R.id.filter_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeQueryBuilder builder = new RecipeQueryBuilder();
                if (!recipeNameView.getText().toString().trim().matches("")) {
                    builder.setRecipeName(recipeNameView.getText().toString().trim());
                }
                if (!fullNameView.getText().toString().trim().matches("")) {
                    builder.setFullAuthorName(fullNameView.getText().toString().trim());
                }
                if (!timeView.getText().toString().trim().matches("")) {
                    builder.setMinutes(Integer.parseInt(timeView.getText().toString().trim()));
                }
                if (!priceView.getText().toString().trim().matches("")) {
                    builder.setPrice(Double.parseDouble(priceView.getText().toString().trim()));
                }
                if (!ratingView.getText().toString().trim().matches("")) {
                    builder.setRating(Double.parseDouble(ratingView.getText().toString().trim()));
                }
                if (!sizeView.getText().toString().trim().matches("")) {
                    builder.setSize(Integer.parseInt(sizeView.getText().toString().trim()));
                }
                builder.setDifficulty(Difficulty.values()[difficultySpinner.getSelectedItemPosition()]);

                ArrayList<DishCategory> dishCategories = new ArrayList<>();
                for (int i = 0; i < categoryCheckBoxes.size(); ++i) {
                    if (categoryCheckBoxes.get(i).isChecked()) {
                        dishCategories.add(DishCategory.values()[i]);
                    }
                }
                builder.setCategories(dishCategories);

                ArrayList<String> ingredientNames = new ArrayList<>();
                for (View ingredientView : ingredientViews) {
                    EditText ingredientNameEdit =
                            ingredientView.findViewById(R.id.filter_ingredient_edit);
                    if (!ingredientNameEdit.getText().toString().trim().matches("")) {
                        ingredientNames.add(ingredientNameEdit.getText().toString().trim());
                    }
                }
                builder.setIngredients(ingredientNames);
                builder.setSortType(SortType.values()[sortSpinner.getSelectedItemPosition()]);

                builder.executeQueryWithActivity(getActivity());
                recipeModels = builder.returnResultingRecipes();
            }
        });

        return inflatedView;
    }

    private void addNewIngredient() {
        final View newIngredientView = LayoutInflater.from(getActivity()).inflate(
                R.layout.filter_ingredient_holder,
                ingredientViewGroup,
                false
        );
        Button removeButton = newIngredientView.findViewById(R.id.filter_ingredient_remove_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childIndex = ingredientViews.indexOf(newIngredientView);
                ingredientViewGroup.removeViewAt(childIndex);
                ingredientViews.remove(childIndex);
            }
        });
        ingredientViews.add(newIngredientView);
        ingredientViewGroup.addView(newIngredientView);
    }

    private void prepareCheckBoxes(View inflatedView) {
        categoryCheckBoxHolder = inflatedView.findViewById(R.id.filter_category_check_box_holder);
        for (DishCategory category : DishCategory.values()) {
            CheckBox categoryCheckBox = new CheckBox(getActivity());
            categoryCheckBox.setText(getResources().getText(category.getStringResource()));
            categoryCheckBox.setTextSize(TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP,
                    6.0f,
                    displayMetrics
            ));
            categoryCheckBoxes.add(categoryCheckBox);
            categoryCheckBoxHolder.addView(categoryCheckBox);
        }
    }

    private void fillUpDifficultySpinner() {
        ArrayList<String> difficultyData = new ArrayList<>();
        List<Difficulty> reversedDifficulties = Arrays.asList(Difficulty.values());
        Collections.reverse(reversedDifficulties);
        for (Difficulty difficulty : reversedDifficulties) {
            difficultyData.add(getResources().getString(difficulty.getStringResource()));
        }
        ArrayAdapter<String> difficultyDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                difficultyData
        );
        difficultyDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyDataAdapter);
    }
}
