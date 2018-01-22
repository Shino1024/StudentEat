package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.DishCategory;
import net.linuxutopia.studenteat.models.IngredientModel;
import net.linuxutopia.studenteat.models.RecipeDetailsModel;
import net.linuxutopia.studenteat.models.SortType;
import net.linuxutopia.studenteat.utils.AppCompatActivityHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FilterSortFragment extends Fragment {

    private EditText recipeNameView;
    private EditText authorView;
    private EditText timeView;
    private EditText priceView;
    private EditText ratingView;
    private EditText sizeView;

    private String recipeName;
    private String author;
    private Integer minutes;
    private Double price;
    private Double rating;
    private Integer size;
    private Difficulty difficulty;
    private ArrayList<DishCategory> dishCategories = new ArrayList<>();
    private ArrayList<String> ingredientNames = new ArrayList<>();
    private SortType sortType;

    private Spinner difficultySpinner;
    private ViewGroup ingredientViewGroup;
    private ArrayList<View> ingredientViews = new ArrayList<>();
    private LinearLayout categoryCheckBoxHolder;
    private ArrayList<CheckBox> categoryCheckBoxes = new ArrayList<>();
    private Button addFilteredIngredientButton;

    private Spinner sortSpinner;

    private Button submitButton;

    private ArrayList<RecipeDetailsModel> recipes = new ArrayList<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

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

        AppCompatActivityHelper.setBackButtonAndTitle(getActivity(),
                true,
                R.string.filter_action_bar_title);

        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        recipeNameView = inflatedView.findViewById(R.id.filter_recipe_name_edit);

        authorView = inflatedView.findViewById(R.id.filter_full_name_edit);

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

        sortSpinner = inflatedView.findViewById(R.id.filter_sort_spinner);

        submitButton = inflatedView.findViewById(R.id.filter_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                displayLoadingDialog();
                setupFilterAndSortData();
                getResults();
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

    private void setupFilterAndSortData() {
        if (!recipeNameView.getText().toString().trim().matches("")) {
            recipeName = recipeNameView.getText().toString().trim();
        }
        if (!authorView.getText().toString().trim().matches("")) {
            author = authorView.getText().toString().trim();
        }
        if (!timeView.getText().toString().trim().matches("")) {
            minutes = Integer.parseInt(timeView.getText().toString().trim());
        }
        if (!priceView.getText().toString().trim().matches("")) {
            price = Double.parseDouble(priceView.getText().toString().trim());
        }
        if (!ratingView.getText().toString().trim().matches("")) {
            rating = Double.parseDouble(ratingView.getText().toString().trim());
        }
        if (!sizeView.getText().toString().trim().matches("")) {
            size = Integer.parseInt(sizeView.getText().toString().trim());
        }

        difficulty = Difficulty.values()[difficultySpinner.getSelectedItemPosition()];

        for (int i = 0; i < categoryCheckBoxes.size(); ++i) {
            if (categoryCheckBoxes.get(i).isChecked()) {
                dishCategories.add(DishCategory.values()[i]);
            }
        }

        for (View ingredientView : ingredientViews) {
            EditText ingredientNameEdit =
                    ingredientView.findViewById(R.id.filter_ingredient_edit);
            if (!ingredientNameEdit.getText().toString().trim().matches("")) {
                ingredientNames.add(ingredientNameEdit.getText().toString().trim());
            }
        }

        sortType = SortType.values()[sortSpinner.getSelectedItemPosition()];
    }

    private void getResults() {
        DatabaseReference recipesReference = database.getReference("recipes");
        recipesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipes = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    recipes.add(child.getValue(RecipeDetailsModel.class));
                }
                filterResults();
                RecipeCardsFragment filteredRecipesFragment = new RecipeCardsFragment();
                Bundle resourceBundle = new Bundle();
                resourceBundle.putInt("titleResource", R.string.filtered_results_action_bar_title);
                filteredRecipesFragment.setArguments(resourceBundle);
                filteredRecipesFragment.setRecipesToDisplay(recipes);
                AppCompatActivityHelper.loadFragment(getFragmentManager(), filteredRecipesFragment);
            }

            // TODO: Handle it better, probably.
            @Override
            public void onCancelled(DatabaseError databaseError) {
                String errorMessage =
                        getResources().getString(R.string.new_recipe_on_failure_message_prelude)
                                + databaseError.getDetails();
                Toast.makeText(
                        getActivity(),
                        errorMessage,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void filterResults() {
        ArrayList<RecipeDetailsModel> incorrectRecipes = new ArrayList<>();
        if (recipeName != null) {
            for (RecipeDetailsModel recipe : recipes) {
                if (!recipe.getName().contains(recipeName)) {
                    incorrectRecipes.add(recipe);
                }
            }
        }
        recipes.removeAll(incorrectRecipes);
        incorrectRecipes.clear();

        if (author != null) {
            for (RecipeDetailsModel recipe : recipes) {
                if (!recipe.getAuthor().contains(author)) {
                    incorrectRecipes.add(recipe);
                }
            }
        }
        recipes.removeAll(incorrectRecipes);
        incorrectRecipes.clear();

        if (minutes != null) {
            for (RecipeDetailsModel recipe : recipes) {
                if (recipe.getMinutes() > minutes) {
                    incorrectRecipes.add(recipe);
                }
            }
        }
        recipes.removeAll(incorrectRecipes);
        incorrectRecipes.clear();

        if (price != null) {
            for (RecipeDetailsModel recipe : recipes) {
                if (recipe.getPrice() > price) {
                    incorrectRecipes.add(recipe);
                }
            }
        }
        recipes.removeAll(incorrectRecipes);
        incorrectRecipes.clear();

        if (rating != null) {
            for (RecipeDetailsModel recipe : recipes) {
                if (recipe.getRating() > 0.0d && recipe.getRating() < rating) {
                    incorrectRecipes.add(recipe);
                }
            }
        }
        recipes.removeAll(incorrectRecipes);
        incorrectRecipes.clear();

        if (size != null) {
            for (RecipeDetailsModel recipe : recipes) {
                if (recipe.getSize() > size) {
                    incorrectRecipes.add(recipe);
                }
            }
        }
        recipes.removeAll(incorrectRecipes);
        incorrectRecipes.clear();

        if (difficulty != null) {
            for (RecipeDetailsModel recipe : recipes) {
                if (recipe.getDifficulty().ordinal() > difficulty.ordinal()) {
                    incorrectRecipes.add(recipe);
                }
            }
        }
        recipes.removeAll(incorrectRecipes);
        incorrectRecipes.clear();

        if (dishCategories.size() > 0) {
            for (RecipeDetailsModel recipe : recipes) {
                if (!dishCategories.contains(recipe.getDishCategory())) {
                    incorrectRecipes.add(recipe);
                }
            }
        }
        recipes.removeAll(incorrectRecipes);
        incorrectRecipes.clear();

        if (ingredientNames.size() > 0) {
            ArrayList<String> lowerCaseGivenIngredients = new ArrayList<>();
            for (String ingredientName : ingredientNames) {
                lowerCaseGivenIngredients.add(ingredientName.toLowerCase());
            }
            for (RecipeDetailsModel recipe : recipes) {
                ArrayList<String> lowerCaseRecipeIngredients = new ArrayList<>();
                for (IngredientModel ingredient : recipe.getIngredients()) {
                    lowerCaseRecipeIngredients.add(ingredient.getName().toLowerCase());
                }
                for (String givenIngredientName : lowerCaseGivenIngredients) {
                    if (!lowerCaseRecipeIngredients.contains(givenIngredientName)) {
                        incorrectRecipes.add(recipe);
                        break;
                    }
                }
            }
        }
        recipes.removeAll(incorrectRecipes);
        incorrectRecipes.clear();
    }
}
