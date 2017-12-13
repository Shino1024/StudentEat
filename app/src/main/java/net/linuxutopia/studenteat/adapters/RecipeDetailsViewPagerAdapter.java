package net.linuxutopia.studenteat.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.linuxutopia.studenteat.models.RecipeDetailsFragmentFactory;

public class RecipeDetailsViewPagerAdapter extends FragmentPagerAdapter {

    RecipeDetailsFragmentFactory factory = new RecipeDetailsFragmentFactory();

    public RecipeDetailsViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return factory.getFragment(position);
    }

    @Override
    public int getCount() {
        return factory.getFragmentCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return factory.getTitleResource(position);
    }
}
