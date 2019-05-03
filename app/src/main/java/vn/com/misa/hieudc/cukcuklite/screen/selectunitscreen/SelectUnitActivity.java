package vn.com.misa.hieudc.cukcuklite.screen.selectunitscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.screen.addfooditemscreen.AddFoodItemsActivity;
import vn.com.misa.hieudc.cukcuklite.model.UnitItem;
import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.screen.selectunitscreen.adapter.UnitItemAdapter;
import vn.com.misa.hieudc.cukcuklite.dialog.AddDialog;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Activity chọn đơn vị tính
 */
public class SelectUnitActivity extends AppCompatActivity implements View.OnClickListener, AddDialog.IOnAddDialogSave, ISelectUnitView, UnitItemAdapter.OnItemClickListener {
    RecyclerView mRecyclerView;
    ArrayList<UnitItem> mUnitItemArrayList;
    Button btnDone;
    int mSelectedIndex = -1;
    int mUnit_id = -1;
    ISelectUnitPresenter mSelectUnitPresenter;
    Boolean isNew;
    UnitItemAdapter mUnitItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_select_unit);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_normal);
            mUnit_id = getIntent().getIntExtra("unit_id", mUnit_id);
            mSelectUnitPresenter = new SelectUnitPresenter(this);
            mSelectUnitPresenter.getAllUnitItem();
            initUnitList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Hiển thị danh sách đơn vị tính
     */
    private void initUnitList() {
        try {
            btnDone = findViewById(R.id.btn_done);
            mRecyclerView = findViewById(R.id.rv_unit_item);

            btnDone.setOnClickListener(this);

            mUnitItemAdapter = new UnitItemAdapter(mUnitItemArrayList, this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(mUnitItemAdapter);
            for (int i = 0; i < mUnitItemArrayList.size(); i++) {
                if (mUnitItemArrayList.get(i).getId() == mUnit_id) {
                    mSelectedIndex = i;
                    mUnitItemAdapter.setSelectIndex(mSelectedIndex);
                }
            }

            mUnitItemAdapter.setOnItemClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_create, menu);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case android.R.id.home:
                    onBackPressed();
                    break;
                case R.id.action_create:
                    try {
                        isNew = true;
                        new AddDialog(this, getResources().getString(R.string.text_add_unit_item), InputType.TYPE_CLASS_TEXT, "").show();
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
                case R.id.btn_done:
                    try {
                        selectDone(mUnitItemArrayList.get(mSelectedIndex));
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
     * Created_date: 4/1/2019
     * Gửi kết quả chọn về activity khởi tạo
     *
     * @param unitItem unit được chọn
     */
    private void selectDone(UnitItem unitItem) {
        try {
            Intent intent = new Intent();
            intent.putExtra("unit", unitItem);
            setResult(AddFoodItemsActivity.REQUEST_CODE, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Thêm đơn vị tính khi nhận dữ liệu từ dialog
     *
     * @param data tên đơn vị tính
     */
    @Override
    public void onAddDialogSave(String data) {
        try {
            if (isNew)
                mSelectUnitPresenter.createUnit(data);
            else {
                mSelectUnitPresenter.editUnit(mUnitItemArrayList.get(mSelectedIndex), data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     *
     * @param unitItems danh sách đơn vị tính
     */
    @Override
    public void onGetUnitItemListDone(ArrayList<UnitItem> unitItems) {
        try {
            mUnitItemArrayList = unitItems;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Gửi kết quả chọn khi tạo thành công
     *
     * @param unitItem đon vị tính tạo thành công
     */
    @Override
    public void onSelectSuccess(UnitItem unitItem) {
        try {
            selectDone(unitItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Hiển thị thông báo lỗi khi tạo
     */
    @Override
    public void onCreateFail() {
        try {
            Toast.makeText(this, getString(R.string.toast_unit_exist), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Hiển thị thông báo khi dữ liệu không hợp lệ
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
     * Created_date: 4/1/2019
     * Nhận view và vị trí được chọn trong danh sách khi click view item
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        try {
            mSelectedIndex = position;
            mUnitItemAdapter.setSelectIndex(mSelectedIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Nhận view và vị trí click nút chỉnh sửa
     * @param itemView
     * @param layoutPosition
     */
    @Override
    public void onItemEditClick(View itemView, int layoutPosition) {
        try {
            isNew = false;
            mSelectedIndex = layoutPosition;
            new AddDialog(this, "Sửa đơn vị tính", InputType.TYPE_CLASS_TEXT, mUnitItemArrayList.get(layoutPosition).getName()).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
