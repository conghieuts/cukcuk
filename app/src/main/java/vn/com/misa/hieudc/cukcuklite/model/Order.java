package vn.com.misa.hieudc.cukcuklite.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created_by: dchieu
 * Created_date: 4/4/2019
 * Đối tượng đơn hàng
 */
public class Order implements Serializable {
    public static String ORDER_STATUS = "order";
    public static String PAID_STATUS = "paid";

    private long mId;
    private int mNumberOfTable;
    private int mNumberOfPeople;
    private String mStatus;
    private HashMap<FoodItem, Integer> mListFoodItem;

    public Order() {
        mNumberOfPeople = -1;
        mNumberOfTable = -1;
        mListFoodItem = new HashMap<>();
        mStatus = ORDER_STATUS;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public int getNumberOfTable() {
        return mNumberOfTable;
    }

    public void setNumberOfTable(int numberOfTable) {
        mNumberOfTable = numberOfTable;
    }

    public int getNumberOfPeople() {
        return mNumberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        mNumberOfPeople = numberOfPeople;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public HashMap<FoodItem, Integer> getListFoodItem() {
        return mListFoodItem;
    }

    public void setListFoodItem(HashMap<FoodItem, Integer> listFoodItem) {
        mListFoodItem = listFoodItem;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/4/2019
     *
     * Thêm món ăn mới vào đơn với số lượng cụ thể
     * @param foodItem món ăn
     * @param amount số lượng
     */
    public void addFoodItemWithAmount(FoodItem foodItem, int amount) {
        if (foodItem == null) return;
        try {
            mListFoodItem.put(foodItem, amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/4/2019
     * Thêm món ăn vào đơn
     * @param foodItem món ăn
     */
    public void addFoodItem(FoodItem foodItem) {
        if (foodItem == null) return;
        try {
            Integer amount;
            if (mListFoodItem.containsKey(foodItem)) {
                amount = mListFoodItem.get(foodItem);
                if (amount == null)
                    amount = 1;
                else amount += 1;
                mListFoodItem.put(foodItem, amount);
            } else {
                mListFoodItem.put(foodItem, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/4/2019
     * Lấy số lượng món ăn rong đơn
     * @param foodItem món ăn
     * @return số lượng món ăn trong đơn
     */
    public int getAmount(FoodItem foodItem) {
        Integer amount = 0;
        try {
            if (mListFoodItem.containsKey(foodItem)) {
                amount = mListFoodItem.get(foodItem);
                if (amount == null)
                    return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/4/2019
     * Thêm món ăn nếu chưa có, hoặc xóa nếu đã có trong đón
     * @param foodItem món ăn
     */
    public void toggleFoodItem(FoodItem foodItem) {
        try {
            if (mListFoodItem.size() != 0 && mListFoodItem.containsKey(foodItem)) {
                mListFoodItem.remove(foodItem);
            } else {
                mListFoodItem.put(foodItem, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/4/2019
     * Xóa món ăn khỏi đơn
     *
     * @param foodItem món ăn
     */
    public void removeFoodItem(FoodItem foodItem) {
        if (foodItem == null) return;
        Integer amount;
        try {
            if (mListFoodItem.containsKey(foodItem)) {
                amount = mListFoodItem.get(foodItem);
                if (amount != null)
                    amount -= 1;
                else amount = 0;
                if (amount > 0) mListFoodItem.put(foodItem, amount);
                else mListFoodItem.remove(foodItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/4/2019
     * Lấy tổng thanh toán của món ăn
     * @return Tổng thanh toán
     */
    public int getTotalPaid() {
        int totalPaid = 0;
        try {
            for (Map.Entry me : mListFoodItem.entrySet()) {
                FoodItem foodItem = (FoodItem) me.getKey();
                Integer amount = (Integer) me.getValue();
                totalPaid += foodItem.getFoodItemsCost() * amount;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalPaid;
    }
}
