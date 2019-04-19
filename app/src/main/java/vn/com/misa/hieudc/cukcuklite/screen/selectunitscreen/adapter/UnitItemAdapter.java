package vn.com.misa.hieudc.cukcuklite.screen.selectunitscreen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.model.UnitItem;
import vn.com.misa.hieudc.cukcuklite.R;

/**
 * Created_by: dchieu
 * Created_date: 4/1/2019
 * Adapter hiển thị danh sách đơn vị tính cho màn hình chọn đơn vị tính
 */
public class UnitItemAdapter extends RecyclerView.Adapter<UnitItemAdapter.ViewHolder> {
    private ArrayList<UnitItem> mUnitItemArrayList;
    private int mSelectIndex;
    private Context mContext;
    private OnItemClickListener mListener;

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Interface nhận sự kiện click
     */
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);

        void onItemEditClick(View itemView, int layoutPosition);
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     * Nhận đối tượng đăng ký sự kiện click
     * @param listener đối tượng đăng ký sự kiện click
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public UnitItemAdapter(ArrayList<UnitItem> unitItemArrayList, Context context) {
        try {
            mUnitItemArrayList = unitItemArrayList;
            mContext = context;
            mSelectIndex = -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     *
     * @param selectIndex vị trí của đơn vị tính được chọn
     */
    public void setSelectIndex(int selectIndex) {
        try {
            int oldSelect = mSelectIndex;
            mSelectIndex = selectIndex;
            if(oldSelect > -1 ) notifyItemChanged(oldSelect);
            notifyItemChanged(mSelectIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public UnitItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.unit_item, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull UnitItemAdapter.ViewHolder viewHolder, int i) {
        try {
            UnitItem unitItem = mUnitItemArrayList.get(i);
            viewHolder.tvUnitName.setText(unitItem.getName());
            if (i == mSelectIndex) {
                viewHolder.ivSelect.setImageResource(R.drawable.ic_check_circle_normal);
            } else {
                viewHolder.ivSelect.setImageResource(R.drawable.ic_add_normal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mUnitItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvUnitName;
        ImageView ivSelect, ivEdit;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            try {
                tvUnitName = itemView.findViewById(R.id.tv_unit_name);
                ivSelect = itemView.findViewById(R.id.iv_select);
                ivEdit = itemView.findViewById(R.id.iv_edit);
                ivSelect.setOnClickListener(this);
                tvUnitName.setOnClickListener(this);
                ivEdit.setOnClickListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {
            try {
                switch (v.getId()) {
                    case R.id.tv_unit_name:
                    case R.id.iv_select:
                        try {
                            if (mListener != null)
                                mListener.onItemClick(itemView, getLayoutPosition());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.iv_edit:
                        try {
                            if (mListener != null)
                                mListener.onItemEditClick(itemView, getLayoutPosition());
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
}
