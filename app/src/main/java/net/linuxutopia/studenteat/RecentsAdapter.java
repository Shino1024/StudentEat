package net.linuxutopia.studenteat;

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

import com.bumptech.glide.Glide;

import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.RecentCardModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.ViewHolder> {

    private ArrayList<RecentCardModel> dataSet;
    private DisplayMetrics displayMetrics;
    private int cardHeight;
    private HashMap<Difficulty, Integer> difficultyMap;

    public RecentsAdapter(ArrayList<RecentCardModel> dataSet) {
        this.dataSet = dataSet;
    }

    public void setCardHeight(int cardHeight) {
        this.cardHeight = cardHeight;
    }

    public void setDifficultyMap(HashMap<Difficulty, Integer> difficultyMap) {
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
        holder.recentsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.titleView.setText(dataSet.get(position).getTitle());
        holder.titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int)(cardHeight * 0.04f));
        holder.titleView.setPadding(
                (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        (int)(cardHeight * 0.01f),
                        displayMetrics)),
                0,
                (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        (int)(cardHeight * 0.01f),
                        displayMetrics)),
        0);
        holder.authorView.setText(dataSet.get(position).getAuthor());
        holder.authorView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int)(cardHeight * 0.03f));
        holder.authorView.setPadding(
                (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        (int)(cardHeight * 0.01f),
                        displayMetrics)),
                0,
                (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        (int)(cardHeight * 0.01f),
                        displayMetrics)),
                0);
        String formattedTime = String.format(Locale.getDefault(),
                "Time\n%2d:%02d",
                dataSet.get(position).getMinutes() / 60,
                dataSet.get(position).getMinutes() % 60);
        holder.timeView.setText(formattedTime);
        holder.timeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int)(cardHeight * 0.03f));
        holder.ratingView.setText(String.format(Locale.getDefault(),
                "Rat.\n%.2f",
                dataSet.get(position).getRating()));
        holder.ratingView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int)(cardHeight * 0.03f));
        holder.difficultyView.setForeground(new ColorDrawable(difficultyMap.get(dataSet.get(position).getDifficulty())));

        holder.recentsCardView.setRadius(cardHeight * 0.05f);

        holder.backgroundCardImage.requestLayout();
        holder.backgroundCardImage.getLayoutParams().height = cardHeight;
        holder.backgroundCardImage.setImageResource(R.drawable.splash_logo);
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

        private ImageView backgroundCardImage;

        ViewHolder(View v) {
            super(v);
            titleView = v.findViewById(R.id.recents_card_title);
            authorView = v.findViewById(R.id.recents_card_author);
            timeView = v.findViewById(R.id.recents_card_time);
            ratingView = v.findViewById(R.id.recents_card_rating);
            difficultyView = v.findViewById(R.id.recents_card_difficulty);
            recentsCardView = v.findViewById(R.id.recents_card);
            backgroundCardImage = v.findViewById(R.id.background_card_image);
        }

    }

}
