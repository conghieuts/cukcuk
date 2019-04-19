package vn.com.misa.hieudc.cukcuklite.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.model.FoodItem;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Đối tượng quản lý dối tượng món ăn trong csdl
 */
public class FoodItemDatabaseManager {
    private DatabaseHelper mDatabaseHelper;

    public FoodItemDatabaseManager() {
        mDatabaseHelper = DatabaseHelper.getInstance();
    }

    private final String TABLE_FOOD_ITEM = DatabaseHelper.TABLE_FOOD_ITEM;
    private final String COLUMN_ID_FOOD_ITEM = DatabaseHelper.COLUMN_FOOD_ITEM_ID;
    private final String COLUMN_NAME_FOOD_ITEM = DatabaseHelper.COLUMN_FOOD_ITEM_NAME;
    private final String COLUMN_COST_FOOD_ITEM = DatabaseHelper.COLUMN_FOOD_ITEM_COST;
    private final String COLUMN_SELLING_FOOD_ITEM = DatabaseHelper.COLUMN_FOOD_ITEM_SELLING;
    private final String COLUMN_COLOR_FOOD_ITEM = DatabaseHelper.COLUMN_FOOD_ITEM_COLOR;
    private final String COLUMN_ICON_FOOD_ITEM = DatabaseHelper.COLUMN_FOOD_ITEM_ICON;
    private final String COLUMN_UNIT_FOOD_ITEM = DatabaseHelper.COLUMN_FOOD_ITEM_FR_UNIT_ID;

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     *
     * @param cursor Cursor chứa thông tin món ăn
     * @return món ăn được tạo
     */
    public static FoodItem createBaseFoodItem(Cursor cursor) {
        FoodItem foodItem = new FoodItem();
        try {
            foodItem.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_ID)));
            foodItem.setFoodItemsName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_NAME)));
            foodItem.setFoodItemsCost(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_COST)));
            foodItem.setColor(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_COLOR)));
            foodItem.setIcon(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_ICON)));
            foodItem.setSelling(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_SELLING)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foodItem;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Tìm và tạo 1 món ăn
     * @param id id món ăn
     * @param db đối tượng db
     * @return món ăn sau khi tạo
     */
    public static FoodItem findFoodItem(long id, SQLiteDatabase db) {
        FoodItem foodItem = null;
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_FOOD_ITEM + " WHERE " + DatabaseHelper.COLUMN_FOOD_ITEM_ID + "=?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                foodItem = new FoodItem();
                foodItem.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_ID)));
                foodItem.setFoodItemsName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_NAME)));
                foodItem.setFoodItemsCost(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_COST)));
                foodItem.setColor(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_COLOR)));
                foodItem.setIcon(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_ICON)));
                foodItem.setSelling(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_SELLING)));
                foodItem.setUnit(UnitItemDatabaseManager.findUnitItem(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_FR_UNIT_ID)), db));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return foodItem;
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Lấy danh sách món ăn
     *
     * @return danh sách món ăn
     */
    public ArrayList<FoodItem> getAllFoodItems() {
        ArrayList<FoodItem> list = new ArrayList<>();
        try {
            String selectQuery = "SELECT *" /*+ TABLE_FOOD_ITEM + ".*," + DatabaseHelper.TABLE_UNIT_ITEM + "." + DatabaseHelper.COLUMN_UNIT_ITEM_ID
                    + " AS unit_id," + DatabaseHelper.TABLE_UNIT_ITEM + "." + DatabaseHelper.COLUMN_UNIT_ITEM_NAME*/
                    + " FROM " + TABLE_FOOD_ITEM /*+ " LEFT OUTER JOIN " + DatabaseHelper.TABLE_UNIT_ITEM
                    + " ON " + TABLE_FOOD_ITEM + "." + COLUMN_UNIT_FOOD_ITEM + "=" + DatabaseHelper.TABLE_UNIT_ITEM + "." + DatabaseHelper.COLUMN_UNIT_ITEM_ID*/
                    + " ORDER BY " + DatabaseHelper.TABLE_FOOD_ITEM + "." + COLUMN_NAME_FOOD_ITEM;
            SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    FoodItem foodItem = createBaseFoodItem(cursor);
                    foodItem.setUnit(UnitItemDatabaseManager.findUnitItem(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_FR_UNIT_ID)), db));
                    list.add(foodItem);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Lấy danh sách món ăn đang được bán
     *
     * @return danh sách món ăn
     */
    public ArrayList<FoodItem> getAllFoodItemsSelling() {
        ArrayList<FoodItem> list = new ArrayList<>();
        try {
            String selectQuery = "SELECT *"/* + TABLE_FOOD_ITEM + ".*," + DatabaseHelper.TABLE_UNIT_ITEM + "." + DatabaseHelper.COLUMN_UNIT_ITEM_ID
                    + " AS unit_id," + DatabaseHelper.TABLE_UNIT_ITEM + "." + DatabaseHelper.COLUMN_UNIT_ITEM_NAME*/
                    + " FROM " + TABLE_FOOD_ITEM /*+ " LEFT OUTER JOIN " + DatabaseHelper.TABLE_UNIT_ITEM
                    + " ON " + TABLE_FOOD_ITEM + "." + COLUMN_UNIT_FOOD_ITEM + "=" + DatabaseHelper.TABLE_UNIT_ITEM + "." + DatabaseHelper.COLUMN_UNIT_ITEM_ID*/
                    + " WHERE " + COLUMN_SELLING_FOOD_ITEM + "='" + FoodItem.SELLING + "'"
                    + " ORDER BY " + DatabaseHelper.TABLE_FOOD_ITEM + "." + COLUMN_NAME_FOOD_ITEM;
            SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    FoodItem foodItem = createBaseFoodItem(cursor);
                    foodItem.setUnit(UnitItemDatabaseManager.findUnitItem(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_FOOD_ITEM_FR_UNIT_ID)), db));
                    list.add(foodItem);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Thêm 1 món ăn
     *
     * @param foodItem món ăn
     * @return Trạng thái thêm thành công/ thất bại
     */
    public boolean addFoodItem(FoodItem foodItem) {
        try {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            db.execSQL("PRAGMA foreign_keys=ON;");
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_FOOD_ITEM, foodItem.getFoodItemsName());
            values.put(COLUMN_COST_FOOD_ITEM, foodItem.getFoodItemsCost());
            values.put(COLUMN_COLOR_FOOD_ITEM, foodItem.getColor());
            values.put(COLUMN_ICON_FOOD_ITEM, foodItem.getIcon());
            values.put(COLUMN_SELLING_FOOD_ITEM, foodItem.getSelling());
            if (foodItem.getUnit() != null)
                values.put(COLUMN_UNIT_FOOD_ITEM, foodItem.getUnit().getId());
            boolean result = db.insert(TABLE_FOOD_ITEM, null, values) != -1;
            db.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Cập nhật món ăn
     *
     * @param foodItem món ăn
     * @return món ăn sau khi cập nhật
     */
    public FoodItem updateFoodItem(FoodItem foodItem) {
        try {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            db.execSQL("PRAGMA foreign_keys=ON;");

            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME_FOOD_ITEM, foodItem.getFoodItemsName());
            values.put(COLUMN_COST_FOOD_ITEM, foodItem.getFoodItemsCost());
            values.put(COLUMN_COLOR_FOOD_ITEM, foodItem.getColor());
            values.put(COLUMN_ICON_FOOD_ITEM, foodItem.getIcon());
            values.put(COLUMN_SELLING_FOOD_ITEM, foodItem.getSelling());
            values.put(COLUMN_UNIT_FOOD_ITEM, foodItem.getUnit().getId());
            int result = db.update(TABLE_FOOD_ITEM, values, COLUMN_ID_FOOD_ITEM + "=" + String.valueOf(foodItem.getId()), null);
            db.close();
            if (result != -1) return foodItem;
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Xóa 1 món ăn
     *
     * @param foodItem món ăn cần xóa
     * @return số hằng bị xóa
     */
    public int deleteFoodItems(FoodItem foodItem) {
        try {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            db.execSQL("PRAGMA foreign_keys=ON;");
            int result = db.delete(TABLE_FOOD_ITEM, COLUMN_ID_FOOD_ITEM + "=?", new String[]{String.valueOf(foodItem.getId())});
            db.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
