package vn.com.misa.hieudc.cukcuklite.screen.addfooditemscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;

import vn.com.misa.hieudc.cukcuklite.dialog.ConfirmDialog;
import vn.com.misa.hieudc.cukcuklite.model.UnitItem;
import vn.com.misa.hieudc.cukcuklite.screen.addfooditemscreen.dialog.ColorDialog;
import vn.com.misa.hieudc.cukcuklite.screen.addfooditemscreen.dialog.IconDialog;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.MainActivity;
import vn.com.misa.hieudc.cukcuklite.model.FoodItem;
import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.screen.selectunitscreen.SelectUnitActivity;
import vn.com.misa.hieudc.cukcuklite.dialog.AddDialog;

/**
 * Created_by: dchieu
 * Created_date: 3/27/2019
 * Activity Thực hiện thêm món ăn
 */
public class AddFoodItemsActivity extends AppCompatActivity implements View.OnClickListener, IconDialog.IIconDialogSelected, ColorDialog.IColorPicked, IAddFoodItemView, AddDialog.IOnAddDialogSave, ConfirmDialog.IOnConfirm {
    public static final int REQUEST_CODE = 1001;
    private boolean mIsCreate;
    private FoodItem mFoodItem;
    private EditText etFoodItemName;
    private TextView tvCost, tvUnit;
    private Button btnSave, btnUpdate, btnDelete;
    private ViewGroup llStatus, llUpdate, rlCost, rlUnit;
    private ImageView ivColor, ivIcon;
    private CheckBox cbSelling;
    private IAddFoodItemsPresenter mIAddFoodItemsPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_add_food_items);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_normal);
            mFoodItem = (FoodItem) getIntent().getSerializableExtra("foodItem");
            mIAddFoodItemsPresenter = new AddFoodItemsPresenter(this);
            if (mFoodItem != null) {
                mIsCreate = false;
            } else {
                mIsCreate = true;
                mIAddFoodItemsPresenter.createNewFoodItem();
            }
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Setup view
     */
    private void initView() {
        try {
            etFoodItemName = findViewById(R.id.edt_food_items_name);
            tvCost = findViewById(R.id.tv_food_items_cost);
            tvUnit = findViewById(R.id.tv_food_items_unit);
            btnSave = findViewById(R.id.btn_save);
            btnUpdate = findViewById(R.id.btn_update);
            btnDelete = findViewById(R.id.btn_delete);
            llStatus = findViewById(R.id.layout_status);
            llUpdate = findViewById(R.id.layout_update);
            rlCost = findViewById(R.id.layout_cost);
            rlUnit = findViewById(R.id.layout_unit);
            ivColor = findViewById(R.id.iv_color);
            ivIcon = findViewById(R.id.iv_icon);
            cbSelling = findViewById(R.id.cb_selling);
            cbSelling.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) mFoodItem.setSelling(FoodItem.STOP_SELLING);
                    else mFoodItem.setSelling(FoodItem.SELLING);
                }
            });
            btnSave.setOnClickListener(this);
            btnUpdate.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
            rlCost.setOnClickListener(this);
            rlUnit.setOnClickListener(this);
            ivColor.setOnClickListener(this);
            ivIcon.setOnClickListener(this);
            if (!mIsCreate) {
                btnSave.setVisibility(View.INVISIBLE);
                llUpdate.setVisibility(View.VISIBLE);
                llStatus.setVisibility(View.VISIBLE);
                etFoodItemName.setText(mFoodItem.getFoodItemsName());
                etFoodItemName.requestFocus(0);
                TextView tvToolbar = findViewById(R.id.tv_toolbar_title);
                tvToolbar.setText(getResources().getString(R.string.update_food_item));
            }

            setSelling(mFoodItem.getSelling());
            setCost(mFoodItem.getFoodItemsCost());
            setIcon(mFoodItem.getIcon());
            setColor(mFoodItem.getColor());
            setUnit(mFoodItem.getUnit());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_save, menu);
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
                case R.id.action_save:
                    try {
                        mFoodItem.setFoodItemsName(etFoodItemName.getText().toString());
                        if (mIsCreate) mIAddFoodItemsPresenter.saveFoodItem(mFoodItem);
                        else mIAddFoodItemsPresenter.updateFoodItem(mFoodItem);
                        //TODO
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
        try {
            switch (v.getId()) {
                case R.id.btn_save:
                    try {
                        mFoodItem.setFoodItemsName(etFoodItemName.getText().toString());
                        mIAddFoodItemsPresenter.saveFoodItem(mFoodItem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_update:
                    try {
                        mFoodItem.setFoodItemsName(etFoodItemName.getText().toString());
                        mIAddFoodItemsPresenter.updateFoodItem(mFoodItem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_delete:
                    try {
                        showConfirmDeleteDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.layout_cost:
                    try {
                        String currentValue = mFoodItem.getFoodItemsCost() == 0?"":String.valueOf(mFoodItem.getFoodItemsCost());
                        new AddDialog(this, getResources().getString(R.string.text_add_cost), InputType.TYPE_CLASS_NUMBER, currentValue).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.layout_unit:
                    try {
                        Intent intent = new Intent(this, SelectUnitActivity.class);
                        if(mFoodItem.getUnit() != null ){
                            intent.putExtra("unit_id", ((int) mFoodItem.getUnit().getId()));
                        }
                        startActivityForResult(intent, REQUEST_CODE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //TODO
                    break;
                case R.id.iv_color:
                    try {
                        new ColorDialog(AddFoodItemsActivity.this, mFoodItem.getColor()).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //TODO
                    break;
                case R.id.iv_icon:
                    try {
                        new IconDialog(AddFoodItemsActivity.this).show();
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
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Hiển thị dialog xác nhận xóa
     */
    private void showConfirmDeleteDialog() {
        try {
            ConfirmDialog confirmDialog = new ConfirmDialog(this, this, "Xóa món ăn", "Bạn có chắc muốn xóa không?");
            confirmDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Setup giá bán lên View
     *
     * @param foodItemsCost giá bán
     */
    private void setCost(long foodItemsCost) {
        try {
            tvCost.setText(NumberFormat.getInstance().format(foodItemsCost));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Setup trạng thái bán lên View
     *
     * @param status trạng thái
     */
    private void setSelling(String status) {
        try {
            cbSelling.setChecked(!status.equals(FoodItem.SELLING));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Nhận icon từ dialog
     *
     * @param fileName tên file
     */
    @Override
    public void iconPicked(String fileName) {
        mFoodItem.setIcon(fileName);
        setIcon(fileName);
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Đưa icon lên view
     *
     * @param fileName tên file icon
     */
    private void setIcon(String fileName) {
        try {
            AssetManager assetManager = getAssets();
            InputStream ims = assetManager.open(getResources().getString(R.string.asset_folder) + "/" + fileName);
            Drawable d = Drawable.createFromStream(ims, null);
            ivIcon.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Nhận màu từ dialog
     *
     * @param color màu nhận được
     */
    @Override
    public void colorPicked(String color) {
        try {
            mFoodItem.setColor(color);
            setColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Đưa màu lên view
     *
     * @param color màu món ăn
     */
    @SuppressLint("NewApi")
    private void setColor(String color) {
        try {
            ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(color));
            ivIcon.setBackgroundTintList(colorStateList);
            ivColor.setBackgroundTintList(colorStateList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Khi thực hiện thêm món thành công, gửi broadcast và finish
     */
    @Override
    public void onAddSuccess() {
        try {
            Intent intent = new Intent();
            intent.setAction(MainActivity.ACTION_REFRESH);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            Toast.makeText(this, getString(R.string.toast_add_success), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Khi thêm món thất bài, Hiển thị lỗi
     */
    @Override
    public void onAddFail() {
        Toast.makeText(this, getString(R.string.toast_add_fail), Toast.LENGTH_SHORT).show();
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Khi thực hiện update thành công, hiển thị thông báo, gửi broadcast và finish
     *
     * @param foodItem món ăn được update
     */
    @Override
    public void onUpdateSuccess(FoodItem foodItem) {
        try {
            Intent intent = new Intent();
            intent.setAction(MainActivity.ACTION_REFRESH);
            Bundle bundle = new Bundle();
            bundle.putSerializable("foodItem", foodItem);
            intent.putExtras(bundle);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            Toast.makeText(this, getString(R.string.toast_edit_success), Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Hiển thị thông báo khi cập nhật thất bại
     */
    @Override
    public void onUpdateFail() {
        try {
            Toast.makeText(this, getString(R.string.toast_edit_fail), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Hiện thị lỗi khi không nhập dữ liệu
     */
    @Override
    public void onDataError() {
        try {
            Toast.makeText(this, getString(R.string.toast_error_name), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Hiển thị thông báo khi xóa và gửi broadcast cập nhật
     */
    @Override
    public void onDeleteSuccess() {
        try {
            Toast.makeText(this, getString(R.string.toast_delete_success), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(MainActivity.ACTION_REFRESH);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDeleteFail() {
        Toast.makeText(this, getString(R.string.toast_delete_fail_by_transaction), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNewFoodItemCreated(FoodItem foodItem) {
        try {
            mFoodItem = foodItem;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Nhận dữ liệu đơn vị tính từ activity chọn đơn vị tính
     *
     * @param requestCode
     * @param resultCode
     * @param intent dữ liệu đơn vị tính
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if(intent == null) return;
        try {
            if (requestCode == REQUEST_CODE && resultCode == REQUEST_CODE) {
                UnitItem unitItem = (UnitItem) intent.getSerializableExtra("unit");
                mFoodItem.setUnit(unitItem);
                setUnit(unitItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * hiển thị đơn vị tính
     *
     * @param unitItem đơn vị tính
     */
    private void setUnit(UnitItem unitItem) {
        if(unitItem == null) return;
        try {
            tvUnit.setText(unitItem.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Nhận dữ liệu từ dialog nhập giá bán
     *
     * @param data giá bán nhận từ dialog
     */
    @Override
    public void onAddDialogSave(String data) {
        try {
            tvCost.setText(data);
            mFoodItem.setFoodItemsCost(Long.valueOf(data));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfirm() {
        mIAddFoodItemsPresenter.deleteFoodItem(mFoodItem);
    }
}
