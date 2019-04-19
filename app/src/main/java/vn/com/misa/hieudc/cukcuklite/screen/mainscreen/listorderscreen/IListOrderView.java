package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.listorderscreen;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.model.Order;

public interface IListOrderView {
    void onGetAllOrderDone(ArrayList<Order> allOrders);

    void onActionUpdate(String message);
}
