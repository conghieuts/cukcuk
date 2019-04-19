package vn.com.misa.hieudc.cukcuklite.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Map;

import vn.com.misa.hieudc.cukcuklite.model.Bill;
import vn.com.misa.hieudc.cukcuklite.model.CheckoutItem;
import vn.com.misa.hieudc.cukcuklite.model.FoodItem;
import vn.com.misa.hieudc.cukcuklite.model.Order;

/**
 * Created_by: dchieu
 * Created_date: 4/12/2019
 * Class quản lý thao tác dữ liệu hóa đơn
 */
public class BillDatabaseManager {
    private DatabaseHelper mDatabaseHelper;

    public BillDatabaseManager() {
        mDatabaseHelper = DatabaseHelper.getInstance();
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/12/2019
     *
     * @param bill       hóa đơn mới
     * @param isNewOrder là 1 đơn mới hay không
     * @return id hóa đơn được tạo
     */
    public long addBill(Bill bill, boolean isNewOrder) {
        int result = 0;
        try {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            db.execSQL("PRAGMA foreign_keys=ON;");
            db.beginTransactionNonExclusive();
            try {
                int orderId = updateOrder(bill.getOrder(), db, isNewOrder);

                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.COLUMN_BILL_NUMBER, bill.getNumber());
                contentValues.put(DatabaseHelper.COLUMN_BILL_CHARGE, bill.getCharge());
                contentValues.put(DatabaseHelper.COLUMN_BILL_RECEIVE, bill.getReceive());
                contentValues.put(DatabaseHelper.COLUMN_BILL_TIME, bill.getTime());
                contentValues.put(DatabaseHelper.COLUMN_BILL_FR_ORDER_ID, orderId);
                int insertID = (int) db.insertOrThrow(DatabaseHelper.TABLE_BILL, null, contentValues);

                db.setTransactionSuccessful();
                result = insertID;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            db.endTransaction();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/12/2019
     * <p>
     * Thực hiện cập nhật 1 đơn hàng mới vào csdl
     *
     * @param order      đơn hàng
     * @param db
     * @param isNewOrder là đơn hàng mới hay không
     * @return id đơn hàng, -1 nếu có lỗi
     */
    private int updateOrder(Order order, SQLiteDatabase db, boolean isNewOrder) {
        long orderId = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ORDER_NUMBER_OF_TABLE, order.getNumberOfTable());
            values.put(DatabaseHelper.COLUMN_ORDER_NUMBER_OF_PEOPLE, order.getNumberOfPeople());
            values.put(DatabaseHelper.COLUMN_ORDER_STATUS, order.getStatus());
            if (isNewOrder) orderId = db.insertOrThrow(DatabaseHelper.TABLE_ORDER, null, values);
            else {
                orderId = order.getId();
                db.update(DatabaseHelper.TABLE_ORDER, values, DatabaseHelper.COLUMN_ORDER_ID + "=" + String.valueOf(orderId), null);
                db.delete(DatabaseHelper.TABLE_ORDER_DETAIL, DatabaseHelper.COLUMN_ORDER_DETAIL_FR_ORDER_ID + "=?", new String[]{String.valueOf(order.getId())});
            }
            if (orderId > -1) {
                for (Map.Entry<FoodItem, Integer> me : order.getListFoodItem().entrySet()) {
                    ContentValues pivotValues = new ContentValues();
                    pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_FR_ORDER_ID, orderId);
                    pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_FR_FOOD_ID, me.getKey().getId());
                    pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_FOOD_NAME, me.getKey().getFoodItemsName());
                    pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_UNIT_NAME, me.getKey().getUnit().getName());
                    pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_AMOUNT, me.getValue());
                    pivotValues.put(DatabaseHelper.COLUMN_ORDER_DETAIL_TOTAL_PAID, me.getValue() * me.getKey().getFoodItemsCost());
                    db.insertOrThrow(DatabaseHelper.TABLE_ORDER_DETAIL, null, pivotValues);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (int) orderId;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/12/2019
     * <p>
     * Lấy toán bộ hóa đơn
     *
     * @return danh sách hóa đơn
     */
    public ArrayList<Bill> getAllBill() {
        ArrayList<Bill> list = new ArrayList<>();
        try {
            SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
            db.execSQL("PRAGMA foreign_keys=ON;");

            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_BILL
                    + " LEFT JOIN " + DatabaseHelper.TABLE_ORDER + " ON " + DatabaseHelper.COLUMN_ORDER_ID + "=" + DatabaseHelper.COLUMN_BILL_FR_ORDER_ID
                    + " LEFT JOIN " + DatabaseHelper.TABLE_ORDER_DETAIL + " ON " + DatabaseHelper.COLUMN_ORDER_ID + "=" + DatabaseHelper.COLUMN_ORDER_DETAIL_FR_ORDER_ID, null);
            Bill bill = null;
            int currentId = -1;
            while (cursor.moveToNext()) {
                int billId = (int) cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_BILL_ID));
                CheckoutItem checkoutItem = new CheckoutItem();
                checkoutItem.setFoodName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_FOOD_NAME)));
                checkoutItem.setAmount(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_AMOUNT)));
                checkoutItem.setTotalPaid(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_TOTAL_PAID)));
                checkoutItem.setUnit(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_UNIT_NAME)));
                if (currentId != billId) {
                    currentId = billId;
                    if (bill != null) {
                        list.add(bill);
                    }
                    bill = createBaseBill(cursor);
                }
                if (bill != null) {
                    bill.getCheckoutItemArrayList().add(checkoutItem);
                }
            }
            if (bill != null) {
                list.add(bill);
            }
            cursor.close();
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/12/2019
     * <p>
     * Lấy danh sách các mặt hàng đã bán
     *
     * @return danh sách món ăn đã thanh toán
     */
    public ArrayList<CheckoutItem> getReportInTime(long start, long end) {
        ArrayList<CheckoutItem> list = new ArrayList<>();
        try {
            SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
            db.execSQL("PRAGMA foreign_keys=ON;");

            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_BILL
                    + " LEFT JOIN " + DatabaseHelper.TABLE_ORDER + " ON " + DatabaseHelper.COLUMN_ORDER_ID + "=" + DatabaseHelper.COLUMN_BILL_FR_ORDER_ID
                    + " LEFT JOIN " + DatabaseHelper.TABLE_ORDER_DETAIL + " ON " + DatabaseHelper.COLUMN_ORDER_ID + "=" + DatabaseHelper.COLUMN_ORDER_DETAIL_FR_ORDER_ID
                    + " WHERE " + DatabaseHelper.COLUMN_BILL_TIME + " BETWEEN " + String.valueOf(start) + " AND " + String.valueOf(end), null);
            while (cursor.moveToNext()) {
                CheckoutItem checkoutItem = new CheckoutItem();
                checkoutItem.setFoodItemId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_FR_FOOD_ID)));
                checkoutItem.setFoodName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_FOOD_NAME)));
                checkoutItem.setAmount(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_AMOUNT)));
                checkoutItem.setTotalPaid(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_TOTAL_PAID)));
                checkoutItem.setUnit(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_UNIT_NAME)));
                boolean check = false;
                for (CheckoutItem checkoutItemInList : list) {
                    if (checkoutItemInList.equals(checkoutItem)) {
                        check = true;
                        checkoutItemInList.setTotalPaid(checkoutItemInList.getTotalPaid() + checkoutItem.getTotalPaid());
                        checkoutItemInList.setAmount(checkoutItemInList.getAmount() + checkoutItem.getAmount());
                    }
                }
                if (!check) {
                    list.add(checkoutItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/12/2019
     * Tạo 1 hóa đơn với các trường cơ bản từ cursor
     *
     * @param cursor Cursor chưa thông tin
     * @return Hóa đơn sau khi tạo
     */
    private Bill createBaseBill(Cursor cursor) {
        Bill bill = new Bill();
        try {
            bill.setId((int) cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_BILL_ID)));
            bill.setCharge(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_BILL_CHARGE)));
            bill.setReceive(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_BILL_RECEIVE)));
            bill.setNumber(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_BILL_NUMBER)));
            bill.setTime(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_BILL_TIME)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bill;
    }

    public ArrayList<Long> getTotalReportByTime(ArrayList<Long> timeList) {
        ArrayList<Long> result = new ArrayList<>();
        for (int i = 0; i < timeList.size() - 1; i++) {
            long start = timeList.get(i);
            long end = timeList.get(i + 1) - 1;
            long total = 0;
            try {
                SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
                db.execSQL("PRAGMA foreign_keys=ON;");
                String sql = "SELECT * FROM " + DatabaseHelper.TABLE_BILL
                        + " LEFT JOIN " + DatabaseHelper.TABLE_ORDER + " ON " + DatabaseHelper.COLUMN_ORDER_ID + "=" + DatabaseHelper.COLUMN_BILL_FR_ORDER_ID
                        + " LEFT JOIN " + DatabaseHelper.TABLE_ORDER_DETAIL + " ON " + DatabaseHelper.COLUMN_ORDER_ID + "=" + DatabaseHelper.COLUMN_ORDER_DETAIL_FR_ORDER_ID
                        + " WHERE " + DatabaseHelper.COLUMN_BILL_TIME + " BETWEEN " + String.valueOf(start) + " AND " + String.valueOf(end);
                Cursor cursor = db.rawQuery(sql, null);
                while (cursor.moveToNext()) {
                    total += cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAIL_TOTAL_PAID));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            result.add(total);
        }

        return result;
    }
}
