package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.menuscreen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.config.AppContext;
import vn.com.misa.hieudc.cukcuklite.model.FoodItem;
import vn.com.misa.hieudc.cukcuklite.R;

/**
 * Created_by: dchieu
 * Created_date: 3/27/2019
 * Adapter hiển thị món ăn
 */
public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {
    private ArrayList<FoodItem> mFoodItemArrayList;
    private Context mContext;
    private static OnItemClickListener mListener;

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     * Interface gửi vị trí click
     */
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    /**
     * Created_by: dchieu
     * Created_date: 3/27/2019
     *
     * @param listener đối tượng nhận sự kiện
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        try {
            mListener = listener;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     *
     * @param foodItemArrayList danh sách món ăn
     */
    public MenuItemAdapter(ArrayList<FoodItem> foodItemArrayList) {
        try {
            mFoodItemArrayList = foodItemArrayList;
            mContext = AppContext.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.menu_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        try {
            FoodItem foodItem = mFoodItemArrayList.get(position);
            viewHolder.tvFoodItemsName.setText(foodItem.getFoodItemsName());
            viewHolder.tvFoodItemsCost.setText(NumberFormat.getNumberInstance().format(foodItem.getFoodItemsCost()));

            AssetManager assetManager = mContext.getAssets();
            InputStream ims = assetManager.open(mContext.getResources().getString(R.string.asset_folder) + "/" + foodItem.getIcon());
            Drawable d = Drawable.createFromStream(ims, null);
            viewHolder.ivFoodItemsIcon.setImageDrawable(d);

            ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(foodItem.getColor()));
            viewHolder.ivFoodItemsIcon.setBackgroundTintList(colorStateList);
            if(foodItem.getSelling().equals(FoodItem.STOP_SELLING)) viewHolder.tvSelling.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return mFoodItemArrayList.size();
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     *
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodItemsName, tvFoodItemsCost, tvSelling;
        ImageView ivFoodItemsIcon;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            try {
                tvFoodItemsName = itemView.findViewById(R.id.item_name);
                tvFoodItemsCost = itemView.findViewById(R.id.item_cost);
                ivFoodItemsIcon = itemView.findViewById(R.id.iv_icon);
                tvSelling = itemView.findViewById(R.id.tv_selling);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (mListener != null)
                                mListener.onItemClick(itemView, getLayoutPosition());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
