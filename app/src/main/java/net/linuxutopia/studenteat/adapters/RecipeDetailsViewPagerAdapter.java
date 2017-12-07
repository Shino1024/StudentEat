package net.linuxutopia.studenteat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.linuxutopia.studenteat.models.PagerLayoutProvider;

public class RecipeDetailsViewPagerAdapter extends PagerAdapter {

    private Context context;

    public RecipeDetailsViewPagerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup viewGroup,
                                  int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        PagerLayoutProvider pagerLayoutProvider = PagerLayoutProvider.values()[position];
        ViewGroup layout = (ViewGroup) inflater.inflate(pagerLayoutProvider.getLayoutResourceId(),
                viewGroup,
                false);
        viewGroup.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup viewGroup,
                            int position,
                            @NonNull Object view) {
        viewGroup.removeView((View) view);
    }

    @Override
    public int getCount() {
        return PagerLayoutProvider.values().length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        PagerLayoutProvider pagerLayoutProvider = PagerLayoutProvider.values()[position];
        return context.getString(pagerLayoutProvider.getLayoutResourceId());
    }

}
