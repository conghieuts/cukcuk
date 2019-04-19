package vn.com.misa.hieudc.cukcuklite.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created_by: dchieu
 * Created_date: 4/12/2019
 * Đối tượng hóa đơn
 */
public class Bill {
    private int mId;
    private int mNumber;
    private int mCharge;
    private int mReceive;
    private long mTime;
    private Order mOrder;
    private ArrayList<CheckoutItem> mCheckoutItemArrayList;

    public Bill() {
        mNumber = 0;
        mCharge = 0;
        mReceive = 0;
        mTime = Calendar.getInstance().getTimeInMillis();
        mCheckoutItemArrayList = new ArrayList<>();
    }

    public Bill(Order order) {
        mNumber = 0;
        mCharge = 0;
        mReceive = 0;
        mTime = Calendar.getInstance().getTimeInMillis();
        mCheckoutItemArrayList = new ArrayList<>();
        mOrder = order;
        try {
            if (order != null) {
                mNumber = order.getNumberOfTable();
                mCharge = 0;
                ArrayList<CheckoutItem> checkoutItems = new ArrayList<>();
                HashMap<FoodItem, Integer> listFoodItem = order.getListFoodItem();
                for (Map.Entry me : listFoodItem.entrySet()) {
                    try {
                        FoodItem foodItem = (FoodItem) me.getKey();
                        checkoutItems.add(new CheckoutItem(foodItem, (int) me.getValue()));
                        mCharge += (int) (foodItem.getFoodItemsCost() * ((int) me.getValue()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mCheckoutItemArrayList = checkoutItems;
                mReceive = mCharge;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public int getCharge() {
        return mCharge;
    }

    public void setCharge(int charge) {
        mCharge = charge;
    }

    public int getReceive() {
        return mReceive;
    }

    public void setReceive(int receive) {
        mReceive = receive;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public Order getOrder() {
        return mOrder;
    }

    public void setOrder(Order order) {
        mOrder = order;
        try {
            if (order != null) {
                mNumber = order.getNumberOfTable();
                mTime = Calendar.getInstance().getTimeInMillis();
                mCharge = 0;
                ArrayList<CheckoutItem> checkoutItems = new ArrayList<>();
                HashMap<FoodItem, Integer> listFoodItem = order.getListFoodItem();
                for (Map.Entry me : listFoodItem.entrySet()) {
                    try {
                        FoodItem foodItem = (FoodItem) me.getKey();
                        checkoutItems.add(new CheckoutItem(foodItem, (int) me.getValue()));
                        mCharge += (int) (foodItem.getFoodItemsCost() * ((int) me.getValue()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mCheckoutItemArrayList = checkoutItems;
                mReceive = mCharge;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CheckoutItem> getCheckoutItemArrayList() {
        return mCheckoutItemArrayList;
    }

    public void setCheckoutItemArrayList(ArrayList<CheckoutItem> checkoutItemArrayList) {
        mCheckoutItemArrayList = checkoutItemArrayList;
    }

}
