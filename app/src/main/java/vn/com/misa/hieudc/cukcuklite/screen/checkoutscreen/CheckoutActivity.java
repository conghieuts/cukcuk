package vn.com.misa.hieudc.cukcuklite.screen.checkoutscreen;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.config.AppContext;
import vn.com.misa.hieudc.cukcuklite.dialog.AddDialog;
import vn.com.misa.hieudc.cukcuklite.dialog.ConfirmDialog;
import vn.com.misa.hieudc.cukcuklite.model.Bill;
import vn.com.misa.hieudc.cukcuklite.model.Order;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.MainActivity;
import vn.com.misa.hieudc.cukcuklite.screen.checkoutscreen.adapter.CheckoutItemAdapter;
import vn.com.misa.hieudc.cukcuklite.screen.selectfooditemscreen.SelectFoodItemActivity;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Activity cho màn hình thanh toán
 */
public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, ICheckoutView, AddDialog.IOnAddDialogSave, ConfirmDialog.IOnConfirm {
    public static final String INTENT_NAME = "ORDER";
    public static final String INTENT_TYPE = "IS_NEW";
    private Bill mBill;
    private RecyclerView mRecyclerView;
    private ICheckoutPresenter mICheckoutPresenter;
    private Boolean mIsNew;
    private TextView tvTotalPaid, tvReceive, tvRefund, tvTable, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_normal);
        try {
            Order order = (Order) getIntent().getSerializableExtra(INTENT_NAME);
            mIsNew = getIntent().getBooleanExtra(INTENT_TYPE, false);
            if (order != null) {
                mBill = new Bill(order);
            }
            mICheckoutPresenter = new CheckoutPresenter(this, mBill);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initView();
        initListener();
    }

    private void initListener() {
        try {
            findViewById(R.id.rl_receive).setOnClickListener(this);
            findViewById(R.id.tv_finish).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        try {
            tvTotalPaid = findViewById(R.id.tv_total_paid);
            tvReceive = findViewById(R.id.tv_receive);
            tvRefund = findViewById(R.id.tv_refund);
            tvTable = findViewById(R.id.tv_table);
            tvDate = findViewById(R.id.tv_date);
            if (mBill != null) {
                if (mBill.getNumber() > 0) {
                    findViewById(R.id.tv_title_table).setVisibility(View.VISIBLE);
                    tvTable.setVisibility(View.VISIBLE);
                    tvTable.setText(String.valueOf(mBill.getOrder().getNumberOfTable()));
                }
                SimpleDateFormat dateFormatPattern = new SimpleDateFormat("dd/MM/yyyy (hh:mm aa)");
                String dateString = dateFormatPattern.format(mBill.getTime());
                tvDate.setText(dateString);
                tvTotalPaid.setText(NumberFormat.getInstance().format(mBill.getCharge()));
                tvReceive.setText(NumberFormat.getInstance().format(mBill.getReceive()));
                tvRefund.setText(NumberFormat.getInstance().format(mBill.getReceive() - mBill.getCharge()));
                initListCheckoutItem();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Hiển thị danh sách món ăn trong hóa đơn
     */
    private void initListCheckoutItem() {
        try {
            mRecyclerView = findViewById(R.id.rv_order_item);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            CheckoutItemAdapter checkoutItemAdapter = new CheckoutItemAdapter(mBill.getCheckoutItemArrayList());
            mRecyclerView.setAdapter(checkoutItemAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_done, menu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case android.R.id.home:
                    try {
                        onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.action_done:
                    try {
                        checkout();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_finish:
                try {
                    checkout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_receive:
                try {
                    new AddDialog(this,
                            getResources().getString(R.string.text_add_number_of_receive),
                            InputType.TYPE_CLASS_NUMBER,
                            "").show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Gọi thao tác thanh toán
     */
    private void checkout() {
        mICheckoutPresenter.preCheckout(mIsNew);
    }

    @Override
    public void checkoutDone() {
        try {
            Toast.makeText(this, "Đã thu tiền thành công. Vui lòng chọn món mới.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(MainActivity.ACTION_REFRESH);
            LocalBroadcastManager.getInstance(AppContext.getInstance()).sendBroadcast(intent);
            startActivity(new Intent(this, SelectFoodItemActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("isStart", true));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSetReceiveDone(Bill bill) {
        try {
            mBill = bill;
            tvReceive.setText(NumberFormat.getInstance().format(mBill.getReceive()));
            tvRefund.setText(NumberFormat.getInstance().format(mBill.getReceive() - mBill.getCharge()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Hiện đialog xác nhận thanh toán tiền nhỏ hơn số cần thành toán
     */
    @Override
    public void showConfirmDialog() {
        try {
            ConfirmDialog confirmDialog = new ConfirmDialog(this, this, "Thanh toán", "Tiền khách đưa nhỏ hơn Số tiền phải trả. Bạn có chắc chắn muốn thu tiền không?");
            confirmDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Nhận dữ liệu từ dialog thêm số tiền nhận
     * @param data số tiền nhận
     */
    @Override
    public void onAddDialogSave(String data) {
        try {
            mICheckoutPresenter.setReceive(Integer.valueOf(data));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Xác nhận thanh toán
     */
    @Override
    public void onConfirm() {
        try {
            mICheckoutPresenter.checkout(mIsNew);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
