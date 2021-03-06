package vn.com.misa.hieudc.cukcuklite.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.config.AppContext;

/**
 * Created_by: dchieu
 * Created_date: 4/6/2019
 * Dialog với 2 nút xác nhận
 */
public class ConfirmDialog extends AlertDialog implements View.OnClickListener {
    private Context mContext;
    private IOnConfirm mIOnConfirm;
    private String mTitle, mMessage;
    private AlertDialog mAlertDialog;


    public interface IOnConfirm {
        void onConfirm();
    }

    public ConfirmDialog(@NonNull Context context, ConfirmDialog.IOnConfirm listener, String title, String message) {
        super(context);
        try {
            mContext = context;
            mIOnConfirm = listener;
            mTitle = title;
            mMessage = message;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.dialog_confirm, null);
            initView(view);
            mAlertDialog = builder.setView(view).create();
            mAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/6/2019
     * Thực hiện setup giao diện
     *
     * @param view
     */
    private void initView(View view) {
        try {
            TextView tvTitle = view.findViewById(R.id.tv_toolbar_title);
            TextView tvMessage = view.findViewById(R.id.tv_description);
            TextView btnCancel = view.findViewById(R.id.btn_cancel);
            TextView btnAccept = view.findViewById(R.id.btn_confirm);
            tvTitle.setText(mTitle);
            tvMessage.setText(mMessage);
            btnAccept.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_confirm:
                    try {
                        mIOnConfirm.onConfirm();
                        mAlertDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_cancel:
                    try {
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
