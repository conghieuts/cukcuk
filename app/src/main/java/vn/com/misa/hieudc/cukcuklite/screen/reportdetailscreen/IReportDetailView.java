package vn.com.misa.hieudc.cukcuklite.screen.reportdetailscreen;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.model.Bill;
import vn.com.misa.hieudc.cukcuklite.model.CheckoutItem;

interface IReportDetailView {
    void getReportDone(ArrayList<CheckoutItem> checkoutItems);
}
