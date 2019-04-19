package vn.com.misa.hieudc.cukcuklite.screen.addfooditemscreen;

import vn.com.misa.hieudc.cukcuklite.model.FoodItem;

public interface IAddFoodItemView {
    void onAddSuccess();

    void onAddFail();

    void onUpdateSuccess(FoodItem foodItem);

    void onUpdateFail();

    void onDataError();

    void onDeleteSuccess();

    void onDeleteFail();

    void onNewFoodItemCreated(FoodItem foodItem);
}
