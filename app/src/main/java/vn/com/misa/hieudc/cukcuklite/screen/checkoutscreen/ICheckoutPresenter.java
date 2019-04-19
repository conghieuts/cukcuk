package vn.com.misa.hieudc.cukcuklite.screen.checkoutscreen;

interface ICheckoutPresenter {
    void checkout(boolean isNewOrder);

    void setReceive(Integer valueOf);

    void preCheckout(boolean isNew);
}
