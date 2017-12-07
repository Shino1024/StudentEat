package net.linuxutopia.studenteat.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.adapters.RecipeDetailsViewPagerAdapter;

public class RecipeDetailsFragment extends Fragment {

    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        View inflatedView = layoutInflater.inflate(R.layout.recipe_details_layout,
                viewGroup,
                false);

        viewPager = inflatedView.findViewById(R.id.recipe_details_pager);
        viewPager.setAdapter(new RecipeDetailsViewPagerAdapter(getActivity()));

        return inflatedView;
    }

}
