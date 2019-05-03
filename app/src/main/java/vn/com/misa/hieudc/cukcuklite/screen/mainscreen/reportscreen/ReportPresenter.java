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
    private ArrayList<ArrayList<Long>> mListTime;
    ReportPresenter() {
        mBillDatabaseManager = new BillDatabaseManager();
        mListReport = new ArrayList<>();
        mListTime = new ArrayList<>();
        initData();
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Chuẩn bị dữ liệu
     */
    private void initData() {
        try {
            mListTime.add(GetTime.getTimeInYesterday());
            mListTime.add(GetTime.getTimeInToday());
            mListTime.add(GetTime.getTimeInWeek());
            mListTime.add(GetTime.getTimeInMonth());
            mListTime.add(GetTime.getTimeInYear());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (ArrayList<Long> time : mListTime) {
                mListReport.add(mBillDatabaseManager.getTotalReportByTime(time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     *
     * @param list
     * @return
     */
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
            iReportView.getReportAllTimeDone(mListReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/19/2019
     *
     * @param iReportView
     * @param position
     */
    @Override
    public void getReportByTime(IReportView iReportView, int position) {
        try {
            iReportView.getReportByTimeDone(mListReport.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/19/2019
     *
     * @param iReportView
     * @param position
     */
    @Override
    public void getListTime(IReportView iReportView, int position) {
        try {
            iReportView.getListTimeDone(mListTime.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
