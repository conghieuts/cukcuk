package vn.com.misa.hieudc.cukcuklite.screen.selectfooditemscreen;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.model.FoodItem;
import vn.com.misa.hieudc.cukcuklite.model.Order;

interface ISelectFoodView {
    void onGetAllFoodItemsDone(ArrayList<FoodItem> allFoodItems);

    void onItemUpdate(Order order);

    void onNumberOfTableUpdate(Order order);

    void onNumberOfPeopleUpdate(Order order);

    void onDataError();

    void onUpdateSuccess(String message);
}
