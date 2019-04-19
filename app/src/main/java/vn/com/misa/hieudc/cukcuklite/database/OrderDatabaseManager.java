package vn.com.misa.hieudc.cukcuklite.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Map;

import vn.com.misa.hieudc.cukcuklite.model.FoodItem;
import vn.com.misa.hieudc.cukcuklite.model.Order;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Đối tượng quản lý dữ liệu đơn hàng trong csdl
 */
public class OrderDatabaseManager {
    private DatabaseHelper mDatabaseHelper;

    public OrderDatabaseManager() {
        mDatabaseHelper = DatabaseHelper.getInstance();
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Tạo đối tượng đơn hàng chứa thông tin cơ bản từ Cursor
     * @param cursor chứa thông tin đơn hàng
     * @return đơn hàng được tạo
     */
    public static Order createOrder(Cursor cursor) {
        Order order = new Order();
        try {
            order.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_ID)));
            order.setNumberOfTable(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_NUMBER_OF_TABLE)));
            order.setNumberOfPeople(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_NUMBER_OF_PEOPLE)));
            order.setStatus(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_STATUS)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/17/2019
     * Tìm và tạo 1 đon hàng hoàn chỉnh, bao gồm các ràng buộc
     * @param id id đơn hàng
     * @param db đối tượng SQLiteDatabase
     * @return đơn hàng tìm thấy
     */
    public static Order findOrder(long id, SQLiteDatabase db) {
        Order order = null;
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_ORDER + " LEFT OUTER JOIN " + DatabaseHelper.TABLE_ORDER_DETAIL
                    + " ON " + DatabaseHelper.COLUMN_ORDER_ID + "=" + DatabaseHelper.COLUMN_ORDER_DETAIL_FR_ORDER_ID + " WHERE " + DatabaseHelper.COLUMN_ORDER_ID + "=?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                order = new Order();
                order.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_ID)));
                order.setNumberOfTable(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_NUMBER_OF_TABLE)));
                order.setNumberOfPeople(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_NUMBER_OF_PEOPLE)));
                order.setStatus(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_STATUS)));
                do {
                    FoodItem foodItem = FoodItemDatabaseManager.findFoodItem(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_FR_FOOD_ID)), db);
                    order.addFoodItemWithAmount(foodItem, cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_AMOUNT)));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Lấy danh sách đơn hàng
     *
     * @return danh sách đơn hàng
     */
    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> list = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_ORDER
                    + " LEFT OUTER JOIN " + DatabaseHelper.TABLE_ORDER_DETAIL
                    + " ON " + DatabaseHelper.COLUMN_ORDER_ID + "=" + DatabaseHelper.COLUMN_ORDER_DETAIL_FR_ORDER_ID
                    /*+ " LEFT OUTER JOIN " + DatabaseHelper.TABLE_FOOD_ITEM
                    + " ON " + DatabaseHelper.COLUMN_FOOD_ITEM_ID + "=" + DatabaseHelper.COLUMN_ORDER_DETAIL_FR_FOOD_ID
                    + " LEFT OUTER JOIN " + DatabaseHelper.TABLE_UNIT_ITEM
                    + " ON " + DatabaseHelper.COLUMN_FOOD_ITEM_FR_UNIT_ID + "=" + DatabaseHelper.COLUMN_UNIT_ITEM_ID*/
                    + " WHERE " + DatabaseHelper.COLUMN_ORDER_STATUS + "='" + Order.ORDER_STATUS + "'";
            //+ " ORDER BY " + DatabaseHelper.TABLE_FOOD_ITEM + "." + COLUMN_NAME_FOOD_ITEM;
            SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                long currentOderId = -1;
                Order order = null;
                do {
                    long orderId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_ID));
                    FoodItem foodItem = FoodItemDatabaseManager.findFoodItem(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_FR_FOOD_ID)), db);
                    if (orderId != currentOderId) {
                        currentOderId = orderId;
                        if (order != null) {
                            list.add(order);
                        }
                        order = createOrder(cursor);
                        order.addFoodItemWithAmount(foodItem, cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_AMOUNT)));
                    } else {
                        if (order != null) {
                            order.addFoodItemWithAmount(foodItem, cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_AMOUNT)));
                        }
                    }
                } while (cursor.moveToNext());
                if (order != null) list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Thêm 1 món đơn hàng
     *
     * @param order đơn hàng
     * @return ID đơn được thêm
     */
    public long addOrder(Order order) {
        long result = -1;
        try {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            db.execSQL("PRAGMA foreign_keys=ON;");
            db.beginTransactionNonExclusive();
            try {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_ORDER_NUMBER_OF_TABLE, order.getNumberOfTable());
                values.put(DatabaseHelper.COLUMN_ORDER_NUMBER_OF_PEOPLE, order.getNumberOfPeople());
                values.put(DatabaseHelper.COLUMN_ORDER_STATUS, order.getStatus());
                long oderIdInserted = db.insertOrThrow(DatabaseHelper.TABLE_ORDER, null, values);
                if (oderIdInserted > -1) {
                    for (Map.Entry<FoodItem, Integer> me : order.getListFoodItem().entrySet()) {
                        ContentValues pivotValues = new ContentValues();
                        pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_FR_ORDER_ID, oderIdInserted);
                        pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_FR_FOOD_ID, me.getKey().getId());
                        pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_FOOD_NAME, me.getKey().getFoodItemsName());
                        pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_UNIT_NAME, me.getKey().getUnit().getName());
                        pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_AMOUNT, me.getValue());
                        pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_TOTAL_PAID, me.getValue() * me.getKey().getFoodItemsCost());
                        db.insertOrThrow(DatabaseHelper.TABLE_ORDER_DETAIL, null, pivotValues);
                    }
                }
                db.setTransactionSuccessful();
                result = oderIdInserted;
                //    result = true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Cập nhật đơn hàng
     *
     * @param order đơn
     * @return đơn sau khi cập nhật
     */
    public Order updateOrder(Order order) {
        try {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            db.execSQL("PRAGMA foreign_keys=ON;");
            db.beginTransactionNonExclusive();
            try {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_ORDER_NUMBER_OF_TABLE, order.getNumberOfTable());
                values.put(DatabaseHelper.COLUMN_ORDER_NUMBER_OF_PEOPLE, order.getNumberOfPeople());
                values.put(DatabaseHelper.COLUMN_ORDER_STATUS, order.getStatus());
                /*int result = */
                db.update(DatabaseHelper.TABLE_ORDER, values, DatabaseHelper.COLUMN_ORDER_ID + "=" + String.valueOf(order.getId()), null);

                db.delete(DatabaseHelper.TABLE_ORDER_DETAIL, DatabaseHelper.COLUMN_ORDER_DETAIL_FR_ORDER_ID + "=?", new String[]{String.valueOf(order.getId())});

                for (Map.Entry<FoodItem, Integer> me : order.getListFoodItem().entrySet()) {
                    ContentValues pivotValues = new ContentValues();
                    pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_FR_ORDER_ID, order.getId());
                    pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_FR_FOOD_ID, (me.getKey().getId()));
                    pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_FOOD_NAME, me.getKey().getFoodItemsName());
                    pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_UNIT_NAME, me.getKey().getUnit().getName());
                    pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_AMOUNT, me.getValue());
                    pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_TOTAL_PAID, me.getValue() * me.getKey().getFoodItemsCost());
                    db.insertOrThrow(DatabaseHelper.TABLE_ORDER_DETAIL, null, pivotValues);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Xóa 1 đơn hàng
     *
     * @param order đơn cần xóa
     * @return số hàng bị xóa
     */
    public int deleteOrder(Order order) {
        try {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            db.execSQL("PRAGMA foreign_keys=ON;");

            int result = db.delete(DatabaseHelper.TABLE_ORDER, DatabaseHelper.COLUMN_ORDER_ID + "=?", new String[]{String.valueOf(order.getId())});
            db.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

/*    public void checkoutOrder(Order order) {
        try {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_ORDER + " WHERE " + DatabaseHelper.COLUMN_ORDER_ID + "=?", new String[]{String.valueOf(order.getId())});
            order.setStatus(Order.PAID_STATUS);
            if (cursor.moveToFirst()) {
                updateOrder(order);
            } else {
                addOrder(order);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
/*
    public ArrayList<Order> getAllPaidOrders() {
        ArrayList<Order> list = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_ORDER
                    + " LEFT OUTER JOIN " + DatabaseHelper.TABLE_ORDER_DETAIL
                    + " ON " + DatabaseHelper.COLUMN_ORDER_ID + "=" + DatabaseHelper.COLUMN_ORDER_DETAIL_FR_ORDER_ID
                    + " LEFT OUTER JOIN " + DatabaseHelper.TABLE_FOOD_ITEM
                    + " ON " + DatabaseHelper.COLUMN_FOOD_ITEM_ID + "=" + DatabaseHelper.COLUMN_ORDER_DETAIL_FR_FOOD_ID
                    + " LEFT OUTER JOIN " + DatabaseHelper.TABLE_UNIT_ITEM
                    + " ON " + DatabaseHelper.COLUMN_FOOD_ITEM_FR_UNIT_ID + "=" + DatabaseHelper.COLUMN_UNIT_ITEM_ID
                    + " WHERE " + DatabaseHelper.COLUMN_ORDER_STATUS + "='" + Order.PAID_STATUS + "'";
            //+ " ORDER BY " + DatabaseHelper.TABLE_FOOD_ITEM + "." + COLUMN_NAME_FOOD_ITEM;
            SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                long currentOderId = -1;
                Order order = null;
                ArrayList<CheckoutItem> CheckoutItems;
                do {
                    long orderId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_ID));
                    FoodItem foodItem = FoodItemDatabaseManager.createBaseFoodItem(cursor);
                    if (orderId != currentOderId) {
                        currentOderId = orderId;
                        if (order != null) {
                            list.add(order);
                        }
                        order = createOrder(cursor);
                        order.addFoodItemWithAmount(foodItem, cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_AMOUNT)));
                    } else {
                        if (order != null) {
                            order.addFoodItemWithAmount(foodItem, cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_AMOUNT)));
                        }
                    }
                } while (cursor.moveToNext());
                if (order != null) list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }*/
}
