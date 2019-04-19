package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.listorderscreen.adapter;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.config.AppContext;
import vn.com.misa.hieudc.cukcuklite.model.FoodItem;
import vn.com.misa.hieudc.cukcuklite.model.Order;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Adapter hiển thị danh sách đơn hàng
 */
public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> {
    private final String[] listColor;
    private ArrayList<Order> mOrderArrayList;
    private ListOrderAdapter.OnItemClick mListener;

    public ListOrderAdapter(ArrayList<Order> orderArrayList) {
        mOrderArrayList = orderArrayList;
        listColor = AppContext.getInstance().getResources().getStringArray(R.array.color_default);
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Set sự kiện onClick
     * @param onItemClick đối tượng implement interface nhận dữ liệu
     */
    public void setOnItemClick(ListOrderAdapter.OnItemClick onItemClick) {
        try {
            mListener = onItemClick;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(AppContext.getInstance());
        View view = inflater.inflate(R.layout.order_item, viewGroup, false);
        return new ListOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try {
            Order order = mOrderArrayList.get(i);
            if (order.getNumberOfTable() > 0) {
                viewHolder.tvNumberOfTable.setText(String.valueOf(order.getNumberOfTable()));
                viewHolder.tvNumberOfTable.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(listColor[i % listColor.length])));
            } else {
                viewHolder.tvNumberOfTable.setBackgroundTintList(ColorStateList.valueOf(AppContext.getInstance().getResources().getColor(R.color.colorBackgroundPrimary)));
            }
            if (order.getNumberOfPeople() > 0) {
                viewHolder.tvNumberOfPeople.setText(String.valueOf(order.getNumberOfPeople()));
                viewHolder.tvNumberOfPeople.setVisibility(View.VISIBLE);
            }
            SpannableStringBuilder textSpan = new SpannableStringBuilder();
            for (Map.Entry me : order.getListFoodItem().entrySet()) {
                try {
                    FoodItem foodItem = (FoodItem) me.getKey();
                    String textSource = foodItem.getFoodItemsName() + " (" + me.getValue() + "), ";
                    SpannableString subSpannable = new SpannableString(textSource);

                    int startIndex = foodItem.getFoodItemsName().length() + 1;
                    int endIndex = textSource.length() - 2;
                    subSpannable.setSpan(new RelativeSizeSpan(0.8f), startIndex, endIndex, 0);
                    subSpannable.setSpan(new ForegroundColorSpan(AppContext.getInstance().getResources().getColor(R.color.colorBlue)), startIndex, endIndex, 0);
                    textSpan.append(subSpannable);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
            textSpan.delete(textSpan.length() - 2, textSpan.length() - 1);
            viewHolder.tvListItem.setText(textSpan);
            viewHolder.tvTotalBill.setText(NumberFormat.getInstance().format(order.getTotalPaid()));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mOrderArrayList.size();
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     *
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNumberOfTable, tvNumberOfPeople, tvListItem, tvTotalBill;
        View rlCancel, rlCollectMoney;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            initListener(itemView);
        }

        /**
         * Created_by: dchieu
         * Created_date: 4/1/2019
         *
         * @param itemView View chứa
         */
        private void initListener(View itemView) {
            try {
                itemView.setOnClickListener(this);
                rlCancel.setOnClickListener(this);
                rlCollectMoney.setOnClickListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Created_by: dchieu
         * Created_date: 4/1/2019
         *
         * @param itemView view chứa
         */
        private void initView(View itemView) {
            try {
                tvNumberOfTable = itemView.findViewById(R.id.tv_table_number);
                tvNumberOfPeople = itemView.findViewById(R.id.tv_people_number);
                tvListItem = itemView.findViewById(R.id.tv_list_item);
                tvTotalBill = itemView.findViewById(R.id.tv_total_paid);
                rlCancel = itemView.findViewById(R.id.rl_cancel);
                rlCollectMoney = itemView.findViewById(R.id.rl_collect_money);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {
            if (mListener != null)
                switch (v.getId()) {
                    case R.id.rl_cancel:
                        try {
                            mListener.onCancel(getLayoutPosition());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.rl_collect_money:
                        try {
                            mListener.onCollectMoney(getLayoutPosition());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        try {
                            mListener.onOrderItemClick(getLayoutPosition());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Interface nhận dữ liệu
     */
    public interface OnItemClick {
        void onOrderItemClick(int position);

        void onCancel(int position);

        void onCollectMoney(int position);
    }
}
