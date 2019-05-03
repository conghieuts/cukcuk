package vn.com.misa.hieudc.cukcuklite.screen.mainscreen.reportscreen.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Objects;

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

    private LineChart lcChart;
    private TextView tvVertical, tvHorizontal;

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
        initView(view);
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
        switch (mType) {
            case ReportOverviewFragment.WEEK_DETAIL:
                try {
                    tvVertical.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.title_week_vertical_report));
                    tvHorizontal.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.title_week_horizontal_report));
                    showLineChart(7, 7);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case ReportOverviewFragment.MONTH_DETAIL:
                try {
                    tvVertical.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.title_month_vertical_report));
                    tvHorizontal.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.title_month_horizontal_report));
                    showLineChart(8, 29);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case ReportOverviewFragment.YEAR_DETAIL:
                try {
                    tvVertical.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.title_year_vertical_report));
                    tvHorizontal.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.title_year_horizontal_report));
                    showLineChart(12, 12);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 5/2/2019
     */
    private void initView(View view) {
        try {
            lcChart = view.findViewById(R.id.lcChart);
            tvVertical = view.findViewById(R.id.tvVertical);
            tvHorizontal = view.findViewById(R.id.tvHorizontal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getReportAllTimeDone(ArrayList<ArrayList<Long>> result) {
    }


    /**
     * Created_by: dchieu
     * Created_date: 5/2/2019
     */
    @Override
    public void getReportByTimeDone(ArrayList<Long> result) {
        try {
            mListReport = result;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 5/2/2019
     */
    @Override
    public void getListTimeDone(ArrayList<Long> listTime) {
        try {
            mListTime = listTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Created_by: dchieu
     * Created_date: 5/2/2019
     * chuyển màn hình chi tiết khi click vào 1 ngày
     */
    @Override
    public void onClickItem(int layoutPosition) {
        try {
            Intent intent = new Intent(getContext(), ReportDetailActivity.class);
            intent.putExtra("startTime", mListTime.get(layoutPosition));
            intent.putExtra("endTime", mListTime.get(layoutPosition + 1) - 1);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Created_by: dchieu
     * Created_date: 5/2/2019
     * Hiển thị biểu đồ đường cho tuần, tháng, năm
     * @param count số mục ngang
     * @param size giá trị ngang lớn nhất
     */
    private void showLineChart(int count, final int size) {
        try {
            ArrayList<Entry> listEntry = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                Entry entry;

                if (i < mListReport.size()) {
                    long money = 0;
                    if (!TextUtils.isEmpty(mListReport.get(i).toString())) {
                        money = mListReport.get(i);
                    }

                    if (count == 12 && size == 12) {
                        entry = new Entry(i + 1, (float) money / 1000000);
                    } else {
                        entry = new Entry(i + 1, (float) money / 1000);
                    }
                    listEntry.add(entry);
                }
            }
            LineDataSet dataSet = new LineDataSet(listEntry, null);
            dataSet.setValueTextSize(0f);
            dataSet.setCircleColor(Color.TRANSPARENT);
            dataSet.setCircleHoleColor(Color.GREEN);
            dataSet.setColor(Color.GREEN);
            LineData lineData = new LineData(dataSet);

            lcChart.setDrawGridBackground(false);
            lcChart.setDescription(null);
            lcChart.setTouchEnabled(false);

            XAxis xAxis = lcChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);
            xAxis.setAxisMinimum(1f);
            xAxis.setAxisMaximum(size);
            xAxis.setLabelCount(count, true);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    String title = "";

                    switch (size) {
                        case 7:
                            int day = (int) value + 1;
                            if (day != 8) {
                                title = "T" + day;
                            } else {
                                title = "CN";
                            }

                            break;
                        case 29:
                            title = (int) value + "";
                            break;
                        case 12:
                            title = (int) value + "";
                            break;
                    }

                    return title;
                }
            });

            YAxis axisLeft = lcChart.getAxisLeft();
            axisLeft.enableGridDashedLine(5.0f, 5.0f, 0.0f);
            axisLeft.setAxisLineColor(Color.TRANSPARENT);
            axisLeft.setAxisMinimum(0f);

            lcChart.getLegend().setEnabled(false);
            lcChart.getAxisRight().setEnabled(false);

            lcChart.setData(lineData);
            lcChart.notifyDataSetChanged();
            lcChart.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
