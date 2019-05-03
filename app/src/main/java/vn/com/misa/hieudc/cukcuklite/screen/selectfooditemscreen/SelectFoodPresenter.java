package vn.com.misa.hieudc.cukcuklite.screen.selectfooditemscreen;

import android.support.annotation.Nullable;

import vn.com.misa.hieudc.cukcuklite.model.FoodItem;
import vn.com.misa.hieudc.cukcuklite.database.FoodItemDatabaseManager;
import vn.com.misa.hieudc.cukcuklite.model.Order;
import vn.com.misa.hieudc.cukcuklite.database.OrderDatabaseManager;

/**
 * Created_by: dchieu
 * Created_date: 4/18/2019
 * Lớp logic cho màn hình chọn món ăn cho đơn hàng
 */
public class SelectFoodPresenter implements ISelectFoodPresenter {
    private ISelectFoodView mISelectFoodView;
    private FoodItemDatabaseManager mFoodItemDatabaseManager;
    private OrderDatabaseManager mOrderDatabaseManager;
    private Order mOrder;

    SelectFoodPresenter(ISelectFoodView ISelectFoodView, @Nullable Order order) {
        try {
            mISelectFoodView = ISelectFoodView;
            mFoodItemDatabaseManager = new FoodItemDatabaseManager();
            mOrderDatabaseManager = new OrderDatabaseManager();
            if (order != null) {
                mOrder = order;
            } else mOrder = new Order();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Lấy toàn bộ danh sách món ăn
     */
    @Override
    public void getAllFoodItems() {
        try {
            mISelectFoodView.onGetAllFoodItemsDone(mFoodItemDatabaseManager.getAllFoodItemsSelling());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Lưu đơn hàng
     */
    @Override
    public void saveOderItem() {
        try {
            if (mOrder.getListFoodItem().size() != 0) {
                mOrderDatabaseManager.addOrder(mOrder);
                mISelectFoodView.onUpdateSuccess("Thêm thành công");
            } else mISelectFoodView.onDataError();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Cập nhật dữ liệu đơn hàng
     */
    @Override
    public void updateOrderItem() {
        try {
            if (mOrder.getListFoodItem().size() != 0) {
                mOrderDatabaseManager.updateOrder(mOrder);
                mISelectFoodView.onUpdateSuccess("Cập nhật thành công");
            } else mISelectFoodView.onDataError();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startCheckout() {
        if (mOrder.getListFoodItem().size() != 0) {
            mISelectFoodView.onCheckout();
        } else mISelectFoodView.onDataError();
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Thêm món ăn vào đơn hàng
     * @param foodItem món ăn
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
        try {
            mOrder.addFoodItem(foodItem);
            mISelectFoodView.onItemUpdate(mOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Thêm khi chưa có hoặc xóa khi đã có món ăn trong đơn hàng
     * @param foodItem món ăn
     */
    @Override
    public void toggleFoodItem(FoodItem foodItem) {
        try {
            mOrder.toggleFoodItem(foodItem);
            mISelectFoodView.onItemUpdate(mOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Xóa món ăn khỏi đơn hàng
     * @param foodItem món ăn
     */
    @Override
    public void removeFoodItem(FoodItem foodItem) {
        try {
            mOrder.removeFoodItem(foodItem);
            mISelectFoodView.onItemUpdate(mOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Thêm số lượng cụ thể của 1 món ăn vào đon hàng
     * @param foodItem món ăn
     * @param amount số lượng
     */
    @Override
    public void addFoodItemWithAmount(FoodItem foodItem, int amount) {
        try {
            mOrder.addFoodItemWithAmount(foodItem, amount);
            mISelectFoodView.onItemUpdate(mOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Thêm số bàn vào đơn
     * @param parseInt số bàn
     */
    @Override
    public void setNumberOfTable(int parseInt) {
        try {
            if (parseInt > 99) parseInt = 99;
            mOrder.setNumberOfTable(parseInt);
            mISelectFoodView.onNumberOfTableUpdate(mOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Theemsoos lượng người vào đơn hàng
     * @param parseInt số người
     */
    @Override
    public void setNumberOfPeople(int parseInt) {
        try {
            if (parseInt > 99) parseInt = 99;
            mOrder.setNumberOfPeople(parseInt);
            mISelectFoodView.onNumberOfPeopleUpdate(mOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
