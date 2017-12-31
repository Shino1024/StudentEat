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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.DishCategory;
import net.linuxutopia.studenteat.models.IngredientModel;
import net.linuxutopia.studenteat.models.MeasureType;
import net.linuxutopia.studenteat.models.RecipeModel;

import java.util.ArrayList;

public class AddNewRecipeFragment extends Fragment {

    private LinearLayout newRecipeLayoutContainer;
    private ImageView photo;
    private EditText nameView;
    private EditText descriptionView;
    private TextView difficultySpinnerLabel;
    private Spinner difficultySpinner;
    private TextView dishCategorySpinnerLabel;
    private Spinner dishCategorySpinner;

    private ViewGroup ingredientViewGroup;
    private ArrayList<View> ingredientViews = new ArrayList<>();
    private Button newIngredientButton;
    private ViewGroup stepViewGroup;
    private ArrayList<View> stepViews = new ArrayList<>();
    private Button newStepButton;
    private Button submitButton;

    private DisplayMetrics displayMetrics;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(
                R.layout.new_recipe,
                container,
                false
        );

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

        nameView = inflatedView.findViewById(R.id.new_recipe_name_edit);

        descriptionView = inflatedView.findViewById(R.id.new_recipe_description_edit);

        int calculatedSpinnerTopMargins = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.heightPixels * 0.01f,
                displayMetrics
        );

        difficultySpinnerLabel = inflatedView.findViewById(R.id.new_recipe_difficulty_spinner_label);
        difficultySpinnerLabel.setText(getResources().getText(
                R.string.new_recipe_difficulty_spinner_hint));
//        difficultySpinnerLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,
//                displayMetrics.heightPixels * 0.01f);
//        LinearLayout.LayoutParams difficultyViewLayoutParams =
//                (LinearLayout.LayoutParams) difficultySpinnerLabel.getLayoutParams();
//        difficultyViewLayoutParams.setMargins(
//                0,
//                calculatedSpinnerTopMargins,
//                0,
//                0
//        );
//        difficultySpinnerLabel.setLayoutParams(difficultyViewLayoutParams);

        difficultySpinner = inflatedView.findViewById(R.id.new_recipe_difficulty_spinner);
        fillUpDifficultySpinner();

        dishCategorySpinnerLabel = inflatedView.findViewById(R.id.new_recipe_category_spinner_label);
        dishCategorySpinnerLabel.setText(getResources().getText(
                R.string.new_recipe_category_spinner_hint));
//        dishCategorySpinnerLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP,
//                displayMetrics.heightPixels * 0.01f);
        dishCategorySpinner = inflatedView.findViewById(R.id.new_recipe_category_spinner);
        fillUpCategorySpinner();
//        LinearLayout.LayoutParams categoryViewLayoutParams =
//                (LinearLayout.LayoutParams) dishCategorySpinner.getLayoutParams();
//        categoryViewLayoutParams.setMargins(
//                0,
//                0,
//                0,
//                calculatedSpinnerTopMargins
//        );
//        dishCategorySpinner.setLayoutParams(categoryViewLayoutParams);

        ingredientViewGroup = inflatedView.findViewById(R.id.new_recipe_ingredients_list);
        addNewIngredient();
        addNewIngredient();
        addNewIngredient();
        Button test = ingredientViews.get(1).findViewById(R.id.new_ingredient_remove);
        Toast.makeText(getActivity(), test.getHint(), Toast.LENGTH_LONG).show();

        newIngredientButton = inflatedView.findViewById(R.id.new_recipe_new_ingredient_button);
        newIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewIngredient();
            }
        });

        stepViewGroup = inflatedView.findViewById(R.id.new_recipe_steps_list);

        newStepButton = inflatedView.findViewById(R.id.new_recipe_new_step_button);
        newStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewStep();
            }
        });

//        int calculatedTopLayoutMargin = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                displayMetrics.heightPixels * 0.01f,
//                displayMetrics
//        );
        submitButton = inflatedView.findViewById(R.id.new_recipe_submit_button);
//        LinearLayout.LayoutParams layoutParams1 =
//                (LinearLayout.LayoutParams) submitButton.getLayoutParams();
//        layoutParams1.setMargins(
//                0,
//                calculatedTopLayoutMargin,
//                0,
//                calculatedTopLayoutMargin
//        );
//        submitButton.setLayoutParams(layoutParams1);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeModel recipeModel = new RecipeModel();
                recipeModel.setName(nameView.getText().toString());
                recipeModel.setDescription(descriptionView.getText().toString());
                recipeModel.setDifficulty(
                        Difficulty.values()[difficultySpinner.getSelectedItemPosition()]
                );
                recipeModel.setDishCategory(
                        DishCategory.values()[dishCategorySpinner.getSelectedItemPosition()]
                );
                ArrayList<IngredientModel> ingredients = new ArrayList<>();
                for (View ingredientView : ingredientViews) {
                    EditText newIngredientNameView =
                            ingredientView.findViewById(R.id.new_ingredient_name);
                    EditText newIngredientAmountView =
                            ingredientView.findViewById(R.id.new_ingredient_amount);
                    Spinner newIngredientMeasureTypeView =
                            ingredientView.findViewById(R.id.new_ingredient_measure_type);
                    EditText newIngredientCostView =
                            ingredientView.findViewById(R.id.new_ingredient_cost);
                    if (
                               newIngredientNameView.getText().toString().trim().length() > 0
                            && newIngredientAmountView.getText().toString().trim().length() > 0
                            && newIngredientCostView.getText().toString().trim().length() > 0
                            ) {
                        IngredientModel ingredient = new IngredientModel();
                        ingredient.setName(newIngredientNameView.getText().toString());
                        ingredient.setAmount(Double.parseDouble(newIngredientAmountView.getText().toString()));
                        ingredient.setMeasureType(
                                MeasureType.values()[newIngredientMeasureTypeView.getSelectedItemPosition()]);
                        ingredient.setCost(Double.parseDouble(newIngredientCostView.getText().toString()));
                        ingredients.add(ingredient);
                    }
                }
            }
        });

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
        dishCategorySpinner.setAdapter(categoryDataAdapter);
    }

    private void addNewIngredient() {
        final View newIngredientView = LayoutInflater.from(getActivity()).inflate(
                R.layout.new_ingredient_holder,
                ingredientViewGroup,
                false
        );
        Button removeButton = newIngredientView.findViewById(R.id.new_ingredient_remove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childIndex = ingredientViews.indexOf(newIngredientView);
                ingredientViewGroup.removeViewAt(childIndex);
                ingredientViews.remove(childIndex);
            }
        });
        Spinner measureTypeSpinner = newIngredientView.findViewById(R.id.new_ingredient_measure_type);
        ArrayList<String> measureTypeData = new ArrayList<>();
        for (MeasureType measureType : MeasureType.values()) {
            measureTypeData.add(getResources().getString(measureType.getStringResource()));
        }
        ArrayAdapter<String> measureTypeDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                measureTypeData
        );
        measureTypeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measureTypeSpinner.setAdapter(measureTypeDataAdapter);
        ingredientViews.add(newIngredientView);
        ingredientViewGroup.addView(newIngredientView);
    }

    private void addNewStep() {
        final View newStepView = LayoutInflater.from(getActivity()).inflate(
                R.layout.new_step_holder,
                stepViewGroup,
                false
        );
        Button button = newStepView.findViewById(R.id.new_step_remove);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childIndex = stepViews.indexOf(newStepView);
                stepViewGroup.removeViewAt(childIndex);
                stepViews.remove(childIndex);
            }
        });
        stepViews.add(newStepView);
        stepViewGroup.addView(newStepView);
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
