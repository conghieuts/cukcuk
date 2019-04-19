package vn.com.misa.hieudc.cukcuklite.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import vn.com.misa.hieudc.cukcuklite.R;

/**
 * Created_by: dchieu
 * Created_date: 4/4/2019
 * Dialog với 1 trường nhập
 */
public class AddDialog extends AlertDialog implements View.OnClickListener {

    /**
     * Created_by: dchieu
     * Created_date: 4/4/2019
     * Interface nhạn dữ liệu khi thêm
     */
    public interface IOnAddDialogSave {
        void onAddDialogSave(String data);
    }
    private Context mContext;
    private EditText edtAdd;
    private TextView tvTitle;
    private Button btnCancel, btnSave;
    private AlertDialog mAlertDialog;
    private IOnAddDialogSave mIOnAddDialogSave;
    private String mTitle, mCurrentData;
    private int mInputType;

    public AddDialog(@NonNull Context context, String title, int inputType, String currentData) {
        super(context);
        try {
            mContext = context;
            mIOnAddDialogSave = (IOnAddDialogSave) context;
            mTitle = title;
            mInputType = inputType;
            mCurrentData = currentData;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        try {
            Builder builder = new Builder(mContext);

            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.dialog_add, null);
            initView(view);

            mAlertDialog = builder.setView(view).create();
            mAlertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/4/2019
     * Thực hiện setup giao diện
     * @param view
     */
    private void initView(View view) {
        try {
            tvTitle = view.findViewById(R.id.tv_toolbar_title);
            edtAdd = view.findViewById(R.id.edt_add);
            btnCancel = view.findViewById(R.id.btn_cancel);
            btnSave = view.findViewById(R.id.btn_save);
            tvTitle.setText(mTitle);
            edtAdd.setInputType(mInputType);
            edtAdd.setText(mCurrentData);
            edtAdd.setSelection(mCurrentData.length());
            btnSave.setOnClickListener(this);
            btnCancel.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_save:
                    try {
                        mIOnAddDialogSave.onAddDialogSave(edtAdd.getText().toString());
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
