package vn.com.misa.hieudc.cukcuklite.utils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created_by: dchieu
 * Created_date: 4/18/2019
 * Lớp hỗ trợ lấy danh sách thời gian
 */
public class GetTime {

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Đưa thời gian về 0 giờ đêm
     * @param calendar đối tượng muốn cài đặt lại thời gian
     */
    private static void resetTime(Calendar calendar) {
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     *
     * lấy thòi gian bắt đầu và kết thúc ngày hôm nay
     * @return danh sách gồm thời gian bắt đầu và thời gian kết thúc ngày hôm nay
     */
    public static ArrayList<Long> getTimeInToday() {
        ArrayList<Long> result = new ArrayList<>();
        try {
            Calendar newTime = (Calendar) Calendar.getInstance().clone();
            result.add(newTime.getTimeInMillis());
            resetTime(newTime);
            result.add(0, newTime.getTimeInMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * lấy thời gian bắt đàu và kết thức ngày hôm trước
     * @return danh sách gồm thời gian bắt đầu và thời gian kết thúc ngày hôm trước
     */
    public static ArrayList<Long> getTimeInYesterday() {
        ArrayList<Long> result = new ArrayList<>();
        try {
            Calendar newTime = (Calendar) Calendar.getInstance().clone();
            resetTime(newTime);
            result.add(newTime.getTimeInMillis() - 1);
            result.add(0, newTime.getTimeInMillis() - 24 * 60 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Lấy danh sách thời gian bắt dầu từng ngày trong tuần dến thời điểm hiện tại
     * @return danh sách thời gian bắt dầu từng ngày trong tuần dến thời điểm hiện tại
     */
    public static ArrayList<Long> getTimeInWeek() {
        ArrayList<Long> result = new ArrayList<>();
        try {
            Calendar calendar = Calendar.getInstance();
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            Calendar newTime = (Calendar) Calendar.getInstance().clone();
            result.add(newTime.getTimeInMillis());
            resetTime(newTime);
            for (int i = Calendar.MONDAY; i <= dayOfWeek; i++) {
                result.add(0, newTime.getTimeInMillis() - 24 * 60 * 60 * 1000 * (i - Calendar.MONDAY));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Lấy danh sách thời gian bắt dầu từng ngày trong tháng dến thời điểm hiện tại
     * @return danh sách thời gian bắt dầu từng ngày trong tháng dến thời điểm hiện tại
     */
    public static ArrayList<Long> getTimeInMonth() {
        Calendar calendar = Calendar.getInstance();
        ArrayList<Long> result = null;
        try {
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            result = new ArrayList<>();
            Calendar newTime = (Calendar) Calendar.getInstance().clone();
            result.add(newTime.getTimeInMillis());
            resetTime(newTime);
            for (int i = 1; i <= dayOfMonth; i++) {
                result.add(0, newTime.getTimeInMillis() - 24 * 60 * 60 * 1000 * (i - 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Lấy danh sách thời gian bắt dầu từng tháng trong năm dến thời điểm hiện tại
     * @return danh sách thời gian bắt dầu từng tháng trong năm dến thời điểm hiện tại
     */
    public static ArrayList<Long> getTimeInYear() {
        Calendar calendar = Calendar.getInstance();
        ArrayList<Long> result = null;
        try {
            int monthOfYear = calendar.get(Calendar.MONTH);
            result = new ArrayList<>();
            Calendar newTime = (Calendar) Calendar.getInstance().clone();
            result.add(newTime.getTimeInMillis());
            resetTime(newTime);
            newTime.set(Calendar.DAY_OF_MONTH, 1);
            for (int i = monthOfYear - 1 ; i >= 0; i--) {
                newTime.set(Calendar.MONTH, i);
                result.add(0, newTime.getTimeInMillis());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
