package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.listorderscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.Objects;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.config.AppContext;
import vn.com.misa.hieudc.cukcuklite.dialog.ConfirmDialog;
import vn.com.misa.hieudc.cukcuklite.model.Order;
import vn.com.misa.hieudc.cukcuklite.screen.checkoutscreen.CheckoutActivity;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.MainActivity;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.listorderscreen.adapter.ListOrderAdapter;
import vn.com.misa.hieudc.cukcuklite.screen.selectfooditemscreen.SelectFoodItemActivity;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Fragment danh sách đơn hàng
 */
public class ListOrderFragment extends Fragment implements IListOrderView, ListOrderAdapter.OnItemClick, ConfirmDialog.IOnConfirm {
    IListOrderPresenter mIListOrderPresenter;
    ArrayList<Order> mOrderArrayList;
    RecyclerView mRecyclerView;
    private int mPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.newInitializerBuilder(getContext());
        try {
            mIListOrderPresenter = new ListOrderPresenter(this);
            initListOrder();
            registerBroadcast();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            mRecyclerView = view.findViewById(R.id.rv_list_order);
            initListOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Đăng ký broadcast cập nhật lại danh sách
     */
    private void registerBroadcast() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(MainActivity.ACTION_REFRESH);
            BroadcastReceiver refreshReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    initListOrder();
                }
            };
            LocalBroadcastManager.getInstance(AppContext.getInstance()).registerReceiver(refreshReceiver,intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Hiển thị danh sách đơn hàng
     */
    private void initListOrder() {
        try {
            mIListOrderPresenter.getAllOrder();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);

            ListOrderAdapter listOrderAdapter = new ListOrderAdapter(mOrderArrayList);
            mRecyclerView.setAdapter(listOrderAdapter);
            listOrderAdapter.setOnItemClick(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Thực hiện chuyển màn hình chi tiết khi 1 đơn đặt hàng được nhấn
     * @param position vị trí trong list
     */
    @Override
    public void onOrderItemClick(int position) {
        try {
            Intent intent = new Intent(getContext(), SelectFoodItemActivity.class);
            intent.putExtra("isStart", false);
            intent.putExtra("orderItem", mOrderArrayList.get(position));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Xóa 1 đơn đặt hàng
     * @param position vị trí trong list
     */
    @Override
    public void onCancel(int position) {
        try {
            mPosition = position;
            ConfirmDialog confirmDialog = new ConfirmDialog(Objects.requireNonNull(getContext()), this, "Hủy đơn hàng", "Bạn có chắc muốn hủy đơn hàng không?");
            confirmDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Thực hiện chuyển màn hình thanh toán đơn
     * @param position vị trí đơn trong list
     */
    @Override
    public void onCollectMoney(int position) {
        try {
            Intent intent = new Intent(getContext(), CheckoutActivity.class);
            intent.putExtra(CheckoutActivity.INTENT_NAME, mOrderArrayList.get(position));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Gán danh sách đơn đặt hàng khi lấy xong từ Presenter
     * @param allOrders danh sách đơn đặt hàng
     */
    @Override
    public void onGetAllOrderDone(ArrayList<Order> allOrders) {
        try {
            mOrderArrayList = allOrders;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Hiển thị thông báo khi thực hiện xong thao tác
     * @param message nội dung thông báo muốn hiển thị
     */
    @Override
    public void onActionUpdate(String message) {
        try {
            Toast.makeText(AppContext.getInstance(), message, Toast.LENGTH_SHORT).show();
            initListOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfirm() {
        mIListOrderPresenter.cancelOrder(mOrderArrayList.get(mPosition));
    }
}
