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

    private IReportPresenter mIReportPresenter;
    TextView tvYesterday, tvToday, tvThisWeek, tvThisMonth, tvThisYear;

    public ReportOverviewFragment() {
    }

    public void setIReportPresenter(IReportPresenter IReportPresenter) {
        mIReportPresenter = IReportPresenter;
    }

    /**
     * Created_by: dchieu
     * Created_date: 4/17/2019
     *
     * @param iReportPresenter Lớp logic
     * @return ínstance fragment
     */
    public static ReportOverviewFragment newInstance(IReportPresenter iReportPresenter) {
        ReportOverviewFragment fragment = new ReportOverviewFragment();
        try {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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
                    Intent intent = new Intent(getContext(), ReportDetailActivity.class);
                    intent.putStringArrayListExtra("time", ((ReportPresenter) mIReportPresenter).getTimeString(0));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_today:
                try {

                    if (!TextUtils.isEmpty(tvToday.getText()) && tvToday.getText().equals("0"))
                        break;
                    Intent intent = new Intent(getContext(), ReportDetailActivity.class);
                    intent.putStringArrayListExtra("time", ((ReportPresenter) mIReportPresenter).getTimeString(1));
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_this_week: {
                try {
                    if (TextUtils.isEmpty(tvThisWeek.getText()) && tvThisWeek.getText().equals("0"))
                        break;
                    Intent intent = new Intent(getContext(), ReportDetailActivity.class);
                    intent.putStringArrayListExtra("time", ((ReportPresenter) mIReportPresenter).getTimeString(2));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            case R.id.rl_this_month: {
                try {
                    if (TextUtils.isEmpty(tvThisMonth.getText()) && tvThisMonth.getText().equals("0"))
                        break;
                    Intent intent = new Intent(getContext(), ReportDetailActivity.class);
                    intent.putStringArrayListExtra("time", ((ReportPresenter) mIReportPresenter).getTimeString(3));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            case R.id.rl_this_year: {
                try {
                    if (TextUtils.isEmpty(tvThisYear.getText()) && tvThisYear.getText().equals("0"))
                        break;
                    Intent intent = new Intent(getContext(), ReportDetailActivity.class);
                    intent.putStringArrayListExtra("time", ((ReportPresenter) mIReportPresenter).getTimeString(4));
                    startActivity(intent);
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
    public void getReportDone(ArrayList<ArrayList<Long>> result) {
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
}
