package vn.com.misa.hieudc.cukcuklite.screen.reportdetailscreen;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import vn.com.misa.hieudc.cukcuklite.R;
import vn.com.misa.hieudc.cukcuklite.model.CheckoutItem;
import vn.com.misa.hieudc.cukcuklite.screen.reportdetailscreen.adapter.ReportItemAdapter;

public class ReportDetailActivity extends AppCompatActivity implements IReportDetailView {
    private IReportDetailPresenter mIReportDetailPresenter;
    private PieChart mPieChart;
    ArrayList<CheckoutItem> mListCheckoutItem;
    ArrayList<String> mTimeListString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_normal);
        mTimeListString = getIntent().getStringArrayListExtra("time");
        long startTime = Calendar.getInstance().getTimeInMillis();
        long endTime = startTime;
        if (mTimeListString != null) {
            if (mTimeListString.size() >= 2) {
                startTime = Long.valueOf(mTimeListString.get(0));
                endTime = Long.valueOf(mTimeListString.get(mTimeListString.size() - 1));
            }
        }
        mIReportDetailPresenter = new ReportDetailPresenter(this, startTime, endTime);
        mIReportDetailPresenter.getReportDetail();
        initListReport();
        mPieChart = findViewById(R.id.pieChart);
        showPieChart();
    }

    private void initListReport() {
        RecyclerView recyclerView = findViewById(R.id.rv_detail_report);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ReportItemAdapter reportItemAdapter = new ReportItemAdapter(this, mListCheckoutItem);
        recyclerView.setAdapter(reportItemAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getReportDone(ArrayList<CheckoutItem> checkoutItems) {
        mListCheckoutItem = checkoutItems;
    }

    private void showPieChart() {
        try {
            if (mListCheckoutItem != null) {
                Collections.sort(mListCheckoutItem, new Comparator<CheckoutItem>() {
                    @Override
                    public int compare(CheckoutItem report1, CheckoutItem report2) {
                        return report2.getTotalPaid() > report1.getTotalPaid() ? 1 : -1;
                    }
                });

                String[] strColors = getResources().getStringArray(R.array.color_report);
                int[] intColors = new int[7];
                intColors[6] = getResources().getColor(R.color.colorGrey);
                for (int i = 0; i < 6; i++) {
                    intColors[i] = Color.parseColor(strColors[i]);
                }

                mPieChart.setUsePercentValues(true);
                mPieChart.setDescription(null);

                float margin = getResources().getDimension(R.dimen.layout_margin);
                mPieChart.setExtraOffsets(margin, margin, margin, margin);
                mPieChart.setHoleRadius(65.0f);
                mPieChart.setDrawCenterText(true);
                mPieChart.setRotationAngle(0.0f);
                mPieChart.setRotationEnabled(false);
                mPieChart.setHighlightPerTapEnabled(false);

                ArrayList<PieEntry> listPieEntry = new ArrayList<>();
                float totalRevenue = 0;
                float totalRemain = 0;
                for (int i = 0; i < mListCheckoutItem.size(); i++) {
                    CheckoutItem checkoutItem = mListCheckoutItem.get(i);
                    Float totalMoney = (float) checkoutItem.getTotalPaid();

                    if (i < 6) {
                        PieEntry pieEntry = new PieEntry(totalMoney);
                        listPieEntry.add(pieEntry);
                    } else {
                        totalRemain += totalMoney;

                        if (i == mListCheckoutItem.size() - 1) {
                            PieEntry pieEntry = new PieEntry(totalRemain);
                            listPieEntry.add(pieEntry);
                        }
                    }
                    totalRevenue += totalMoney;
                }

                PieDataSet pieDataSet = new PieDataSet(listPieEntry, null);
                pieDataSet.setColors(intColors);
                pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
                pieDataSet.setValueTextSize(16);
                PieData data = new PieData(pieDataSet);
                data.setValueFormatter(new PercentFormatter(mPieChart));

                mPieChart.setData(data);

                String textCenter = getString(R.string.total_revenue);
                String textTotalRevenue = NumberFormat.getInstance().format(totalRevenue);
                SpannableString spannableString = new SpannableString(textCenter + "\n" + textTotalRevenue);
                spannableString.setSpan(new RelativeSizeSpan(2f), textCenter.length() + 1, spannableString.length(), 0);
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlack)), textCenter.length() + 1, spannableString.length(), 0);

                spannableString.setSpan(new RelativeSizeSpan(1.2f), 0, textCenter.length(), 0);
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlack)), 0, textCenter.length(), 0);

                mPieChart.setCenterText(spannableString);
                mPieChart.animateY(1000);
                mPieChart.getLegend().setEnabled(false);
                mPieChart.invalidate();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
}
