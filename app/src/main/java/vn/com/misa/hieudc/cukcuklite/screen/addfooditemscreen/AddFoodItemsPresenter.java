package vn.com.misa.hieudc.cukcuklite.screen.addfooditemscreen;

import android.content.Context;
import android.text.TextUtils;

import vn.com.misa.hieudc.cukcuklite.model.FoodItem;
import vn.com.misa.hieudc.cukcuklite.database.FoodItemDatabaseManager;
import vn.com.misa.hieudc.cukcuklite.database.UnitItemDatabaseManager;

/**
 * Created_by: dchieu
 * Created_date: 3/27/2019
 * Presenter cho Activity thêm món ăn
 */
public class AddFoodItemsPresenter implements IAddFoodItemsPresenter {
    private FoodItemDatabaseManager mFoodItemDatabaseManager;
    private UnitItemDatabaseManager mUnitItemDatabaseManager;
    private IAddFoodItemView mIAddFoodItemView;

    public AddFoodItemsPresenter(Context context) {
        try {
            mFoodItemDatabaseManager = new FoodItemDatabaseManager();
            mUnitItemDatabaseManager = new UnitItemDatabaseManager();
            mIAddFoodItemView = (IAddFoodItemView) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Thực hiện lưu món ăn
     *
     * @param foodItem món ăn cần lưu
     */
    @Override
    public void saveFoodItem(FoodItem foodItem) {
        if (foodItem == null || TextUtils.isEmpty(foodItem.getFoodItemsName()) || foodItem.getUnit() == null) {
            mIAddFoodItemView.onDataError();
            return;
        }
        try {
            if (mFoodItemDatabaseManager.addFoodItem(foodItem)) {
                mIAddFoodItemView.onAddSuccess();
            } else {
                mIAddFoodItemView.onAddFail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Thực hiện cập nhật món ăn
     *
     * @param foodItem món ăn cần cập nhật
     */
    @Override
    public void updateFoodItem(FoodItem foodItem) {
        if (foodItem == null || TextUtils.isEmpty(foodItem.getFoodItemsName()) || foodItem.getUnit() == null) {
            mIAddFoodItemView.onDataError();
            return;
        }
        try {
            if (mFoodItemDatabaseManager.updateFoodItem(foodItem) != null) {
                mIAddFoodItemView.onUpdateSuccess(foodItem);
            } else {
                mIAddFoodItemView.onUpdateFail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Xóa 1 món ăn
     *
     * @param foodItem món ăn cần xóa
     */
    @Override
    public void deleteFoodItem(FoodItem foodItem) {
        if (foodItem == null) return;
        try {
            if (mFoodItemDatabaseManager.deleteFoodItems(foodItem) > 0)
                mIAddFoodItemView.onDeleteSuccess();
            else mIAddFoodItemView.onDeleteFail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createNewFoodItem() {
        try {
            FoodItem foodItem = new FoodItem();
            foodItem.setUnit(mUnitItemDatabaseManager.getUnitItemFirst());
            mIAddFoodItemView.onNewFoodItemCreated(foodItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
