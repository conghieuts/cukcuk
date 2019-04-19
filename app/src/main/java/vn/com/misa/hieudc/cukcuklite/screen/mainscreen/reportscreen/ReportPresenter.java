package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.database.BillDatabaseManager;
import vn.com.misa.hieudc.cukcuklite.utils.GetTime;

/**
 * Created_by: dchieu
 * Created_date: 4/18/2019
 * Lớp logic cho màn hình báo cáo
 */
public class ReportPresenter implements IReportPresenter {
    private BillDatabaseManager mBillDatabaseManager;
    private ArrayList<ArrayList<Long>> mListReport;
    private ArrayList<ArrayList<Long>> mTime;
    ReportPresenter() {
        mBillDatabaseManager = new BillDatabaseManager();
        mListReport = new ArrayList<>();
        mTime = new ArrayList<>();
        initData();
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     *
     * @param index vị trí trong list
     * @return danh sách thòi gian dạng String
     */
    public ArrayList<String> getTimeString(int index) {
        return convertString(mTime.get(index));
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Chuẩn bị dữ liệu
     */
    private void initData() {
        try {
            mTime.add(GetTime.getTimeInYesterday());
            mTime.add(GetTime.getTimeInToday());
            mTime.add(GetTime.getTimeInWeek());
            mTime.add(GetTime.getTimeInMonth());
            mTime.add(GetTime.getTimeInYear());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (ArrayList<Long> time : mTime) {
                mListReport.add(mBillDatabaseManager.getTotalReportByTime(time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> convertString(ArrayList<Long> list) {
        ArrayList<String> result = new ArrayList<>();
        try {
            for (Long number: list) {
                result.add(number.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Gửi dữ liệu báo cáo
     * @param iReportView nơi nhận
     */
    @Override
    public void getReportAllTime(IReportView iReportView) {
        try {
            iReportView.getReportDone(mListReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
