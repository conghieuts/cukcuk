package vn.com.misa.hieudc.cukcuklite.screen.addfooditemscreen;

import vn.com.misa.hieudc.cukcuklite.model.FoodItem;

public interface IAddFoodItemsPresenter {
    void saveFoodItem(FoodItem foodItem);

    void updateFoodItem(FoodItem foodItem);

    void deleteFoodItem(FoodItem foodItem);

    void createNewFoodItem();
}
