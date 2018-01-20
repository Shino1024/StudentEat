package net.linuxutopia.studenteat.fragments;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.GridLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.models.RecipeDetailsModel;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeDetailsDescriptionFragment extends Fragment {

    private View inflatedView;

    private TextView authorView;
    private TextView dishDescriptionView;

    private CardView statisticsCardView;

    private Button removeButton;

    private ArrayList<LinearLayout> containerViews = new ArrayList<>();
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ArrayList<TextView> textViews = new ArrayList<>();

    private RecipeDetailsModel recipeDetailsModel;

    private DisplayMetrics displayMetrics;

    private Typeface typeface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             @Nullable ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        int layoutResource = getArguments().getInt("layoutResource");
        inflatedView = layoutInflater.inflate(layoutResource,
                viewGroup,
                false);

        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        typeface = Typeface.createFromAsset(getActivity().getAssets(),
                "roboto_light.ttf");

        prepareStatisticsCardView();

        prepareAuthorView();

        prepareDishDescriptionView();

        prepareRemoveRecipeView();

        prepareArrayListViews();

        prepareStatisticsView();

        return inflatedView;
    }

    private void prepareStatisticsView() {
        ArrayList<String> statisticsStrings = new ArrayList<>();
        statisticsStrings.add(getString(recipeDetailsModel.getDishCategory().getStringResource()));
        statisticsStrings.add(String.format(
                Locale.getDefault(),
                "%02d:%02d",
                recipeDetailsModel.getMinutes() / 60,
                recipeDetailsModel.getMinutes() % 60
        ));
        statisticsStrings.add(getString(recipeDetailsModel.getDifficulty().getStringResource()));
        statisticsStrings.add(String.format(
                Locale.getDefault(),
                "%1.2f",
                recipeDetailsModel.getRating()
        ));
        statisticsStrings.add(String.format(
                Locale.getDefault(),
                "%.2f$",
                recipeDetailsModel.getPrice()
        ));
        statisticsStrings.add(String.format(
                Locale.getDefault(),
                "%d",
                recipeDetailsModel.getSize()
        ));
        statisticsStrings.add(String.format(
                Locale.getDefault(),
                "%d",
                recipeDetailsModel.getFavorite()
        ));
        statisticsStrings.add(String.format(
                Locale.getDefault(),
                "%d",
                recipeDetailsModel.getCooked()
        ));

        for (LinearLayout linearLayout : containerViews) {
            prepareStatisticsContainerView(linearLayout);
        }
        for (ImageView imageView : imageViews) {
            prepareStatisticsImageView(imageView);
        }
        for (int i = 0; i < statisticsStrings.size(); ++i) {
            prepareStatisticsTextView(textViews.get(i), statisticsStrings.get(i));
        }
    }

    private void prepareStatisticsCardView() {
        statisticsCardView = inflatedView.findViewById(R.id.recipe_details_statistics_card_view);
        statisticsCardView.setRadius((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.heightPixels * 0.004f,
                displayMetrics
        ));
        statisticsCardView.setCardElevation((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.heightPixels * 0.002f,
                displayMetrics
        ));
        LinearLayout.LayoutParams statisticsLayoutParams =
                (LinearLayout.LayoutParams) statisticsCardView.getLayoutParams();
        statisticsLayoutParams.setMargins((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.widthPixels * 0.01f,
                displayMetrics
        ), (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.heightPixels * 0.005f,
                displayMetrics
        ), (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.widthPixels * 0.01f,
                displayMetrics
        ), (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.heightPixels * 0.005f,
                displayMetrics
        ));
        statisticsCardView.setLayoutParams(statisticsLayoutParams);
    }

    private void prepareAuthorView() {
        authorView = inflatedView.findViewById(R.id.recipe_details_author);
        authorView.setText(recipeDetailsModel.getAuthor());
    }

    private void prepareDishDescriptionView() {
        dishDescriptionView = inflatedView.findViewById(R.id.recipe_details_dish_description);
        dishDescriptionView.setText(recipeDetailsModel.getDescription());
    }

    private void prepareArrayListViews() {
        int[] containerResources = {
                R.id.recipe_details_category,
                R.id.recipe_details_time,
                R.id.recipe_details_difficulty,
                R.id.recipe_details_rating,
                R.id.recipe_details_price,
                R.id.recipe_details_size,
                R.id.recipe_details_favorite,
                R.id.recipe_details_cooked
        };
        int[] imageResources = {
                R.id.recipe_details_category_image,
                R.id.recipe_details_time_image,
                R.id.recipe_details_difficulty_image,
                R.id.recipe_details_rating_image,
                R.id.recipe_details_price_image,
                R.id.recipe_details_size_image,
                R.id.recipe_details_favorite_image,
                R.id.recipe_details_cooked_image
        };
        int[] textResources = {
                R.id.recipe_details_category_text,
                R.id.recipe_details_time_text,
                R.id.recipe_details_difficulty_text,
                R.id.recipe_details_rating_text,
                R.id.recipe_details_price_text,
                R.id.recipe_details_size_text,
                R.id.recipe_details_favorite_text,
                R.id.recipe_details_cooked_text
        };

        for (int containerResource : containerResources) {
            containerViews.add((LinearLayout) inflatedView.findViewById(containerResource));
        }
        for (int imageResource : imageResources) {
            imageViews.add((ImageView) inflatedView.findViewById(imageResource));
        }
        for (int textResource : textResources) {
            textViews.add((TextView) inflatedView.findViewById(textResource));
        }
    }

    private void prepareStatisticsContainerView(LinearLayout linearLayout) {
        GridLayout.LayoutParams layoutParams =
                (GridLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.setMargins((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.widthPixels * 0.01f,
                displayMetrics
        ), (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.heightPixels * 0.005f,
                displayMetrics
        ), (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.widthPixels * 0.01f,
                displayMetrics
        ), (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.heightPixels * 0.005f,
                displayMetrics
        ));
        linearLayout.setLayoutParams(layoutParams);
    }

    private void prepareStatisticsImageView(ImageView imageView) {
        imageView.requestLayout();
        imageView.getLayoutParams().height = (int) (displayMetrics.heightPixels * 0.05f);
        imageView.getLayoutParams().width = (int) (displayMetrics.heightPixels * 0.05f);
    }

    private void prepareStatisticsTextView(TextView textView, String value) {
        textView.setTypeface(typeface);
        textView.setText(value);
    }

    private void prepareRemoveRecipeView() {
        removeButton = inflatedView.findViewById(R.id.recipe_details_remove_recipe_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle(R.string.remove_recipe_question);
                alertDialog.setPositiveButton(R.string.remove_recipe_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // TODO: Remove the recipe and pop back the fragment stack.
                                // TODO: Should child fragment manager be used?
                                getActivity().getFragmentManager().popBackStack();
                            }
                }).setNegativeButton(R.string.remove_recipe_cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    public void setRecipeDetailsModel(RecipeDetailsModel recipeDetailsModel) {
        this.recipeDetailsModel = recipeDetailsModel;
    }

}
