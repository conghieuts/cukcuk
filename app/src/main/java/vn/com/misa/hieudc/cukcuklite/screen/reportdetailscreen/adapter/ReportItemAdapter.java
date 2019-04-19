package vn.com.misa.hieudc.cukcuklite.screen.reportdetailscreen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.model.CheckoutItem;

public class ReportItemAdapter extends RecyclerView.Adapter<ReportItemAdapter.ViewHolder> {
    private Context mContext;

    private ArrayList<CheckoutItem> mListCheckoutItem;

    public ReportItemAdapter(Context context, ArrayList<CheckoutItem> listCheckoutItem) {
        mContext = context;
        mListCheckoutItem = listCheckoutItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.report_detail_item, parent, false);
        return new ViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            String[] colors = mContext.getResources().getStringArray(R.array.color_report);
            CheckoutItem checkoutItem = mListCheckoutItem.get(position);

            if (position < 6) {
                int index = position % 6;
                ViewCompat.setBackgroundTintList(holder.ivIndex, ColorStateList.valueOf(Color.parseColor(colors[index])));
            }
            else {
                ViewCompat.setBackgroundTintList(holder.ivIndex, ColorStateList.valueOf(mContext.getResources().getColor(R.color.colorGrey)));
            }

            holder.tvIndex.setText(String.valueOf(position + 1));
            holder.tvNameFood.setText(checkoutItem.getFoodName());
            holder.tvNumber.setText(checkoutItem.getAmount() + " " + checkoutItem.getUnit());
            holder.tvTotalMoney.setText(NumberFormat.getInstance().format(checkoutItem.getTotalPaid()));

            if (position == mListCheckoutItem.size() - 1) {
                holder.vSeparate.setVisibility(View.INVISIBLE);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListCheckoutItem != null ? mListCheckoutItem.size() : 0;
    }

    void setDate(ArrayList<CheckoutItem> listCheckoutItem) {
        try {
            if (listCheckoutItem != null) {
                mListCheckoutItem = listCheckoutItem;
                notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIndex;
        private TextView tvIndex, tvNameFood, tvNumber, tvTotalMoney;
        private View vSeparate;

        ViewHolder(View itemView) {
            super(itemView);

            try {
                ivIndex = itemView.findViewById(R.id.ivIndex);
                tvIndex = itemView.findViewById(R.id.tvIndex);
                tvNameFood = itemView.findViewById(R.id.tvNameFood);
                tvNumber = itemView.findViewById(R.id.tvNumber);
                tvTotalMoney = itemView.findViewById(R.id.tvTotalMoney);
                vSeparate = itemView.findViewById(R.id.vSeparate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
