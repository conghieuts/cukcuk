package vn.com.misa.hieudc.cukcuklite.screen.addfooditemscreen.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import vn.com.misa.hieudc.cukcuklite.R;

/**
 * Created_by: dchieu
 * Created_date: 3/27/2019
 * Dialog chọn màu sắc
 */
public class ColorDialog extends AlertDialog implements View.OnClickListener {
    private AlertDialog mAlertDialog;
    private IColorPicked mIColorPicked;
    private String mColor;
    private ViewGroup glIcon;
    private String[] listColor;

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Interface nhận màu khi chọn xong
     */
    public interface IColorPicked {
        void colorPicked(String color);
    }

    public ColorDialog(@NonNull Context context, String color) {
        super(context);
        try {
            mIColorPicked = (IColorPicked) context;
            mColor = color;
            listColor = context.getResources().getStringArray(R.array.color_default);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        try {
            Context context = getContext();
            Builder builder = new Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_color, null);
            initView(view);
            mAlertDialog = builder.setView(view).create();
            mAlertDialog.show();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            mAlertDialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            mAlertDialog.getWindow().setLayout(displayMetrics.widthPixels*98/100, displayMetrics.heightPixels*65/100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Thiết lập hiển thị giao diện
     *
     * @param view giao diện cần thiết lập đã được inflate
     */
    @SuppressLint("NewApi")
    private void initView(View view) {
        glIcon = view.findViewById(R.id.gl_icon_color);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);

        for (int i = 0; i < listColor.length; i++) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ImageView imageView = (ImageView) inflater.inflate(R.layout.color_item, glIcon, false);
            if (mColor.equals(listColor[i])) imageView.setImageResource(R.drawable.ic_check_normal);
            imageView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(listColor[i])));
            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIColorPicked.colorPicked(listColor[finalI]);
                    mAlertDialog.dismiss();
                }
            });
            glIcon.addView(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
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
