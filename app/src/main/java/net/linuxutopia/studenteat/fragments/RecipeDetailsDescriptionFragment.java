package net.linuxutopia.studenteat.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.linuxutopia.studenteat.R;

import java.util.ArrayList;

public class RecipeDetailsDescriptionFragment extends Fragment {

    View inflatedView;

    TextView authorView;
    TextView dishDescriptionView;

    CardView statisticsCardView;

    ArrayList<LinearLayout> views = new ArrayList<>();
    ArrayList<ImageView> imageViews = new ArrayList<>();
    ArrayList<TextView> textViews = new ArrayList<>();

    DisplayMetrics displayMetrics;

    Typeface typeface;

    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup viewGroup,
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

        prepareArrayListViews();
        for (LinearLayout linearLayout : views) {
            prepareStatisticsView(linearLayout);
        }
        for (ImageView imageView: imageViews) {
            prepareStatisticsImageView(imageView);
        }
        for (TextView textView : textViews) {
            prepareStatisticsTextView(textView);
        }

        return inflatedView;
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
        authorView.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                displayMetrics.heightPixels * 0.015f);
    }

    private void prepareDishDescriptionView() {
        dishDescriptionView = inflatedView.findViewById(R.id.recipe_details_dish_description);
        dishDescriptionView.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                displayMetrics.heightPixels * 0.010f);
    }

    private void prepareArrayListViews() {
        views.add((LinearLayout) inflatedView.findViewById(R.id.recipe_details_category));
        views.add((LinearLayout) inflatedView.findViewById(R.id.recipe_details_time));
        views.add((LinearLayout) inflatedView.findViewById(R.id.recipe_details_difficulty));
        views.add((LinearLayout) inflatedView.findViewById(R.id.recipe_details_rating));
        views.add((LinearLayout) inflatedView.findViewById(R.id.recipe_details_price));
        views.add((LinearLayout) inflatedView.findViewById(R.id.recipe_details_size));

        imageViews.add((ImageView) inflatedView.findViewById(R.id.recipe_details_category_image));
        imageViews.add((ImageView) inflatedView.findViewById(R.id.recipe_details_time_image));
        imageViews.add((ImageView) inflatedView.findViewById(R.id.recipe_details_difficulty_image));
        imageViews.add((ImageView) inflatedView.findViewById(R.id.recipe_details_rating_image));
        imageViews.add((ImageView) inflatedView.findViewById(R.id.recipe_details_price_image));
        imageViews.add((ImageView) inflatedView.findViewById(R.id.recipe_details_size_image));

        textViews.add((TextView) inflatedView.findViewById(R.id.recipe_details_category_text));
        textViews.add((TextView) inflatedView.findViewById(R.id.recipe_details_time_text));
        textViews.add((TextView) inflatedView.findViewById(R.id.recipe_details_difficulty_text));
        textViews.add((TextView) inflatedView.findViewById(R.id.recipe_details_rating_text));
        textViews.add((TextView) inflatedView.findViewById(R.id.recipe_details_price_text));
        textViews.add((TextView) inflatedView.findViewById(R.id.recipe_details_size_text));
    }

    private void prepareStatisticsView(LinearLayout linearLayout) {
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

    private void prepareStatisticsTextView(TextView textView) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                displayMetrics.heightPixels * 0.01f);
        textView.setTypeface(typeface);
    }

}
