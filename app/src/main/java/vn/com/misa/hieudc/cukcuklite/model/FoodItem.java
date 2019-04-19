package vn.com.misa.hieudc.cukcuklite.model;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Đối tượng món ăn
 */
public class FoodItem implements Serializable {
    public static final String COLOR_DEFAULT = "#039be5", ICON_DEFAULT = "ic_default.png";
    public static final String SELLING = "SELLING";
    public static final String STOP_SELLING = "STOP_SELLING";
    private long mId;
    private String mFoodItemsName;
    private long mFoodItemsCost;
    private String mSelling;
    private String mColor;
    private String mIcon;
    private UnitItem mUnit;

    public FoodItem() {
        mFoodItemsCost = 0;
        mSelling = SELLING;
        mColor = COLOR_DEFAULT;
        mIcon = ICON_DEFAULT;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getFoodItemsName() {
        return mFoodItemsName;
    }

    public void setFoodItemsName(String foodItemsName) {
        mFoodItemsName = foodItemsName;
    }

    public long getFoodItemsCost() {
        return mFoodItemsCost;
    }

    public void setFoodItemsCost(long foodItemsCost) {
        mFoodItemsCost = foodItemsCost;
    }

    public String getSelling() {
        return mSelling;
    }

    public void setSelling(String selling) {
        mSelling = selling;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public UnitItem getUnit() {
        return mUnit;
    }

    public void setUnit(UnitItem unit) {
        mUnit = unit;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof FoodItem){
            return ((FoodItem) obj).getId() == mId ;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) mId;
    }
}
