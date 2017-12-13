package net.linuxutopia.studenteat.adapters;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.RecentCardModel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.ViewHolder> {

    private ArrayList<RecentCardModel> dataSet;
    private DisplayMetrics displayMetrics;
    private int cardHeight;
    private Map<Difficulty, Integer> difficultyMap;

    public RecentsAdapter(ArrayList<RecentCardModel> dataSet) {
        this.dataSet = dataSet;
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
    public RecentsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View inflatedLayout = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recents_card_layout, viewGroup, false
        );
        return new ViewHolder(inflatedLayout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        prepareRecentsCardView(holder.recentsCardView);
        prepareCardBackgroundImageView(holder.cardBackgroundImageView);
        prepareTitleView(holder.titleView, position);
        prepareAuthorView(holder.authorView, position);
        prepareTimeView(holder.timeView, position);
        prepareRatingView(holder.ratingView, position);
        prepareDifficultyView(holder.difficultyView, position);
    }

    private void prepareTitleView(TextView titleView, int position) {
        titleView.setText(dataSet.get(position).getTitle());
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int) (cardHeight * 0.04f));
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
        authorView.setText(dataSet.get(position).getAuthor());
        authorView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int) (cardHeight * 0.03f));
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
                dataSet.get(position).getMinutes() / 60,
                dataSet.get(position).getMinutes() % 60);
        timeView.setText(formattedTime);
        timeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int) (cardHeight * 0.03f));
    }

    private void prepareRatingView(TextView ratingView, int position) {
        ratingView.setText(String.format(Locale.getDefault(),
                "Rat.\n%.2f",
                dataSet.get(position).getRating()));
        ratingView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int) (cardHeight * 0.03f));
    }

    private void prepareDifficultyView(FrameLayout difficultyView, int position) {
        difficultyView.setForeground(
                new ColorDrawable(
                        difficultyMap.get(
                                dataSet.get(position).getDifficulty())));
    }

    private void prepareRecentsCardView(CardView recentsCardView) {
        recentsCardView.setRadius(cardHeight * 0.05f);
        recentsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fill out the event callback.
            }
        });
    }

    private void prepareCardBackgroundImageView(ImageView cardBackgroundImageView){
        cardBackgroundImageView.requestLayout();
        cardBackgroundImageView.getLayoutParams().height = cardHeight;
        cardBackgroundImageView.setImageResource(R.drawable.splash_logo);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titleView;
        private TextView authorView;
        private TextView timeView;
        private TextView ratingView;
        private FrameLayout difficultyView;

        private CardView recentsCardView;

        private ImageView cardBackgroundImageView;

        ViewHolder(View v) {
            super(v);
            titleView = v.findViewById(R.id.recents_card_title);
            authorView = v.findViewById(R.id.recents_card_author);
            timeView = v.findViewById(R.id.recents_card_time);
            ratingView = v.findViewById(R.id.recents_card_rating);
            difficultyView = v.findViewById(R.id.recents_card_difficulty);
            recentsCardView = v.findViewById(R.id.recents_card);
            cardBackgroundImageView = v.findViewById(R.id.card_background_image);
        }

    }

}
