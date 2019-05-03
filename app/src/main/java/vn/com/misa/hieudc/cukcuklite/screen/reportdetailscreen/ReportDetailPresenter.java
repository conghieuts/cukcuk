package vn.com.misa.hieudc.cukcuklite.screen.reportdetailscreen;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.database.BillDatabaseManager;
import vn.com.misa.hieudc.cukcuklite.model.CheckoutItem;

/**
 * Created_by: dchieu
 * Created_date: 4/19/2019
 * Lớp logic cho màn hình báo cáo theo món ăn
 */
public class ReportDetailPresenter implements IReportDetailPresenter {
    private BillDatabaseManager mBillDatabaseManager;
    private IReportDetailView mIReportDetailView;
    private long mStartTime;
    private long mEndTime;

    public ReportDetailPresenter(IReportDetailView IReportDetailView, long startTime, long endTime) {
        mBillDatabaseManager = new BillDatabaseManager();
        mIReportDetailView = IReportDetailView;
        mStartTime = startTime;
        mEndTime = endTime;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/19/2019
     * Lấy danh sách món ăn đã thanh toán
     */
    @Override
    public void getReportDetail() {
        ArrayList<CheckoutItem> checkoutItems = mBillDatabaseManager.getReportInTime(mStartTime, mEndTime);
        mIReportDetailView.getReportDone(checkoutItems);
    }



}
