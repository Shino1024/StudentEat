package net.linuxutopia.studenteat.models;

import net.linuxutopia.studenteat.R;

public enum DishCategory {

    SALAD(R.string.dish_category_salad),
    DRINK(R.string.dish_category_drink),
    SANDWICH(R.string.dish_category_sandwich),
    SOUP(R.string.dish_category_soup),
    DESSERT(R.string.dish_category_dessert),
    PIZZA(R.string.dish_category_pizza),
    PASTA(R.string.dish_category_pasta),
    FAST_FOOD(R.string.dish_category_fast_food),
    MAIN_COURSE(R.string.dish_category_main_course),
    SNACK(R.string.dish_category_snack);

    private final int dishCategoryResource;

    DishCategory(int dishCategoryResource) {
        this.dishCategoryResource = dishCategoryResource;
    }

    public int getStringResource() {
        return dishCategoryResource;
    }

}
