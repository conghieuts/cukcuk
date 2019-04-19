package vn.com.misa.hieudc.cukcuklite.model;

import androidx.annotation.Nullable;

/**
 * Created_by: dchieu
 * Created_date: 4/12/2019
 * Đối tượng món ăn đã thanh toán
 */
public class CheckoutItem {
    private int mFoodItemId;
    private String mFoodName;
    private String mUnit;
    private int mAmount;
    private int mTotalPaid;

    public CheckoutItem() {
    }

    CheckoutItem(FoodItem foodItem, int amount) {
        try {
            mFoodItemId = (int) foodItem.getId();
            mFoodName = foodItem.getFoodItemsName();
            mUnit = foodItem.getUnit().getName();
            mAmount = amount;
            mTotalPaid = (int) (foodItem.getFoodItemsCost()*amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getFoodItemId() {
        return mFoodItemId;
    }

    public void setFoodItemId(int foodItemId) {
        mFoodItemId = foodItemId;
    }

    public String getFoodName() {
        return mFoodName;
    }

    public void setFoodName(String foodName) {
        mFoodName = foodName;
    }

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }

    public int getTotalPaid() {
        return mTotalPaid;
    }

    public void setTotalPaid(int totalPaid) {
        mTotalPaid = totalPaid;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof CheckoutItem) {
            return ((CheckoutItem) obj).getFoodItemId() == mFoodItemId &&
                    ((CheckoutItem) obj).getUnit().equals(mUnit) &&
                    ((CheckoutItem) obj).getFoodName().equals(mFoodName);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return mFoodName.hashCode();
    }
}
