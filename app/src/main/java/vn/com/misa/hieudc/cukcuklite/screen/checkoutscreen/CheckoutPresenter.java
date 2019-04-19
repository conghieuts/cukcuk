package vn.com.misa.hieudc.cukcuklite.screen.checkoutscreen;

import vn.com.misa.hieudc.cukcuklite.database.BillDatabaseManager;
import vn.com.misa.hieudc.cukcuklite.model.Bill;
import vn.com.misa.hieudc.cukcuklite.model.Order;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Lớp xử lý logic cho màn hình thanh toán
 */
public class CheckoutPresenter implements ICheckoutPresenter {
    private ICheckoutView mICheckoutView;
    private Bill mBill;
    private BillDatabaseManager mBillDatabaseManager;

    CheckoutPresenter(ICheckoutView ICheckoutView, Bill bill) {
        try {
            mICheckoutView = ICheckoutView;
            mBill = bill;
            mBillDatabaseManager = new BillDatabaseManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Thực hiện thao tác thanh toán
     * @param isNewOrder có là đơn hàng mới chưa có trong csdl hay không
     */
    @Override
    public void checkout(boolean isNewOrder) {
        try {
            mBill.getOrder().setStatus(Order.PAID_STATUS);
            mBillDatabaseManager.addBill(mBill, isNewOrder);
            mICheckoutView.checkoutDone();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Thực hiện cập nhật số tiền đã nhận
     * @param valueOf
     */
    @Override
    public void setReceive(Integer valueOf) {
        try {
            mBill.setReceive(valueOf);
            mICheckoutView.onSetReceiveDone(mBill);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     *
     * Kiểm tra số tiền thanh toán
     * @param isNew
     */
    @Override
    public void preCheckout(boolean isNew) {
        try {
            if(mBill.getCharge() > mBill.getReceive()) mICheckoutView.showConfirmDialog();
            else checkout(isNew);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
