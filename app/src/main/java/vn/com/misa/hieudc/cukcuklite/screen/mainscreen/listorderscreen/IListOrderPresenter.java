package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.listorderscreen;

import vn.com.misa.hieudc.cukcuklite.model.Order;

public interface IListOrderPresenter {
    void getAllOrder();

    void cancelOrder(Order order);
}
