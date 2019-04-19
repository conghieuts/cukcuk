package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.menuscreen;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.model.FoodItem;
import vn.com.misa.hieudc.cukcuklite.database.FoodItemDatabaseManager;

/**
 * Created_by: dchieu
 * Created_date: 3/27/2019
 * Lớp logic cho Activity danh sách món ăn
 */
public class MenuPresenter implements IMenuPresenter {
    private FoodItemDatabaseManager mFoodItemDatabaseManager;
    private IMenuView mIMenuView;

    public MenuPresenter(IMenuView iMenuView) {
        try {
            mIMenuView = iMenuView;
            mFoodItemDatabaseManager = new FoodItemDatabaseManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Lấy danh sách món ăn
     */
    @Override
    public void getAllFoodItem() {
        try {
            ArrayList<FoodItem> mFoodItemArrayList;
            mFoodItemArrayList = mFoodItemDatabaseManager.getAllFoodItems();
            mIMenuView.getAllFoodItemDone(mFoodItemArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
