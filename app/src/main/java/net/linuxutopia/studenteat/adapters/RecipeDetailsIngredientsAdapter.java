package net.linuxutopia.studenteat.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.models.IngredientModel;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeDetailsIngredientsAdapter
        extends RecyclerView.Adapter<RecipeDetailsIngredientsAdapter.ViewHolder> {

    private ArrayList<IngredientModel> ingredients;

    private DisplayMetrics displayMetrics;

    public RecipeDetailsIngredientsAdapter(ArrayList<IngredientModel> ingredients) {
        this.ingredients = ingredients;
    }

    public void setDisplayMetrics(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.ingredient_view_holder, parent, false
        );

        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        prepareIngredientHolderView(holder.ingredientHolderView, position);
        prepareNoView(holder.noView, position);
        prepareNameView(holder.nameView, position);
        prepareAmountView(holder.amountView, position);
        prepareMeasureTypeView(holder.measureTypeView, position);
        prepareCostView(holder.costView, position);
    }

    private void prepareIngredientHolderView(LinearLayout ingredientViewHolder, int position) {
        ingredientViewHolder.setPadding(0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        displayMetrics.heightPixels * 0.006f,
                        displayMetrics),
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        displayMetrics.heightPixels * 0.006f,
                        displayMetrics));
        if (position % 2 == 0) {
            ingredientViewHolder.setBackgroundColor(
                    ingredientViewHolder.getResources().getColor(R.color.light_black));
        }
    }

    private void prepareNoView(TextView noView, int position) {
        noView.setText(String.format(Locale.getDefault(),
                "%02d",
                position + 1));
    }

    private void prepareNameView(TextView nameView, int position) {
        nameView.setText(ingredients.get(position).getName());
    }

    private void prepareAmountView(TextView amountView, int position) {
        amountView.setText(String.format(Locale.getDefault(),
                "%f",
                ingredients.get(position).getAmount()).replaceAll("\\.?0*$", ""));
    }

    private void prepareMeasureTypeView(TextView measureTypeView, int position) {
        measureTypeView.setText(
                measureTypeView.getResources().getText(
                        ingredients.get(position).getMeasureType().getStringResource()
                )
        );
    }

    private void prepareCostView(TextView costView, int position) {
        costView.setText(String.format(
                Locale.getDefault(),
                "%.2f$",
                ingredients.get(position).getCost()
        ));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ingredientHolderView;
        private TextView noView;
        private TextView nameView;
        private TextView measureTypeView;
        private TextView amountView;
        private TextView costView;

        ViewHolder(View view) {
            super(view);
            ingredientHolderView = view.findViewById(R.id.recipe_details_ingredient_holder);
            noView = view.findViewById(R.id.recipe_details_ingredient_no);
            nameView = view.findViewById(R.id.recipe_details_ingredient_name);
            measureTypeView = view.findViewById(R.id.recipe_details_ingredient_measure);
            amountView = view.findViewById(R.id.recipe_details_ingredient_amount);
            costView = view.findViewById(R.id.recipe_details_ingredient_cost);
        }

    }

}
