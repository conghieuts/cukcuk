package vn.com.misa.hieudc.cukcuklite.screen.selectunitscreen;

import vn.com.misa.hieudc.cukcuklite.model.UnitItem;

public interface ISelectUnitPresenter {
    void createUnit(String unitName);

    void getAllUnitItem();

    void editUnit(UnitItem unitItem, String data);
}
