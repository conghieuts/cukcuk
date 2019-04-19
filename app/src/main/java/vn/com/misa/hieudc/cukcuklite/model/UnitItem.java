package vn.com.misa.hieudc.cukcuklite.model;

import java.io.Serializable;

/**
 * Created_by: dchieu
 * Created_date: 4/4/2019
 * Đối tượng đơn vị tính
 */
public class UnitItem implements Serializable {

    private long mId;
    private String mName;

    public UnitItem() {
    }

    public UnitItem(long id, String name) {
        mId = id;
        mName = name;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
