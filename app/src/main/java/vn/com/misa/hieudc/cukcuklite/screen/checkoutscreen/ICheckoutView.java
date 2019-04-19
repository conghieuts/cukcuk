package vn.com.misa.hieudc.cukcuklite.screen.checkoutscreen;

import vn.com.misa.hieudc.cukcuklite.model.Bill;

interface ICheckoutView {
    void checkoutDone();

    void onSetReceiveDone(Bill bill);

    void showConfirmDialog();
}
