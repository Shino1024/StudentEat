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
import android.widget.ImageView;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.adapters.RecipeDetailsViewPagerAdapter;

public class RecipeDetailsFragment extends Fragment {

    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
    RecipeDetailsViewPagerAdapter viewPagerAdapter;
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             @Nullable ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View inflatedView = layoutInflater.inflate(R.layout.recipe_details,
                viewGroup,
                false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        collapsingToolbarLayout =
                inflatedView.findViewById(R.id.recipe_details_collapsing_toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(
                R.color.expanded_title_color
        ));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(
                R.color.full_black
        ));

        ImageView imageView = inflatedView.findViewById(R.id.recipe_details_photo);
        imageView.requestLayout();
        imageView.setImageResource(R.drawable.lain);
        imageView.getLayoutParams().height = displayMetrics.heightPixels / 2;

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        toolbar = inflatedView.findViewById(R.id.recipe_details_toolbar);

        viewPager = inflatedView.findViewById(R.id.recipe_details_view_pager);
        viewPagerAdapter = new RecipeDetailsViewPagerAdapter(getChildFragmentManager());
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
        collapsingToolbarLayout.setTitle("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        return inflatedView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }
}
