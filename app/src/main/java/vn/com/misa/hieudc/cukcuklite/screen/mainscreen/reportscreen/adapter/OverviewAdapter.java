package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.R;

/**
 * Created_by: dchieu
 * Created_date: 4/17/2019
 * Adapter hiển thị danh sách báo cáo theo ngày, tháng
 */
public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Long> mListReport;
    private String typeReport;
    private String mTitle;

    OverviewAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.report_overview_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Long report = mListReport.get(position);
//            holder.tvTitle.setText(report.getTitle());
            holder.tvContent.setText(NumberFormat.getInstance().format(report));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListReport != null ? mListReport.size() : 0;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/1/2019
     *
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle, tvContent;

        ViewHolder(View itemView) {
            super(itemView);
            try {
                tvTitle = itemView.findViewById(R.id.tv_title);
                tvContent = itemView.findViewById(R.id.tv_content);
                itemView.setOnClickListener(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {
            try {
//TODO
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
