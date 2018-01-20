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

    private RatingBar ratingBar;
    private Button giveRatingButton;
    private Button cookedButton;
    private Button favoriteButton;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        int layoutResource = getArguments().getInt("layoutResource");
        View inflatedView = inflater.inflate(layoutResource,
                container,
                false);

        ratingBar = inflatedView.findViewById(R.id.recipe_details_rating_bar);
        giveRatingButton = inflatedView.findViewById(R.id.recipe_details_give_rating);
        cookedButton = inflatedView.findViewById(R.id.recipe_details_cooked_button);
        favoriteButton = inflatedView.findViewById(R.id.recipe_details_favorite_button);

        giveRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingBar.getRating() > 0.0) {
                    updateRating(ratingBar.getRating());
                } else {
                    Toast.makeText(getActivity(),
                            R.string.recipe_details_no_rating_set_message,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return inflatedView;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    private void updateRating(float rating) {
        // TODO: Finish updating the rating.
        FirebaseUser user = auth.getCurrentUser();
        String userId = user.getUid();
        DatabaseReference ratingsReference = database.getReference("ratings").child(recipeId);
        ratingsReference.child(userId).setValue(rating);
        DatabaseReference recipeReference = database.getReference("recipes").child(recipeId);
        ratingsReference.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(),
                        "",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
