package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.adapters.RecipeDetailsViewPagerAdapter;

public class RecipeDetailsFragment extends Fragment {

    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
    RecipeDetailsViewPagerAdapter viewPagerAdapter;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View inflatedView = layoutInflater.inflate(R.layout.recipe_details,
                viewGroup,
                false);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        }
        collapsingToolbarLayout = inflatedView.findViewById(R.id.recipe_details_collapsing_toolbar_layout);

        toolbar = inflatedView.findViewById(R.id.recipe_details_toolbar);
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        viewPager = inflatedView.findViewById(R.id.recipe_details_view_pager);
        viewPagerAdapter = new RecipeDetailsViewPagerAdapter(
                ((AppCompatActivity) getActivity()).getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = inflatedView.findViewById(R.id.recipe_details_tabs);
        tabLayout.setupWithViewPager(viewPager);

        appBarLayout = inflatedView.findViewById(R.id.recipe_details_app_bar_layout);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //ViewGroup.LayoutParams layoutParams = appBarLayout.getLayoutParams();
        //layoutParams.height = displayMetrics.heightPixels / 3;
        //appBarLayout.setLayoutParams(layoutParams);
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");


        return inflatedView;
    }
}
