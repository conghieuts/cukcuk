package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen;

public interface IReportPresenter {
    void getReportAllTime(IReportView iReportView);

    void getReportByTime(IReportView iReportView, int position);

    void getListTime(IReportView iReportView, int position);
}
