package vn.com.misa.hieudc.cukcuklite.screen.selectunitscreen;

import android.content.Context;
import android.text.TextUtils;

import vn.com.misa.hieudc.cukcuklite.model.UnitItem;
import vn.com.misa.hieudc.cukcuklite.database.UnitItemDatabaseManager;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Lớp logic cho màn hình chọn đơn vị tính
 */
public class SelectUnitPresenter implements ISelectUnitPresenter {
    private UnitItemDatabaseManager mUnitItemDatabaseManager;
    private ISelectUnitView mISelectUnitView;

    SelectUnitPresenter(Context context) {
        try {
            mISelectUnitView = (ISelectUnitView) context;
            mUnitItemDatabaseManager = new UnitItemDatabaseManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Tạo đơn vị tính mới
     * @param unitName tên đơn vị tính
     */
    @Override
    public void createUnit(String unitName) {
        if (TextUtils.isEmpty(unitName)) mISelectUnitView.onDataError();
        try {
            UnitItem unitItem = new UnitItem();
            unitItem.setName(unitName);
            long result = mUnitItemDatabaseManager.addUnitItem(unitItem);
            if (result > -1) {
                unitItem.setId(result);
                mISelectUnitView.onSelectSuccess(unitItem);
            } else {
                mISelectUnitView.onCreateFail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * lấy danh sách đơn vị tính
     */
    @Override
    public void getAllUnitItem() {
        try {
            mISelectUnitView.onGetUnitItemListDone(mUnitItemDatabaseManager.getAllUnitItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Cập nhật tên đơn vị tính
     * @param unitItem đơn vị tính
     * @param data tên đơn vị tính
     */
    @Override
    public void editUnit(UnitItem unitItem, String data) {
        if (TextUtils.isEmpty(data)) mISelectUnitView.onDataError();
        try {
            unitItem.setName(data);
            boolean check = mUnitItemDatabaseManager.updateUnitItem(unitItem);
            if (check) {
                mISelectUnitView.onSelectSuccess(unitItem);
            } else {
                mISelectUnitView.onCreateFail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
