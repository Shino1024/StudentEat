package net.linuxutopia.studenteat.adapters;

import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.fragments.RecipeDetailsFragment;
import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.RecipeDetailsModel;
import net.linuxutopia.studenteat.utils.AppCompatActivityHelper;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.ViewHolder> {

    private ArrayList<RecipeDetailsModel> recipes;
    private DisplayMetrics displayMetrics;
    private int cardHeight;
    private Map<Difficulty, Integer> difficultyMap;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public RecipeCardAdapter(ArrayList<RecipeDetailsModel> recipes) {
        this.recipes = recipes;
    }

    public void setCardHeight(int cardHeight) {
        this.cardHeight = cardHeight;
    }

    public void setDifficultyMap(Map<Difficulty, Integer> difficultyMap) {
        this.difficultyMap = difficultyMap;
    }

    public void setDisplayMetrics(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
    }

    @Override
    public RecipeCardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View inflatedLayout = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recipe_card_layout, viewGroup, false
        );
        return new ViewHolder(inflatedLayout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        prepareClickArea(holder.clickArea, position);
        prepareRecentsCardView(holder.recipeCardView);
        prepareCardBackgroundImageView(holder.cardBackgroundImageView, position);
        prepareTitleView(holder.titleView, position);
        prepareAuthorView(holder.authorView, position);
        prepareTimeView(holder.timeView, position);
        prepareRatingView(holder.ratingView, position);
        prepareDifficultyView(holder.difficultyView, position);
    }

    private void prepareClickArea(final LinearLayout clickArea, final int position) {
        clickArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DatabaseReference recipeReference
                        = database.getReference("recipes");
                recipeReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(recipes.get(position).getId())) {
                            FragmentManager fragmentManager =
                                    ((AppCompatActivity) view.getContext()).getFragmentManager();
                            RecipeDetailsFragment fragment = new RecipeDetailsFragment();
                            fragment.setRecipeDetailsModel(recipes.get(position));
                            AppCompatActivityHelper.loadFragment(fragmentManager, fragment);
                        } else {
                            AppCompatActivityHelper.displayErrorInToast(
                                    ((AppCompatActivity) view.getContext()),
                                    view.getResources().getString(R.string.recipe_not_found_error)
                            );
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        AppCompatActivityHelper.displayErrorInToast(
                                (AppCompatActivity) view.getContext(),
                                databaseError.getDetails()
                        );
                    }
                });
                clickArea.setOnClickListener(null);
            }
        });
    }

    private void prepareTitleView(TextView titleView, int position) {
        titleView.setText(recipes.get(position).getName());
        titleView.setPadding(
                (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        (int) (cardHeight * 0.01f),
                        displayMetrics)),
                0,
                (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        (int) (cardHeight * 0.01f),
                        displayMetrics)),
                0);
    }

    private void prepareAuthorView(TextView authorView, int position) {
        authorView.setText(recipes.get(position).getAuthor());
        authorView.setPadding(
                (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        (int) (cardHeight * 0.01f),
                        displayMetrics)),
                0,
                (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        (int) (cardHeight * 0.01f),
                        displayMetrics)),
                0);
    }

    private void prepareTimeView(TextView timeView, int position) {
        String formattedTime = String.format(Locale.getDefault(),
                "Time\n%2d:%02d",
                recipes.get(position).getMinutes() / 60,
                recipes.get(position).getMinutes() % 60);
        timeView.setText(formattedTime);
    }

    private void prepareRatingView(TextView ratingView, int position) {
        ratingView.setText(String.format(Locale.getDefault(),
                "Rat.\n%.2f",
                recipes.get(position).getRating()));
    }

    private void prepareDifficultyView(FrameLayout difficultyView, int position) {
        difficultyView.setForeground(
                new ColorDrawable(
                        difficultyMap.get(
                                recipes.get(position).getDifficulty())));
    }

    private void prepareRecentsCardView(CardView recipeCardView) {
        recipeCardView.setRadius(cardHeight * 0.05f);
    }

    private void prepareCardBackgroundImageView(ImageView cardBackgroundImageView, int position) {
        cardBackgroundImageView.requestLayout();
        cardBackgroundImageView.getLayoutParams().height = cardHeight;

        ViewPropertyTransition.Animator animationObject = new ViewPropertyTransition.Animator() {
            @Override
            public void animate(View view) {
                view.setAlpha(0.0f);

                ObjectAnimator fadeInAnimation = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.1f);
                fadeInAnimation.setDuration(500);
                fadeInAnimation.start();
            }
        };

        Glide
                .with(cardBackgroundImageView.getContext())
                .load(recipes.get(position).getDownloadLink())
                .transition(GenericTransitionOptions.with(animationObject))
                .into(cardBackgroundImageView);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleView;
        private TextView authorView;
        private TextView timeView;
        private TextView ratingView;
        private FrameLayout difficultyView;

        private CardView recipeCardView;

        private ImageView cardBackgroundImageView;

        private LinearLayout clickArea;

        ViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.recipe_card_title);
            authorView = view.findViewById(R.id.recipe_card_author);
            timeView = view.findViewById(R.id.recipe_card_time);
            ratingView = view.findViewById(R.id.recipe_card_rating);
            difficultyView = view.findViewById(R.id.recipe_card_difficulty);
            recipeCardView = view.findViewById(R.id.recipe_card);
            cardBackgroundImageView = view.findViewById(R.id.recipe_card_background_image);
            clickArea = view.findViewById(R.id.recipe_card_click_area);
        }

    }

}
