package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.DishCategory;
import net.linuxutopia.studenteat.models.IngredientModel;
import net.linuxutopia.studenteat.models.RecipeModel;
import net.linuxutopia.studenteat.models.SortType;
import net.linuxutopia.studenteat.utils.AppCompatActivityHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private ArrayList<DishCategory> dishCategories;
    private ArrayList<String> ingredientNames;
    private SortType sortType;

    private Spinner difficultySpinner;
    private ViewGroup ingredientViewGroup;
    private ArrayList<View> ingredientViews = new ArrayList<>();
    private LinearLayout categoryCheckBoxHolder;
    private ArrayList<CheckBox> categoryCheckBoxes = new ArrayList<>();
    private Button addFilteredIngredientButton;

    private Spinner sortSpinner;

    private Button submitButton;

    private ArrayList<RecipeModel> recipeModels;

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

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar()
                    .setTitle(R.string.filter_action_bar_title);
        }

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

        sortSpinner = inflatedView.findViewById(R.id.sort_spinner);

        submitButton = inflatedView.findViewById(R.id.filter_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    recipeModels.add(child.getValue(RecipeModel.class));
                }
                filterResults();
                RecipeCardsFragment filteredRecipesFragment = new RecipeCardsFragment();
                Bundle resourceBundle = new Bundle();
                resourceBundle.putInt("titleResource", R.string.filtered_results_action_bar_title);
                filteredRecipesFragment.setArguments(resourceBundle);
                filteredRecipesFragment.setRecipesToDisplay(recipeModels);
                AppCompatActivityHelper.loadFragment(filteredRecipesFragment);
            }

            // TODO: Handle it better, probably.
            @Override
            public void onCancelled(DatabaseError databaseError) {
                String errorMessage =
                        getResources().getString(R.string.new_recipe_on_failure_message_prelude)
                                + databaseError.getDetails();
                Snackbar.make(
                        getActivity().findViewById(R.id.fragment_container),
                        errorMessage,
                        Snackbar.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void filterResults() {
        if (recipeName != null) {
            for (int i = 0; i < recipeModels.size(); ++i) {
                if (!recipeModels.get(i).getName().contains(recipeName)) {
                    recipeModels.remove(i);
                }
            }
        }

        if (author != null) {
            for (int i = 0; i < recipeModels.size(); ++i) {
                if (!recipeModels.get(i).getAuthor().contains(author)) {
                    recipeModels.remove(i);
                }
            }
        }

        if (minutes != null) {
            for (int i = 0; i < recipeModels.size(); ++i) {
                if (!(recipeModels.get(i).getMinutes() > minutes)) {
                    recipeModels.remove(i);
                }
            }
        }

        if (price != null) {
            for (int i = 0; i < recipeModels.size(); ++i) {
                if (!(recipeModels.get(i).getPrice() > price)) {
                    recipeModels.remove(i);
                }
            }
        }

        if (rating != null) {
            for (int i = 0; i < recipeModels.size(); ++i) {
                if (!(recipeModels.get(i).getRating() < rating)) {
                    recipeModels.remove(i);
                }
            }
        }

        if (size != null) {
            for (int i = 0; i < recipeModels.size(); ++i) {
                if (!(recipeModels.get(i).getSize() > size)) {
                    recipeModels.remove(i);
                }
            }
        }

        if (difficulty != null) {
            for (int i = 0; i < recipeModels.size(); ++i) {
                if (!(recipeModels.get(i).getDifficulty().ordinal() > difficulty.ordinal())) {
                    recipeModels.remove(i);
                }
            }
        }

        if (dishCategories.size() > 0) {
            for (int i = 0; i < recipeModels.size(); ++i) {
                boolean dishCategoryFound = false;
                for (DishCategory dishCategory : dishCategories) {
                    if (recipeModels.get(i).getDishCategory() == dishCategory) {
                        dishCategoryFound = true;
                        break;
                    }
                }
                if (!dishCategoryFound) {
                    recipeModels.remove(i);
                }
            }
        }

        // TODO: Is checking ingredients in query correct?
        if (ingredientNames.size() > 0) {
            for (int i = 0; i < recipeModels.size(); ++i) {
                boolean allIngredientsFound = true;
                boolean singleIngredientFound = false;
                recipeIngredientLoop: for (IngredientModel recipeIngredient : recipeModels.get(i).getIngredients()) {
                    singleIngredientFound = false;
                    for (String ingredientName : ingredientNames) {
                        if (recipeIngredient.getName().contains(ingredientName)) {
                            singleIngredientFound = true;
                            break recipeIngredientLoop;
                        }
                    }
                    if (!singleIngredientFound) {
                        allIngredientsFound = false;
                    }
                }
                if (!allIngredientsFound) {
                    recipeModels.remove(i);
                }
            }
        }
    }
}
