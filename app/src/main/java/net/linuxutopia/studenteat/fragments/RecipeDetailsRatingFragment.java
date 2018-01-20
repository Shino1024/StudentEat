package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    // TODO: Check for rating/cooked/favorite statuses first and configure the corresponding views appropriately.
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

        updateViews();

        giveRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ratingBar.getRating() > 0.0) {
//                    updateRating(ratingBar.getRating());
//                } else {
//                    Toast.makeText(getActivity(),
//                            R.string.recipe_details_no_rating_set_message,
//                            Toast.LENGTH_SHORT).show();
//                }
            }
        });

        cookedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cookedReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        //
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // oh
//                    }
//                });
            }
        });

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                favoriteReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        //
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // error
//                    }
//                });
            }
        });

        return inflatedView;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    private void updateViews() {
        DatabaseReference favoriteReference = database.getReference("favorite");
        favoriteReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(recipeId).hasChild(userId)) {
                    favoriteButton.setText(R.string.recipe_details_set_favorite);
                } else {
                    favoriteButton.setText(R.string.recipe_details_set_not_favorite);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // error
            }
        });

        DatabaseReference cookedReference = database.getReference("cooked");
        cookedReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(recipeId).hasChild(userId)) {
                    cookedButton.setText(R.string.recipe_details_set_cooked);
                } else {
                    cookedButton.setText(R.string.recipe_details_set_not_cooked);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // error
            }
        });

        DatabaseReference recipesReference = database.getReference("recipes");
        recipesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float rating = dataSnapshot.child(recipeId).child("rating").getValue(Double.class).floatValue();
                ratingBar.setRating(rating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // error
            }
        });
    }

    private void updateRating(float rating) {
        // TODO: Finish updating the rating.
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            return;
        }
        String userId = user.getUid();
        DatabaseReference ratingsRecipeReference = database.getReference("ratings").child(recipeId);
        ratingsRecipeReference.child(userId).setValue(rating);
        final DatabaseReference recipeReference = database.getReference("recipes").child(recipeId);
        ratingsRecipeReference.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                long sum = 0;
                for (DataSnapshot userRatingSnapshot : dataSnapshot.getChildren()) {
                    sum += (long) userRatingSnapshot.getValue();
                }
                Double calculatedRating = (((double) sum) / (double) count);
                recipeReference.child("rating").setValue(calculatedRating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),
                        "something went wrong with setting up the rating",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleCooked() {
        //
    }

    private void toggleFavorite() {
        //
    }
}
