package vn.com.misa.hieudc.cukcuklite.screen.selectfooditemscreen.adapter;

import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.config.AppContext;
import vn.com.misa.hieudc.cukcuklite.model.FoodItem;
import vn.com.misa.hieudc.cukcuklite.model.Order;

/**
 * Created_by: dchieu
 * Created_date: 4/18/2019
 * Adapter hiển thị danh sách món ăn trong màn hình chọn món
 */
public class SelectFoodItemAdapter extends RecyclerView.Adapter<SelectFoodItemAdapter.ViewHolder> {
    private ArrayList<FoodItem> mFoodItemArrayList;
    private Order mOrder;
    private static SelectFoodItemAdapter.OnItemClickListener mListener;

    public SelectFoodItemAdapter(ArrayList<FoodItem> foodItemArrayList, @Nullable Order order) {
        mFoodItemArrayList = foodItemArrayList;
        mOrder = order;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     * Interface nhận sự kiện click trong 1 item
     */
    public interface OnItemClickListener {
        void onFoodItemClick(View view, int position);

        void onIconItemClick(View view, int position);

        void onIncrementClick(View view, int position);

        void onDecrementClick(View view, int position);

        void onAmountItemClick(View view, int position);
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/18/2019
     *
     * @param listener Đối tượng lằng nghe sự kiện click
     */
    public void setOnItemClickListener(SelectFoodItemAdapter.OnItemClickListener listener) {
        try {
            mListener = listener;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(AppContext.getInstance());
        View view = layoutInflater.inflate(R.layout.menu_select_item, viewGroup, false);
        return new SelectFoodItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try {
            FoodItem foodItem = mFoodItemArrayList.get(i);
            viewHolder.tvFoodItemsName.setText(foodItem.getFoodItemsName());
            viewHolder.tvFoodItemsCost.setText(NumberFormat.getInstance().format(foodItem.getFoodItemsCost()));
            AssetManager assetManager = AppContext.getInstance().getAssets();
            InputStream ims = assetManager.open(AppContext.getInstance().getResources().getString(R.string.asset_folder) + "/" + foodItem.getIcon());
            Drawable d = Drawable.createFromStream(ims, null);
            viewHolder.ivFoodItemsIcon.setImageDrawable(d);
            int padding = (int) AppContext.getInstance().getResources().getDimension(R.dimen.icon_padding);
            viewHolder.ivFoodItemsIcon.setPadding(padding, padding, padding, padding);
            if (mOrder != null) {
                Integer amount = mOrder.getListFoodItem().get(foodItem);
                if (amount != null) {
                    viewHolder.tvAmount.setText(String.valueOf(amount));
                    viewHolder.ivFoodItemsIcon.setImageResource(R.drawable.ic_selected);
                    viewHolder.ivFoodItemsIcon.setPadding(0,0,0,0);
                    viewHolder.rootLayout.setBackgroundColor(AppContext.getInstance().getResources().getColor(R.color.colorBackgroundSecondary));
                    viewHolder.llSelectAmount.setVisibility(View.VISIBLE);
                }
            }
            ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(foodItem.getColor()));
            viewHolder.ivFoodItemsIcon.setBackgroundTintList(colorStateList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mFoodItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFoodItemsName, tvFoodItemsCost, tvAmount;
        ImageView ivFoodItemsIcon, ivIncrement, ivDecrement;
        View rootLayout, llSelectAmount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            initListener();
        }

        /**
         * Created_by: dchieu
         * Created_date: 4/18/2019
         * Ánh xạ và hiển thị view
         * @param itemView đối tượng view gốc
         */
        private void initView(View itemView) {
            try {
                tvFoodItemsName = itemView.findViewById(R.id.item_name);
                tvFoodItemsCost = itemView.findViewById(R.id.item_cost);
                tvAmount = itemView.findViewById(R.id.tv_amount);
                ivFoodItemsIcon = itemView.findViewById(R.id.iv_icon);
                ivIncrement = itemView.findViewById(R.id.iv_increment);
                ivDecrement = itemView.findViewById(R.id.iv_decrement);
                rootLayout = itemView.findViewById(R.id.ll_menu_select_item);
                llSelectAmount = itemView.findViewById(R.id.ll_select_amount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Created_by: dchieu
         * Created_date: 4/18/2019
         * Đăng ký lắng nghe sự kiện click
         */
        private void initListener() {
            try {
                tvAmount.setOnClickListener(this);
                ivDecrement.setOnClickListener(this);
                ivIncrement.setOnClickListener(this);
                rootLayout.setOnClickListener(this);
                llSelectAmount.setOnClickListener(this);
                ivFoodItemsIcon.setOnClickListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {
            if (mListener != null)
                switch (v.getId()) {
                    case R.id.tv_amount:
                        try {
                            mListener.onAmountItemClick(itemView, getLayoutPosition());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.iv_decrement:
                        try {
                            mListener.onDecrementClick(itemView, getLayoutPosition());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.iv_increment:
                        try {
                            mListener.onIncrementClick(itemView, getLayoutPosition());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.ll_menu_select_item:
                        try {
                            mListener.onFoodItemClick(itemView, getLayoutPosition());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.iv_icon:
                        try {
                            mListener.onIconItemClick(itemView, getLayoutPosition());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
        }
    }
}
