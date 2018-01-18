package net.linuxutopia.studenteat.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;

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

import java.util.ArrayList;

public class RecipeQueryBuilder {

    private DatabaseReference recipeReference;

    private String recipeName;
    private String fullAuthorName;
    private Integer minutes;
    private Double price;
    private Double rating;
    private Integer size;
    private Difficulty difficulty;
    private ArrayList<DishCategory> categories;
    private ArrayList<String> ingredientNames;
    private SortType sortType;

    private ArrayList<RecipeModel> recipeModels;

    public RecipeQueryBuilder() {
        recipeReference = FirebaseDatabase.getInstance().getReference("/recipes");
    }

    public RecipeQueryBuilder setRecipeName(String recipeName) {
        this.recipeName = recipeName;
        return this;
    }

    public RecipeQueryBuilder setFullAuthorName(String fullAuthorName) {
        this.fullAuthorName = fullAuthorName;
        return this;
    }

    public RecipeQueryBuilder setMinutes(Integer minutes) {
        this.minutes = minutes;
        return this;
    }

    public RecipeQueryBuilder setPrice(Double price) {
        this.price = price;
        return this;
    }

    public RecipeQueryBuilder setRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public RecipeQueryBuilder setSize(Integer size) {
        this.size = size;
        return this;
    }

    public RecipeQueryBuilder setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public RecipeQueryBuilder setCategories(ArrayList<DishCategory> categories) {
        this.categories = categories;
        return this;
    }

    public RecipeQueryBuilder setIngredients(ArrayList<String> ingredientNames) {
        this.ingredientNames = ingredientNames;
        return this;
    }

    public RecipeQueryBuilder setSortType(SortType sortType) {
        this.sortType = sortType;
        return this;
    }

    public QueryResult executeQueryWithActivity(final Activity activity) {
        recipeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    recipeModels.add(child.getValue(RecipeModel.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String errorMessage =
                        activity.getResources().getString(R.string.new_recipe_on_failure_message_prelude)
                        + databaseError.getDetails();
                Snackbar.make(
                        activity.findViewById(R.id.fragment_container),
                        errorMessage,
                        Snackbar.LENGTH_SHORT
                ).show();
            }
        });

        if (recipeName != null) {
            for (int i = 0; i < recipeModels.size(); ++i) {
                if (!recipeModels.get(i).getName().contains(recipeName)) {
                    recipeModels.remove(i);
                }
            }
        }

        if (fullAuthorName != null) {
            for (int i = 0; i < recipeModels.size(); ++i) {
                if (!recipeModels.get(i).getAuthor().contains(fullAuthorName)) {
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

        if (categories.size() > 0) {
            for (int i = 0; i < recipeModels.size(); ++i) {
                boolean dishCategoryFound = false;
                for (DishCategory dishCategory : categories) {
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

        if (sortType != SortType.NOTHING) {
            switch (sortType) {
                case TIME:
                    break;
                case PRICE:
                    break;
                case RATING:
                    break;
                case SIZE:
                    break;
                default:
                    break;

            }
        }

        return QueryResult.OK;
    }

    public ArrayList<RecipeModel> returnResultingRecipes() {
        return recipeModels;
    }

    public enum QueryResult {
        ERROR,
        OK
    }

}
