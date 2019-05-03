package vn.com.misa.hieudc.cukcuklite.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import vn.com.misa.hieudc.cukcuklite.config.AppContext;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Đối tượng thao tác với cơ sở dữ liệu
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private String DB_PATH = "/data/data/vn.com.misa.hieudc.cukcuklite/databases/";

    static final String DATABASE_NAME = "Db";
    static final int VERSION = 2;

    static final String TABLE_FOOD_ITEM = "FoodItems";
    static final String COLUMN_FOOD_ITEM_ID = "food_id";
    static final String COLUMN_FOOD_ITEM_NAME = "food_name";
    static final String COLUMN_FOOD_ITEM_COST = "food_cost";
    static final String COLUMN_FOOD_ITEM_SELLING = "food_selling";
    static final String COLUMN_FOOD_ITEM_COLOR = "food_color";
    static final String COLUMN_FOOD_ITEM_ICON = "food_icon";
    static final String COLUMN_FOOD_ITEM_FR_UNIT_ID = "food_fr_unit_id";

    static final String TABLE_UNIT_ITEM = "UnitItems";
    static final String COLUMN_UNIT_ITEM_ID = "unit_id";
    static final String COLUMN_UNIT_ITEM_NAME = "unit_name";

    static final String TABLE_ORDER = "OrderItems";
    static final String COLUMN_ORDER_ID = "order_id";
    static final String COLUMN_ORDER_NUMBER_OF_TABLE = "order_number_of_table";
    static final String COLUMN_ORDER_NUMBER_OF_PEOPLE = "order_number_of_people";
    static final String COLUMN_ORDER_STATUS = "order_status";

    static final String TABLE_ORDER_DETAIL = "FoodOrders";
    static final String COLUMN_ORDER_DETAIL_FR_ORDER_ID = "order_detail_fr_order_id";
    static final String COLUMN_ORDER_DETAIL_FR_FOOD_ID = "order_detail_fr_food_id";
    static final String COLUMN_ORDER_DETAIL_FOOD_NAME = "order_detail_food_name";
    static final String COLUMN_ORDER_DETAIL_UNIT_ID = "order_detail_unit_id";
    static final String COLUMN_ORDER_DETAIL_UNIT_NAME = "order_detail_unit_name";
    static final String COLUMN_ORDER_DETAIL_AMOUNT = "order_detail_amount";
    static final String COLUMN_ORDER_DETAIL_TOTAL_PAID = "order_detail_total_paid";


    static final String TABLE_BILL = "Bills";
    static final String COLUMN_BILL_ID = "bill_id";
    static final String COLUMN_BILL_NUMBER = "bill_number";
    static final String COLUMN_BILL_CHARGE = "bill_charge";
    static final String COLUMN_BILL_RECEIVE = "bill_receive";
    static final String COLUMN_BILL_TIME = "bill_time";
    static final String COLUMN_BILL_FR_ORDER_ID = "bill_fr_order_id";

    private static final DatabaseHelper ourInstance = new DatabaseHelper(AppContext.getInstance());

    public static DatabaseHelper getInstance() {
        return ourInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        boolean dbExist = checkDatabase();
        if (!dbExist) {
            copyDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String script = "CREATE TABLE " + TABLE_FOOD_ITEM + "("
                + COLUMN_FOOD_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FOOD_ITEM_NAME + " TEXT,"
                + COLUMN_FOOD_ITEM_COST + " INTEGER," + COLUMN_FOOD_ITEM_SELLING + " TEXT,"
                + COLUMN_FOOD_ITEM_COLOR + " TEXT," + COLUMN_FOOD_ITEM_ICON + " TEXT,"
                + COLUMN_FOOD_ITEM_FR_UNIT_ID + " INTEGER, FOREIGN KEY(" + COLUMN_FOOD_ITEM_FR_UNIT_ID + ") REFERENCES "
                + TABLE_UNIT_ITEM + "(" + COLUMN_UNIT_ITEM_ID + "))";
        db.execSQL(script);

        String script2 = "CREATE TABLE " + TABLE_UNIT_ITEM + "("
                + COLUMN_UNIT_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_UNIT_ITEM_NAME + " TEXT)";
        db.execSQL(script2);

        String script3 = "CREATE TABLE " + TABLE_ORDER + "("
                + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ORDER_NUMBER_OF_TABLE + " INTEGER,"
                + COLUMN_ORDER_NUMBER_OF_PEOPLE + " INTEGER," + COLUMN_ORDER_STATUS + " TEXT)";
        db.execSQL(script3);

        String script4 = "CREATE TABLE " + TABLE_ORDER_DETAIL + "("
                + COLUMN_ORDER_DETAIL_FR_FOOD_ID + " INTEGER,"
                + COLUMN_ORDER_DETAIL_FR_ORDER_ID + " INTEGER,"
                + COLUMN_ORDER_DETAIL_AMOUNT + " INTEGER, "
                + COLUMN_ORDER_DETAIL_FOOD_NAME + " TEXT, "
                + COLUMN_ORDER_DETAIL_UNIT_ID + " INTEGER, "
                + COLUMN_ORDER_DETAIL_UNIT_NAME + " TEXT, "
                + COLUMN_ORDER_DETAIL_TOTAL_PAID + " INTEGER, "
                + "PRIMARY KEY(" + COLUMN_ORDER_DETAIL_FR_FOOD_ID + "," + COLUMN_ORDER_DETAIL_FR_ORDER_ID + "), "
                + "FOREIGN KEY(" + COLUMN_ORDER_DETAIL_FR_FOOD_ID + ") REFERENCES " + TABLE_FOOD_ITEM + "(" + COLUMN_FOOD_ITEM_ID + "), "
                + "FOREIGN KEY(" + COLUMN_ORDER_DETAIL_FR_ORDER_ID + ") REFERENCES " + TABLE_ORDER + "(" + COLUMN_ORDER_ID + ") ON DELETE CASCADE)";
        db.execSQL(script4);

        String script5 = "CREATE TABLE " + TABLE_BILL + "("
                + COLUMN_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_BILL_NUMBER + " INTEGER, "
                + COLUMN_BILL_CHARGE + " INTEGER,"
                + COLUMN_BILL_RECEIVE + " INTEGER,"
                + COLUMN_BILL_TIME + " INTEGER, "
                + COLUMN_BILL_FR_ORDER_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_BILL_FR_ORDER_ID + ") REFERENCES " + TABLE_ORDER + "(" + COLUMN_ORDER_ID + "))";
        db.execSQL(script5);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void copyDatabase() {
        try {
            AssetManager dirPath = AppContext.getInstance().getAssets();

            InputStream myInput = dirPath.open("database/" + DATABASE_NAME);

            String outFileName = DB_PATH + DATABASE_NAME;

            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkDatabase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            File dbFile = new File(myPath);
            checkdb = dbFile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist!");
        }

        return checkdb;
    }
}
