package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.adapters.RecipeDetailsViewPagerAdapter;
import net.linuxutopia.studenteat.models.RecipeDetailsModel;
import net.linuxutopia.studenteat.utils.RecipeDetailsFragmentFactory;

public class RecipeDetailsFragment extends Fragment {

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecipeDetailsViewPagerAdapter viewPagerAdapter;
    private Toolbar toolbar;

    private RecipeDetailsModel recipeDetailsModel;

    private DisplayMetrics displayMetrics;

    private ImageView recipePhotoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             @Nullable ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View inflatedView = layoutInflater.inflate(R.layout.recipe_details,
                viewGroup,
                false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        collapsingToolbarLayout =
                inflatedView.findViewById(R.id.recipe_details_collapsing_toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(
                R.color.expanded_title_color
        ));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(
                R.color.full_black
        ));

        recipePhotoView = inflatedView.findViewById(R.id.recipe_details_photo);
        recipePhotoView.requestLayout();
        recipePhotoView.getLayoutParams().height = displayMetrics.heightPixels / 2;
        Glide
                .with(this)
                .load(recipeDetailsModel.getDownloadLink())
                .into(recipePhotoView);

//        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
//            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//        }
        toolbar = inflatedView.findViewById(R.id.recipe_details_toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        // TODO: Try to implement the back navigation button in the collapsing toolbar layout.

        viewPager = inflatedView.findViewById(R.id.recipe_details_view_pager);
        viewPagerAdapter = new RecipeDetailsViewPagerAdapter(getChildFragmentManager());
        RecipeDetailsFragmentFactory factory = new RecipeDetailsFragmentFactory();
        factory.setRecipeDetailsModel(recipeDetailsModel);
        viewPagerAdapter.setRecipeDetailsFactory(factory);
        viewPager.setAdapter(viewPagerAdapter);

        CoordinatorLayout.LayoutParams viewPagerLayoutParams =
                (CoordinatorLayout.LayoutParams) viewPager.getLayoutParams();
        int calculatedMarginSideSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.widthPixels * 0.004f,
                displayMetrics);
        int calculatedMarginTopSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.heightPixels * 0.004f,
                displayMetrics);
        viewPagerLayoutParams.setMargins(
                calculatedMarginSideSize,
                calculatedMarginTopSize,
                calculatedMarginSideSize,
                calculatedMarginTopSize
        );
        viewPager.setLayoutParams(viewPagerLayoutParams);

        tabLayout = inflatedView.findViewById(R.id.recipe_details_tabs);
        tabLayout.setupWithViewPager(viewPager);

        appBarLayout = inflatedView.findViewById(R.id.recipe_details_app_bar_layout);

        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(recipeDetailsModel.getName());

        return inflatedView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    public void setRecipeDetailsModel(RecipeDetailsModel recipeDetailsModel) {
        this.recipeDetailsModel = recipeDetailsModel;
    }

}
