package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen;

import java.util.ArrayList;

public interface IReportView {
    void getReportAllTimeDone(ArrayList<ArrayList<Long>> result);

    void getReportByTimeDone(ArrayList<Long> result);

    void getListTimeDone(ArrayList<Long> listTime);
}
