package vn.com.misa.hieudc.cukcuklite.screen.selectfooditemscreen;

import vn.com.misa.hieudc.cukcuklite.model.FoodItem;

interface ISelectFoodPresenter {
    void getAllFoodItems();

    void saveOderItem();

    void addFoodItem(FoodItem foodItem);

    void toggleFoodItem(FoodItem foodItem);

    void removeFoodItem(FoodItem foodItem);

    void addFoodItemWithAmount(FoodItem currentFoodItem, int amount);

    void setNumberOfTable(int parseInt);

    void setNumberOfPeople(int parseInt);

    void updateOrderItem();

    void startCheckout();
}
