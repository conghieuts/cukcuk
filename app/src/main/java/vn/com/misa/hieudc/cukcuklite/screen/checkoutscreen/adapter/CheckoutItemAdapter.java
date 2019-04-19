package vn.com.misa.hieudc.cukcuklite.screen.checkoutscreen.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.config.AppContext;
import vn.com.misa.hieudc.cukcuklite.model.CheckoutItem;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Adapter hiển thị danh sách các món trong hóa đơn
 */
public class CheckoutItemAdapter extends RecyclerView.Adapter<CheckoutItemAdapter.ViewHolder> {
    private ArrayList<CheckoutItem> mCheckoutItemArrayList;

    public CheckoutItemAdapter(ArrayList<CheckoutItem> checkoutItemArrayList) {
        mCheckoutItemArrayList = checkoutItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(AppContext.getInstance());
        View view = inflater.inflate(R.layout.bill_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        try {
            CheckoutItem checkoutItem = mCheckoutItemArrayList.get(i);
            viewHolder.tvFoodName.setText(checkoutItem.getFoodName());
            viewHolder.tvAmount.setText(String.valueOf(checkoutItem.getAmount()));
            viewHolder.tvCost.setText(NumberFormat.getInstance().format(checkoutItem.getTotalPaid() / checkoutItem.getAmount()));
            viewHolder.tvTotalPaid.setText(NumberFormat.getInstance().format(checkoutItem.getTotalPaid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mCheckoutItemArrayList.size();
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvAmount, tvCost, tvTotalPaid;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                tvFoodName = itemView.findViewById(R.id.tv_food_name);
                tvAmount = itemView.findViewById(R.id.tv_amount);
                tvCost = itemView.findViewById(R.id.tv_food_cost);
                tvTotalPaid = itemView.findViewById(R.id.tv_total_paid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
