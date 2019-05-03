package vn.com.misa.hieudc.cukcuklite.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import vn.com.misa.hieudc.cukcuklite.R;

/**
 * Created_by: dchieu
 */
public class TimeDialog extends AlertDialog implements View.OnClickListener {
    private Context mContext;
    private IOnSelect mIOnSelect;
    private AlertDialog mAlertDialog;
    public static final int DEFAULT = 0;
    public static final int THIS_WEEK = 1;
    public static final int THIS_MONTH = 2;
    public static final int THIS_YEAR = 3;

    public interface IOnSelect {

        void onSelect(int thisYear);
    }

    public TimeDialog(@NonNull Context context, IOnSelect listener) {
        super(context);
        try {
            mContext = context;
            mIOnSelect = listener;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.dialog_select_time, null);
            initListener(view);
            mAlertDialog = builder.setView(view).create();
            mAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * <p>
     * Thực hiện setup on click
     *
     * @param view
     */
    private void initListener(View view) {
        try {
            view.findViewById(R.id.tv_default).setOnClickListener(this);
            view.findViewById(R.id.tv_this_week).setOnClickListener(this);
            view.findViewById(R.id.tv_this_month).setOnClickListener(this);
            view.findViewById(R.id.tv_this_year).setOnClickListener(this);
            view.findViewById(R.id.tv_other).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.tv_default:
                    try {
                        mIOnSelect.onSelect(DEFAULT);
                        mAlertDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.tv_this_week:
                    try {
                        mIOnSelect.onSelect(THIS_WEEK);
                        mAlertDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.tv_this_month:
                    try {
                        mIOnSelect.onSelect(THIS_MONTH);
                        mAlertDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.tv_this_year:
                    try {
                        mIOnSelect.onSelect(THIS_YEAR);
                        mAlertDialog.dismiss();
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
}
