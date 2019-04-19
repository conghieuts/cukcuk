package vn.com.misa.hieudc.cukcuklite.screen.reportdetailscreen;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.database.BillDatabaseManager;
import vn.com.misa.hieudc.cukcuklite.model.CheckoutItem;

public class ReportDetailPresenter implements IReportDetailPresenter {
    private BillDatabaseManager mBillDatabaseManager;
    private IReportDetailView mIReportDetailView;
    private long startTime;
    private long endTime;

    public ReportDetailPresenter(IReportDetailView IReportDetailView, long startTime, long endTime) {
        mBillDatabaseManager = new BillDatabaseManager();
        mIReportDetailView = IReportDetailView;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public void getReportDetail() {
        ArrayList<CheckoutItem> checkoutItems = mBillDatabaseManager.getReportInTime(startTime, endTime);
        mIReportDetailView.getReportDone(checkoutItems);
    }



}
