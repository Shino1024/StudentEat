package net.linuxutopia.studenteat.models;

import net.linuxutopia.studenteat.R;

public enum PagerLayoutProvider {

    PAGE_DESCRIPTION(R.string.recipe_details_description_title,
            R.layout.recipe_details_description_page),
    PAGE_INGREDIENTS(R.string.recipe_details_ingredients_title,
            R.layout.recipe_details_ingredients_page),
    PAGE_STEPS(R.string.recipe_details_steps_title,
            R.layout.recipe_details_steps_page),
    PAGE_COMMENTS(R.string.recipe_details_comments_title,
            R.layout.recipe_details_comments_page);

    private int titleResourceId;
    private int layoutResourceId;

    PagerLayoutProvider(int titleResourceId, int layoutResourceId) {
        this.titleResourceId = titleResourceId;
        this.layoutResourceId = layoutResourceId;
    }

    public int getTitleResourceId() {
        return titleResourceId;
    }

    public void setTitleResourceId(int titleResourceId) {
        this.titleResourceId = titleResourceId;
    }

    public int getLayoutResourceId() {
        return layoutResourceId;
    }

    public void setLayoutResourceId(int layoutResourceId) {
        this.layoutResourceId = layoutResourceId;
    }
}
