package vn.com.misa.hieudc.cukcuklite.screen.addfooditemscreen.dialog;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import vn.com.misa.hieudc.cukcuklite.R;

/**
 * Created_by: dchieu
 * Created_date: 3/27/2019
 * Dialog chọn icon món ăn
 */
public class IconDialog extends AlertDialog implements View.OnClickListener {
    private AlertDialog mAlertDialog;
    private IIconDialogSelected mIIconDialogSelected;
    private ViewGroup glIcon;
    private String[] mListImage;
    private AssetManager mAssetManager;
    private String mAssetFolder;

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Interface nhận icon
     */
    public interface IIconDialogSelected {
        void iconPicked(String fileName);
    }

    public IconDialog(@NonNull Context context) {
        super(context);
        try {
            mIIconDialogSelected = (IIconDialogSelected) context;
            mAssetFolder = context.getString(R.string.asset_folder);
            mAssetManager = context.getAssets();
            mListImage = mAssetManager.list(mAssetFolder);

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
            View view = inflater.inflate(R.layout.dialog_icon, null);
            initView(view);
            mAlertDialog = builder.setView(view).create();
            mAlertDialog.show();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            mAlertDialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            mAlertDialog.getWindow().setLayout(displayMetrics.widthPixels*98/100, displayMetrics.heightPixels*90/100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     *
     * @param view
     */
    private void initView(View view) {
        try {
            glIcon = view.findViewById(R.id.gl_icon_color);
            Button btnCancel = view.findViewById(R.id.btn_cancel);
            btnCancel.setOnClickListener(this);
            for (int i = 0; i < mListImage.length; i++) {
                InputStream ims = mAssetManager.open(mAssetFolder + "/" + mListImage[i]);
                Drawable d = Drawable.createFromStream(ims, null);
                LayoutInflater inflater = LayoutInflater.from(getContext());
                ImageView imageView = (ImageView) inflater.inflate(R.layout.icon_item, glIcon, false);
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mIIconDialogSelected.iconPicked(mListImage[finalI]);
                            mAlertDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                imageView.setImageDrawable(d);
                glIcon.addView(imageView);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
