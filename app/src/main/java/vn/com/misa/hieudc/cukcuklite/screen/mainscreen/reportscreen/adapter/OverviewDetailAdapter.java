package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.adapter;

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
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.fragment.ReportOverviewFragment;

/**
 * Created_by: dchieu
 * Created_date: 4/19/2019
 * Adapter hiển thị danh sách báo cáo theo ngày, tháng
 */
public class OverviewDetailAdapter extends RecyclerView.Adapter<OverviewDetailAdapter.ViewHolder> {

    private ArrayList<Long> mListReport;
    private int mTitleType;
    private IOverviewDetailItemClick mIOverviewDetailItemClick;

    /**
     * Created_by: dchieu
     * Created_date: 4/19/2019
     */
    public interface IOverviewDetailItemClick {

        void onClickItem(int layoutPosition);
    }

    public OverviewDetailAdapter(ArrayList<Long> listReport, int titleType) {
        mListReport = listReport;
        mTitleType = titleType;
    }

    public void setListener(IOverviewDetailItemClick iOverviewDetailItemClick) {
        mIOverviewDetailItemClick = iOverviewDetailItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(AppContext.getInstance());
        View itemView = inflater.inflate(R.layout.report_overview_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Long report = mListReport.get(position);
            String title = "";
            switch (mTitleType) {
                case ReportOverviewFragment.WEEK_DETAIL:
                    if(position == 6 ) title = "CN";
                    else title = String.format("Thứ %s", String.valueOf(position + 2));
                    break;
                case ReportOverviewFragment.MONTH_DETAIL:
                    title = String.format("Ngày %s", String.valueOf(position + 1));
                    break;
                case ReportOverviewFragment.YEAR_DETAIL:
                    title = String.format("Tháng %s", String.valueOf(position + 1));
                    break;
                default:
                    break;
            }
            holder.tvTitle.setText(title);
            holder.tvContent.setText(NumberFormat.getInstance().format(report));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mListReport.size();
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/19/2019
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
                if (mIOverviewDetailItemClick != null) {
                    if (mListReport.get(getLayoutPosition()) > 0)
                        mIOverviewDetailItemClick.onClickItem(getLayoutPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
