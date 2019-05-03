package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.config.AppContext;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.IReportPresenter;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.IReportView;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.ReportPresenter;
import vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.adapter.OverviewDetailAdapter;
import vn.com.misa.hieudc.cukcuklite.screen.reportdetailscreen.ReportDetailActivity;


/**
 * Created_by: dchieu
 * Created_date: 4/18/2019
 * Fragment hiển thị tổng quan của tuần, tháng, năm
 */
public class ReportOverviewDetailFragment extends Fragment implements IReportView, OverviewDetailAdapter.IOverviewDetailItemClick {

    private IReportPresenter mIReportPresenter;
    private int mType;
    private ArrayList<Long> mListReport;
    private ArrayList<Long> mListTime;

    public ReportOverviewDetailFragment() {
    }

    public static ReportOverviewDetailFragment newInstance(IReportPresenter iReportPresenter, int type) {
        ReportOverviewDetailFragment fragment = new ReportOverviewDetailFragment();
        fragment.setType(type);
        fragment.setIReportPresenter(iReportPresenter);
        return fragment;
    }

    public void setIReportPresenter(IReportPresenter IReportPresenter) {
        mIReportPresenter = IReportPresenter;
        try {
            int position = 0;
            switch (mType) {
                case ReportOverviewFragment.WEEK_DETAIL:
                    position = 2;
                    break;
                case ReportOverviewFragment.MONTH_DETAIL:
                    position = 3;
                    break;
                case ReportOverviewFragment.YEAR_DETAIL:
                    position = 4;
                    break;
                default:
                    break;
            }
            mIReportPresenter.getReportByTime(this, position);
            mIReportPresenter.getListTime(this, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setType(int type) {
        mType = type;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report_overview_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            RecyclerView recyclerView = view.findViewById(R.id.rv_report_overview_detail);

            OverviewDetailAdapter overviewDetailAdapter = new OverviewDetailAdapter(mListReport, mType);
            overviewDetailAdapter.setListener(this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppContext.getInstance(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setAdapter(overviewDetailAdapter);
            recyclerView.setLayoutManager(linearLayoutManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getReportAllTimeDone(ArrayList<ArrayList<Long>> result) {
    }

    @Override
    public void getReportByTimeDone(ArrayList<Long> result) {
        try {
            mListReport = result;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getListTimeDone(ArrayList<Long> listTime) {
        try {
            mListTime = listTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickItem(int layoutPosition) {
        Intent intent = new Intent(getContext(), ReportDetailActivity.class);
        intent.putExtra("startTime", mListTime.get(layoutPosition));
        intent.putExtra("endTime", mListTime.get(layoutPosition + 1) - 1);
        startActivity(intent);
    }
}
