package vn.com.misa.hieudc.cukcuklite.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.model.UnitItem;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Đối tượng quản lý thao tác dữ liệu đơn vị tính
 */
public class UnitItemDatabaseManager {
    private DatabaseHelper mDatabaseHelper;

    public UnitItemDatabaseManager() {
        mDatabaseHelper = DatabaseHelper.getInstance();
    }

    private final String TABLE_NAME = DatabaseHelper.TABLE_UNIT_ITEM;
    private final String COLUMN_ID = DatabaseHelper.COLUMN_UNIT_ITEM_ID;
    private final String COLUMN_NAME = DatabaseHelper.COLUMN_UNIT_ITEM_NAME;

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Tạo đối tượng đơn vị tính
     *
     * @param cursor chứa thông tin đơn vị tính
     * @return đơn vị tính
     */
    public static UnitItem createUnitItem(Cursor cursor) {
        UnitItem unitItem = null;
        try {
            unitItem = new UnitItem(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_UNIT_ITEM_ID)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_UNIT_ITEM_NAME)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitItem;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Tìm và tạo 1 đơn vị tính
     *
     * @param id id cần tìm
     * @param db đối tượng SQLiteDatabase
     * @return Đơn vị tính tìm được
     */
    public static UnitItem findUnitItem(long id, SQLiteDatabase db) {
        UnitItem unitItem = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_UNIT_ITEM + " WHERE " + DatabaseHelper.COLUMN_UNIT_ITEM_ID + "=?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                unitItem = new UnitItem(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_UNIT_ITEM_ID)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_UNIT_ITEM_NAME)));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitItem;
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Lấy danh sách đơn vị tính
     *
     * @return danh sách đơn vị tính
     */
    public ArrayList<UnitItem> getAllUnitItem() {
        ArrayList<UnitItem> list = new ArrayList<>();
        try {
            String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_NAME;
            SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    UnitItem unitItem = new UnitItem(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    list.add(unitItem);
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
     * Lấy đơn vị tính theo tên
     *
     * @param name tên muốn lấy
     * @return đơn vị tính, không có trả null
     */
    private UnitItem getUnitItemByName(String name) {
        try {
            SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "=?";
            Cursor cursor = db.rawQuery(query, new String[]{name});
            if (cursor.moveToFirst()) {
                return new UnitItem(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Tìm đón vị tính đầu tiên trong csdl
     * @return đơn vị tính
     */
    public UnitItem getUnitItemFirst() {
        try {
            SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME + " LIMIT 1";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                return new UnitItem(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)), cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Thêm đơn vị tính
     *
     * @param unitItem đơn vị tính
     * @return trạng thái thêm thành công, thất bại
     */
    public long addUnitItem(UnitItem unitItem) {
        if (getUnitItemByName(unitItem.getName()) != null) return -1;
        try {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, unitItem.getName());
            long result = db.insert(TABLE_NAME, null, values);
            db.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Cập nhật đơn vị tính
     * @param unitItem đon vị tính cần cập nhật
     */
    public boolean updateUnitItem(UnitItem unitItem) {
        try {
            if (getUnitItemByName(unitItem.getName()) != null) return false;
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, unitItem.getName());
            db.update(TABLE_NAME, values, COLUMN_ID + "=" + String.valueOf(unitItem.getId()), null);
            db.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Xóa 1 đơn vị tính
     *
     * @param unitItem đơn vị tính cần xóa
     */
    public void deleteUnitItem(UnitItem unitItem) {
        try {
            SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
            db.delete(TABLE_NAME, COLUMN_ID + "=" + String.valueOf(unitItem.getId()), null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Thêm các đơn vị tính mặc định
     */
    public void createDefaultIfNeed() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        if (count == 0) {
            String[] list = {"Bao", "Bát", "Cái", "Chén", "Cốc", "Đĩa", "Điếu", "Lạng", "Lon", "Phần", "Phong", "Quả", "Suất", "Tô", "Vỉ"};
            UnitItem unitItem = new UnitItem();
            for (String name : list) {
                unitItem.setName(name);
                this.addUnitItem(unitItem);
            }
        }
    }
}
