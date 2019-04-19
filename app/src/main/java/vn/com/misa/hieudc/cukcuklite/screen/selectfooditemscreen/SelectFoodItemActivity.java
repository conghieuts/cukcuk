package vn.com.misa.hieudc.cukcuklite.screen.selectfooditemscreen;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.model.FoodItem;
import vn.com.misa.hieudc.cukcuklite.model.Order;
import vn.com.misa.hieudc.cukcuklite.dialog.AddDialog;
import vn.com.misa.hieudc.cukcuklite.screen.checkoutscreen.CheckoutActivity;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.MainActivity;
import vn.com.misa.hieudc.cukcuklite.screen.selectfooditemscreen.adapter.SelectFoodItemAdapter;

/**
 * Created_by: dchieu
 * Created_date: 4/18/2019
 * Activity màn hình chọn món ăn cho đơn hàng
 */
public class SelectFoodItemActivity extends AppCompatActivity implements ISelectFoodView, SelectFoodItemAdapter.OnItemClickListener, View.OnClickListener, AddDialog.IOnAddDialogSave {
    RecyclerView mRecyclerView;
    ISelectFoodPresenter mISelectFoodPresenter;
    ArrayList<FoodItem> mFoodItemArrayList;
    TextView tvNumOfTable, tvNumOfPeople, tvTotalBill, tvSave, tvCollectMoney;
    View mCurrentViewClicked;
    FoodItem mCurrentFoodItem;
    Boolean mIsNew;

    /**
     * Xác định dialog cần hiển thị
     */
    enum DialogTypes {
        AMOUNT, TABLE, PEOPLE
    }

    DialogTypes mDialogTypes;
    Order mOrder;
    boolean mIsStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_select_food_item);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_normal);

            try {
                mIsStart = getIntent().getSerializableExtra("isStart") == null;
                mOrder = (Order) getIntent().getSerializableExtra("orderItem");
                mIsNew = mOrder == null;
                mISelectFoodPresenter = new SelectFoodPresenter(this, mOrder);
            } catch (Exception e) {
                e.printStackTrace();
            }
            initView();
            initListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Ánh xạ và đổ dữ liệu lên view
     */
    private void initView() {
        try {
            mRecyclerView = findViewById(R.id.rv_list_food);
            tvNumOfPeople = findViewById(R.id.tv_num_of_people);
            tvNumOfTable = findViewById(R.id.tv_num_of_table);
            tvTotalBill = findViewById(R.id.tv_total_paid);
            tvSave = findViewById(R.id.tv_save);
            tvCollectMoney = findViewById(R.id.tv_collect_money);
            if (mOrder != null) {
                if (mOrder.getNumberOfTable() > 0)
                    tvNumOfTable.setText(String.valueOf(mOrder.getNumberOfTable()));
                if (mOrder.getNumberOfPeople() > 0)
                    tvNumOfPeople.setText(String.valueOf(mOrder.getNumberOfPeople()));
                tvTotalBill.setText(NumberFormat.getInstance().format(mOrder.getTotalPaid()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initListFood();
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * đăng ký lằng nghe sự kiện click
     */
    private void initListener() {
        try {
            tvNumOfTable.setOnClickListener(this);
            tvNumOfPeople.setOnClickListener(this);
            tvSave.setOnClickListener(this);
            tvCollectMoney.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Hiển thị danh sách món ăn
     */
    private void initListFood() {
        try {
            mISelectFoodPresenter.getAllFoodItems();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            SelectFoodItemAdapter selectFoodItemAdapter = new SelectFoodItemAdapter(mFoodItemArrayList, mOrder);
            mRecyclerView.setAdapter(selectFoodItemAdapter);
            selectFoodItemAdapter.setOnItemClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            try {
                getMenuInflater().inflate(R.menu.menu_collect_money, menu);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    if (!mIsStart)
                        onBackPressed();
                    else {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.action_collect_money:
                startCheckout();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Chuyển màn hinh thanh toán
     */
    private void startCheckout() {
        try {
            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putExtra(CheckoutActivity.INTENT_NAME, mOrder);
            intent.putExtra(CheckoutActivity.INTENT_TYPE, mIsNew);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Thêm món ăn vào đơn khi click
     *
     * @param view     item click
     * @param position vị trí click
     */
    @Override
    public void onFoodItemClick(View view, int position) {
        try {
            mCurrentViewClicked = view;
            mCurrentFoodItem = mFoodItemArrayList.get(position);
            mISelectFoodPresenter.addFoodItem(mCurrentFoodItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Xóa món ăn khỏi đơn khi click vào icon
     *
     * @param view     item view click
     * @param position vị trí click
     */
    @Override
    public void onIconItemClick(View view, int position) {
        try {
            mCurrentViewClicked = view;
            mCurrentFoodItem = mFoodItemArrayList.get(position);
            mISelectFoodPresenter.toggleFoodItem(mCurrentFoodItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Tăng số lượng món ăn trong đơn khi click vào nút +
     *
     * @param view     item view click
     * @param position vị trí trong đanh sách
     */
    @Override
    public void onIncrementClick(View view, int position) {
        try {
            mCurrentViewClicked = view;
            mCurrentFoodItem = mFoodItemArrayList.get(position);
            mISelectFoodPresenter.addFoodItem(mCurrentFoodItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Giảm số lượng món ăn trong đơn khi click vào nút -
     *
     * @param view     item view click
     * @param position vị trí trong đanh sách
     */
    @Override
    public void onDecrementClick(View view, int position) {
        try {
            mCurrentViewClicked = view;
            mCurrentFoodItem = mFoodItemArrayList.get(position);
            mISelectFoodPresenter.removeFoodItem(mCurrentFoodItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Hiển thị dialog khi nhấn vào textview số lượng món ăn
     *
     * @param view     item view click
     * @param position vị trí trong danh sách
     */
    @Override
    public void onAmountItemClick(View view, int position) {
        try {
            mCurrentViewClicked = view;
            mCurrentFoodItem = mFoodItemArrayList.get(position);
            mDialogTypes = DialogTypes.AMOUNT;
            new AddDialog(this,
                    getResources().getString(R.string.text_add_amount),
                    InputType.TYPE_CLASS_NUMBER,
                    "").show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_num_of_table:
                try {
                    mDialogTypes = DialogTypes.TABLE;
                    new AddDialog(this,
                            getResources().getString(R.string.text_add_number_of_table),
                            InputType.TYPE_CLASS_NUMBER,
                            "").show();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_num_of_people:
                try {
                    mDialogTypes = DialogTypes.PEOPLE;
                    new AddDialog(this,
                            getResources().getString(R.string.text_add_number_of_people),
                            InputType.TYPE_CLASS_NUMBER,
                            "").show();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_collect_money:
                startCheckout();
                break;
            case R.id.tv_save:
                try {
                    if (mIsNew)
                        mISelectFoodPresenter.saveOderItem();
                    else mISelectFoodPresenter.updateOrderItem();
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
     * Created_date: 4/18/2019
     * Gán danh sách món ăn sau khi lấy xong
     *
     * @param allFoodItems danh sách món ăn lấy được
     */
    @Override
    public void onGetAllFoodItemsDone(ArrayList<FoodItem> allFoodItems) {
        try {
            mFoodItemArrayList = allFoodItems;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Thực hiện đổ dữ liệu lên view
     *
     * @param amount dữ liệu muốn hiển thị
     */
    private void setAmount(int amount) {
        try {
            TextView tvAmount = mCurrentViewClicked.findViewById(R.id.tv_amount);
            tvAmount.setText(String.valueOf(amount));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Thực hiện hiển thị lại view khi từ trạng thái không có sang có mặt trong đơn hàng
     *
     * @param amount số lượng món ăn
     */
    private void setViewOnAdd(int amount) {
        try {
            setAmount(amount);
            ImageView ivIcon = mCurrentViewClicked.findViewById(R.id.iv_icon);
            ivIcon.setImageResource(R.drawable.ic_selected);
            mCurrentViewClicked.findViewById(R.id.ll_menu_select_item).setBackgroundResource(R.drawable.selector_background_second);
            mCurrentViewClicked.findViewById(R.id.ll_select_amount).setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Gán lại đối tượng đơn hàng khi có cập nhật
     *
     * @param order đối tượng đon hàng
     */
    @Override
    public void onItemUpdate(Order order) {
        try {
            mOrder = order;
            tvTotalBill.setText(NumberFormat.getInstance().format(order.getTotalPaid()));
            if (order.getAmount(mCurrentFoodItem) > 0)
                setViewOnAdd(order.getAmount(mCurrentFoodItem));
            else setViewOnRemove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Đổ lại dữ liệu số bàn lên view
     *
     * @param order đối tượng đơn hàng sau khi được cập nhật
     */
    @Override
    public void onNumberOfTableUpdate(Order order) {
        try {
            mOrder = order;
            tvNumOfTable.setText(String.valueOf(order.getNumberOfTable()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Đổ lại dữ liệu số người lên view
     *
     * @param order đối tượng đơn hàng sau khi được cập nhật
     */
    @Override
    public void onNumberOfPeopleUpdate(Order order) {
        try {
            mOrder = order;
            tvNumOfPeople.setText(String.valueOf(order.getNumberOfPeople()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Hiển thị thông báo lỗi khi không thêm món ăn vào hóa đơn
     */
    @Override
    public void onDataError() {
        try {
            Toast.makeText(this, "Hãy thêm ít nhất 1 món ăn", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Hiển thị thông báo sau khi cập nhật món ăn
     *
     * @param message nội dung thông báo
     */
    @Override
    public void onUpdateSuccess(String message) {
        try {
            Intent intent = new Intent();
            intent.setAction(MainActivity.ACTION_REFRESH);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if (!mIsStart)
                finish();
            else {
                startActivity(new Intent(this, MainActivity.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Thực hiện hiển thị lại view khi 1 món ăn bị loại khỏi đơn
     */
    private void setViewOnRemove() {
        try {
            ImageView ivIcon = mCurrentViewClicked.findViewById(R.id.iv_icon);
            AssetManager assetManager = getAssets();
            InputStream ims = assetManager.open(getResources().getString(R.string.asset_folder) + "/" + mCurrentFoodItem.getIcon());
            Drawable d = Drawable.createFromStream(ims, null);
            ivIcon.setImageDrawable(d);
            mCurrentViewClicked.findViewById(R.id.ll_menu_select_item).setBackgroundResource(R.drawable.selector_border_background_white);
            mCurrentViewClicked.findViewById(R.id.ll_select_amount).setVisibility(View.GONE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Phương thúc nhận dữ liệu khi đóng dialog thêm
     *
     * @param data dữ liệu nhận được
     */
    @Override
    public void onAddDialogSave(String data) {
        switch (mDialogTypes) {
            case AMOUNT:
                try {
                    mISelectFoodPresenter.addFoodItemWithAmount(mCurrentFoodItem, Integer.parseInt(data));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            case TABLE:
                try {
                    mISelectFoodPresenter.setNumberOfTable(Integer.parseInt(data));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            case PEOPLE:
                try {
                    mISelectFoodPresenter.setNumberOfPeople(Integer.parseInt(data));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


}
