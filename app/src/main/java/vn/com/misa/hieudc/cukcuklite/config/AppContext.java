package vn.com.misa.hieudc.cukcuklite.config;

import android.app.Application;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Lấy context ứng dụng
 */
public class AppContext extends Application {
    private static AppContext ourInstance;

    public static AppContext getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
    }
}
