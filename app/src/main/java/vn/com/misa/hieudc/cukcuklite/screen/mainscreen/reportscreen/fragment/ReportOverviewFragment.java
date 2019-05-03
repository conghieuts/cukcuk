package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.IReportPresenter;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.IReportView;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.ReportPresenter;
import vn.com.misa.hieudc.cukcuklite.screen.reportdetailscreen.ReportDetailActivity;

/**
 * Created_by: dchieu
 * Created_date: 4/17/2019
 * Fragment hiển thị tổng quan báo cáo theo ngày, tuần, tháng, năm
 */
public class ReportOverviewFragment extends Fragment implements View.OnClickListener, IReportView {
    public static final int WEEK_DETAIL = 2;
    public static final int MONTH_DETAIL = 3;
    public static final int YEAR_DETAIL = 4;
    
    private IOnClickTimeReport mIOnClickTimeReport;
    private IReportPresenter mIReportPresenter;
    TextView tvYesterday, tvToday, tvThisWeek, tvThisMonth, tvThisYear;

    public ReportOverviewFragment() {
    }

    public void setIReportPresenter(IReportPresenter IReportPresenter) {
        mIReportPresenter = IReportPresenter;
    }

    public void setIOnClickTimeReport(IOnClickTimeReport IOnClickTimeReport) {
        mIOnClickTimeReport = IOnClickTimeReport;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/17/2019
     *
     * @param iReportPresenter Lớp logic
     * @return ínstance fragment
     */
    public static ReportOverviewFragment newInstance(IOnClickTimeReport iOnClickTimeReport, IReportPresenter iReportPresenter) {
        ReportOverviewFragment fragment = new ReportOverviewFragment();
        try {
            fragment.setIOnClickTimeReport(iOnClickTimeReport);
            fragment.setIReportPresenter(iReportPresenter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            view.findViewById(R.id.rl_yesterday).setOnClickListener(this);
            view.findViewById(R.id.rl_today).setOnClickListener(this);
            view.findViewById(R.id.rl_this_week).setOnClickListener(this);
            view.findViewById(R.id.rl_this_month).setOnClickListener(this);
            view.findViewById(R.id.rl_this_year).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            tvYesterday = view.findViewById(R.id.tv_yesterday);
            tvToday = view.findViewById(R.id.tv_today);
            tvThisWeek = view.findViewById(R.id.tv_this_week);
            tvThisMonth = view.findViewById(R.id.tv_this_month);
            tvThisYear = view.findViewById(R.id.tv_this_year);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            mIReportPresenter.getReportAllTime(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_yesterday:
                try {
                    if (!TextUtils.isEmpty(tvYesterday.getText()) && tvYesterday.getText().equals("0"))
                        break;
                    mIReportPresenter.getListTime(this, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_today:
                try {
                    if (!TextUtils.isEmpty(tvToday.getText()) && tvToday.getText().equals("0"))
                        break;
                    mIReportPresenter.getListTime(this, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_this_week: {
                try {
                    if(mIOnClickTimeReport != null) {
                        mIOnClickTimeReport.startOverviewDetail(WEEK_DETAIL);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            case R.id.rl_this_month: {
                try {
                    if(mIOnClickTimeReport != null) {
                        mIOnClickTimeReport.startOverviewDetail(MONTH_DETAIL);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            case R.id.rl_this_year: {
                try {
                    if(mIOnClickTimeReport != null) {
                        mIOnClickTimeReport.startOverviewDetail(YEAR_DETAIL);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            default:
                break;
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/17/2019
     *
     * @param result dữ liệu
     */
    @Override
    public void getReportAllTimeDone(ArrayList<ArrayList<Long>> result) {
        try {
            ArrayList<Long> res = new ArrayList<>();
            for (ArrayList<Long> listReport : result) {
                long total = 0;
                for (Long report : listReport) {
                    total += report;
                }
                res.add(total);
            }
            tvYesterday.setText(NumberFormat.getInstance().format(res.get(0)));
            tvToday.setText(NumberFormat.getInstance().format(res.get(1)));
            tvThisWeek.setText(NumberFormat.getInstance().format(res.get(2)));
            tvThisMonth.setText(NumberFormat.getInstance().format(res.get(3)));
            tvThisYear.setText(NumberFormat.getInstance().format(res.get(4)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getReportByTimeDone(ArrayList<Long> result) {

    }

    @Override
    public void getListTimeDone(ArrayList<Long> listTime) {
        if(listTime.size() < 2) return;
        try {
            Intent intent = new Intent(getContext(), ReportDetailActivity.class);
            intent.putExtra("startTime", listTime.get(0));
            intent.putExtra("endTime", listTime.get(1));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/19/2019
     * Interface nhận sự kiện click vào 1 khoảng thời gian danh sách
     */
    public interface IOnClickTimeReport {
        void startOverviewDetail(int weekDetail);
    }
}
