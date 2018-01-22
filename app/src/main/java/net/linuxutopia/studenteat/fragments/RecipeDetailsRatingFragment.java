package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.utils.AppCompatActivityHelper;

public class RecipeDetailsRatingFragment extends Fragment {

    private String recipeId;
    private String userId;

    private RatingBar ratingBar;
    private Button giveRatingButton;
    private Button cookedButton;
    private Button favoriteButton;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference cookedReference;
    private DatabaseReference favoriteReference;

    private boolean isCooked;
    private boolean isFavorite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        int layoutResource = getArguments().getInt("layoutResource");
        View inflatedView = inflater.inflate(layoutResource,
                container,
                false);

        cookedReference = database.getReference("cooked");
        favoriteReference = database.getReference("favorite");
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        ratingBar = inflatedView.findViewById(R.id.recipe_details_rating_bar);
        giveRatingButton = inflatedView.findViewById(R.id.recipe_details_give_rating);
        cookedButton = inflatedView.findViewById(R.id.recipe_details_cooked_button);
        favoriteButton = inflatedView.findViewById(R.id.recipe_details_favorite_button);

        prepareViewsValues();

        giveRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRating();
            }
        });

        cookedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleCooked();
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavorite();
            }
        });

        return inflatedView;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    private void prepareFavoritesView() {
        DatabaseReference favoriteReference = database.getReference("favorites");
        favoriteReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(recipeId).hasChild(userId)) {
                    favoriteButton.setText(R.string.recipe_details_set_favorite);
                    isFavorite = true;
                } else {
                    favoriteButton.setText(R.string.recipe_details_set_not_favorite);
                    isFavorite = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppCompatActivityHelper.displayErrorInToast(
                        (AppCompatActivity) getActivity(),
                        databaseError.getDetails()
                );
            }
        });
    }

    private void prepareCookedView() {
        DatabaseReference cookedReference = database.getReference("cooked");
        cookedReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(recipeId).hasChild(userId)) {
                    cookedButton.setText(R.string.recipe_details_set_cooked);
                    isCooked = true;
                } else {
                    cookedButton.setText(R.string.recipe_details_set_not_cooked);
                    isCooked = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppCompatActivityHelper.displayErrorInToast(
                        (AppCompatActivity) getActivity(),
                        databaseError.getDetails()
                );
            }
        });
    }

    private void prepareRatingView() {
        DatabaseReference recipesReference = database.getReference("ratings");
        recipesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(recipeId).hasChild(userId)) {
                    Double ratingDouble = dataSnapshot.child(recipeId).child(userId).getValue(Double.class);
                    if (ratingDouble != null) {
                        ratingBar.setRating(ratingDouble.floatValue());
                    } else {
                        ratingBar.setRating(0.0f);
                    }
                } else {
                    ratingBar.setRating(0.0f);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppCompatActivityHelper.displayErrorInToast(
                        (AppCompatActivity) getActivity(),
                        databaseError.getDetails()
                );
            }
        });
    }

    private void prepareViewsValues() {
        prepareFavoritesView();
        prepareCookedView();
        prepareRatingView();
    }

    private void updateRating() {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            return;
        }
        String userId = user.getUid();

        float rating = ratingBar.getRating();

        DatabaseReference ratingsReference = database.getReference("ratings");
        ratingsReference.child(recipeId).child(userId).setValue(rating);
        final DatabaseReference recipeReference = database.getReference("recipes");
        ratingsReference.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                Double sum = 0.0d;
                for (DataSnapshot userRatingSnapshot : dataSnapshot.getChildren()) {
                    if (userRatingSnapshot.getValue() instanceof Double) {
                        sum += userRatingSnapshot.getValue(Double.class);
                    } else {
                        sum += userRatingSnapshot.getValue(Double.class).doubleValue();
                    }
                }
                Double calculatedRating = (sum / (double) count);
                recipeReference.child(recipeId).child("rating").setValue(calculatedRating);
                Toast.makeText(
                        getActivity(),
                        R.string.recipe_details_new_rating_set,
                        Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppCompatActivityHelper.displayErrorInToast(
                        (AppCompatActivity) getActivity(),
                        databaseError.getDetails()
                );
            }
        });
    }

    private void toggleCooked() {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            return;
        }
        final String userId = user.getUid();

        final DatabaseReference cookedReference = database.getReference("cooked");
        final DatabaseReference recipeReference = database.getReference("recipes");
        recipeReference.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long cookedNumber = dataSnapshot.child("cooked").getValue(Long.class);
                if (isCooked) {
                    isCooked = false;
                    cookedReference.child(recipeId).child(userId).removeValue();
                    recipeReference.child(recipeId).child("cooked").setValue(cookedNumber - 1);
                    cookedButton.setText(R.string.recipe_details_set_not_cooked);
                } else {
                    isCooked = true;
                    cookedReference.child(recipeId).child(userId).setValue(true);
                    recipeReference.child(recipeId).child("cooked").setValue(cookedNumber + 1);
                    cookedButton.setText(R.string.recipe_details_set_cooked);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppCompatActivityHelper.displayErrorInToast(
                        (AppCompatActivity) getActivity(),
                        databaseError.getDetails()
                );
            }
        });
    }

    private void toggleFavorite() {
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            return;
        }
        final String userId = user.getUid();

        final DatabaseReference favoriteReference = database.getReference("favorite");
        final DatabaseReference recipeReference = database.getReference("recipes");
        recipeReference.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long favoriteNumber = dataSnapshot.child("favorite").getValue(Long.class);
                if (isFavorite) {
                    isFavorite = false;
                    favoriteReference.child(recipeId).child(userId).removeValue();
                    recipeReference.child(recipeId).child("favorite").setValue(favoriteNumber - 1);
                    favoriteButton.setText(R.string.recipe_details_set_not_favorite);
                } else {
                    isFavorite = true;
                    favoriteReference.child(recipeId).child(userId).setValue(true);
                    recipeReference.child(recipeId).child("favorite").setValue(favoriteNumber + 1);
                    favoriteButton.setText(R.string.recipe_details_set_favorite);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                AppCompatActivityHelper.displayErrorInToast(
                        (AppCompatActivity) getActivity(),
                        databaseError.getDetails()
                );
            }
        });
    }
}
