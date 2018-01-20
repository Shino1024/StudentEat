package net.linuxutopia.studenteat.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import net.linuxutopia.studenteat.models.RecipeDetailsModel;
import net.linuxutopia.studenteat.utils.RecipeDetailsFragmentFactory;

public class RecipeDetailsViewPagerAdapter extends FragmentStatePagerAdapter {

    private RecipeDetailsFragmentFactory factory;

    public RecipeDetailsViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setRecipeDetailsFactory(RecipeDetailsFragmentFactory factory) {
        this.factory = factory;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment returnFragment = factory.getFragment(position);
        Bundle resourceBundle = new Bundle();
        resourceBundle.putInt("layoutResource", factory.getLayoutResource(position));
        returnFragment.setArguments(resourceBundle);
        return returnFragment;
    }

    @Override
    public int getCount() {
        return factory.FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return factory.getTitleResource(position);
    }

}
