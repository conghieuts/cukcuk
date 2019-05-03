package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.fragment.ReportOverviewDetailFragment;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.fragment.ReportOverviewFragment;

/**
 * Created_by: dchieu
 * Created_date: 4/18/2019
 * Fragment báo cáo doanh thu
 */
public class ReportFragment extends Fragment implements ReportOverviewFragment.IOnClickTimeReport {
    IReportPresenter mIReportPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIReportPresenter = new ReportPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fl_container_report, ReportOverviewFragment.newInstance(this, mIReportPresenter), null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startOverviewDetail(int targetFragment) {
        try {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fl_container_report, ReportOverviewDetailFragment.newInstance(mIReportPresenter, targetFragment), null);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
