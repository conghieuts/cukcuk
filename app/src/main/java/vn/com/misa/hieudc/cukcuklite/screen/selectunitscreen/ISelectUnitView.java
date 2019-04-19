package vn.com.misa.hieudc.cukcuklite.screen.selectunitscreen;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.model.UnitItem;

public interface ISelectUnitView {
    void onGetUnitItemListDone(ArrayList<UnitItem> unitItems);

    void onSelectSuccess(UnitItem unitItem);

    void onCreateFail();

    void onDataError();
}
