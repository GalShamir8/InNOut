package com.example.inout.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inout.R;
import com.example.inout.utils.Helper;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Calendar;
import java.util.HashMap;

public class ReportFragment extends Fragment {
    private View view;
    private PieChart report_pieChart;
    private TextView report_TXT_reported;
    private TextView report_TXT_left;
    private TextView report_TXT_missing;
    private TextView report_TXT_total;
    private TextView report_TXT_title;


    public ReportFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);
        setViews();
        setData();
        return view;
    }

    private void setViews() {
        report_pieChart = view.findViewById(R.id.report_pieChart);
        report_TXT_reported = view.findViewById(R.id.report_TXT_reported);
        report_TXT_left = view.findViewById(R.id.report_TXT_left);
        report_TXT_missing = view.findViewById(R.id.report_TXT_missing);
        report_TXT_total = view.findViewById(R.id.report_TXT_total);
        report_TXT_title = view.findViewById(R.id.report_TXT_title);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setData() {
        HashMap<String, Integer> reportData = Helper.getInstance().getReportData(
                Calendar.getInstance().get(Calendar.MONTH) + 1); // +1 for index 0 offset
        setLabelsData(reportData);
        int leftDays = reportData.get(Helper.getInstance().getLeftDaysKey());
        int reportedDays = reportData.get(Helper.getInstance().getReportedDaysKey());
        int missingReportDays = reportData.get(Helper.getInstance().getMissingDaysKey());
        report_pieChart.addPieSlice(new PieModel(
               getString(R.string.left), leftDays, Color.GRAY));
        report_pieChart.addPieSlice(new PieModel(
                getString(R.string.reported), reportedDays, Color.GREEN));
        report_pieChart.addPieSlice(new PieModel(
                getString(R.string.missing_report),missingReportDays , Color.RED));
        report_pieChart.startAnimation();
    }

    private void setLabelsData(HashMap<String, Integer> reportData) {
        int leftDays = reportData.get(Helper.getInstance().getLeftDaysKey());
        int reportedDays = reportData.get(Helper.getInstance().getReportedDaysKey());
        int missingReportDays = reportData.get(Helper.getInstance().getMissingDaysKey());
        int totalDaysInMonth = reportData.get(Helper.getInstance().getDaysMonthKey());
        report_TXT_reported.setText(getString(R.string.reported) + " "  + reportedDays);
        report_TXT_left.setText(getString(R.string.left) + " " + leftDays);
        report_TXT_missing.setText(getString(R.string.missing_report) + " " +  missingReportDays);
        report_TXT_total.setText(totalDaysInMonth + " " + getString(R.string.days_in_month));
        report_TXT_title.setText(getString(R.string.month_report));
    }
}