package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.dialog.TimeDialog;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.fragment.ReportOverviewDetailFragment;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.fragment.ReportOverviewFragment;

/**
 * Created_by: dchieu
 * Created_date: 4/18/2019
 * Fragment báo cáo doanh thu
 */
public class ReportFragment extends Fragment implements ReportOverviewFragment.IOnClickTimeReport, View.OnClickListener, TimeDialog.IOnSelect {
    IReportPresenter mIReportPresenter;
    TextView tvTypeReport;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIReportPresenter = new ReportPresenter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTypeReport = view.findViewById(R.id.tvTypeReport);
        tvTypeReport.setOnClickListener(this);
        startOverviewFragment();
    }

    /**
     * Created_by: dchieu
     * Created_date: 5/2/2019
     * Fragment báo cáo gần đây
     */
    private void startOverviewFragment() {
        try {
            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fl_container_report, ReportOverviewFragment.newInstance(this, mIReportPresenter), null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 2/2/2019
     * Fragment báo cáo tuần, tháng, năm
     */
    @Override
    public void startOverviewDetail(int targetFragment) {
        try {
            FragmentManager fragmentManager = getFragmentManager();
            assert fragmentManager != null;
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fl_container_report, ReportOverviewDetailFragment.newInstance(mIReportPresenter, targetFragment), null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.tvTypeReport:
                    try {
                        new TimeDialog(Objects.requireNonNull(getContext()), this).show();
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

    @Override
    public void onSelect(int type) {
        try {
            switch (type) {
                case TimeDialog.DEFAULT:
                    tvTypeReport.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.time_default));
                    startOverviewFragment();
                    break;
                case TimeDialog.THIS_WEEK:
                    tvTypeReport.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.text_this_week));
                    startOverviewDetail(ReportOverviewFragment.WEEK_DETAIL);
                    break;
                case TimeDialog.THIS_MONTH:
                    tvTypeReport.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.text_this_month));
                    startOverviewDetail(ReportOverviewFragment.MONTH_DETAIL);
                    break;
                case TimeDialog.THIS_YEAR:
                    tvTypeReport.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.this_year));
                    startOverviewDetail(ReportOverviewFragment.YEAR_DETAIL);
                    break;
                default:
                    break;
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
}
