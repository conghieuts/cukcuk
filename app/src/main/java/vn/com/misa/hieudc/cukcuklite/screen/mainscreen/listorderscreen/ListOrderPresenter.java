package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.listorderscreen;

import vn.com.misa.hieudc.cukcuklite.model.Order;
import vn.com.misa.hieudc.cukcuklite.database.OrderDatabaseManager;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Lớp Logic cho màn hình hiển thị danh sách đơn
 */
public class ListOrderPresenter implements IListOrderPresenter {
    private OrderDatabaseManager mOrderDatabaseManager;
    private IListOrderView mIListOrderView;
    ListOrderPresenter(IListOrderView iListOrderView) {
        mOrderDatabaseManager = new OrderDatabaseManager();
        mIListOrderView = iListOrderView;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Lấy danh sách đơn hàng
     */
    @Override
    public void getAllOrder() {
        try {
            mIListOrderView.onGetAllOrderDone(mOrderDatabaseManager.getAllOrders());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Hủy đơn hàng
     * @param order đơn hàng
     */
    @Override
    public void cancelOrder(Order order) {
        try {
            if(mOrderDatabaseManager.deleteOrder(order) > 0){
                mIListOrderView.onActionUpdate("Hủy thành công");
            } else {
                mIListOrderView.onActionUpdate("Hủy không thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
