package com.example.inout.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inout.R;
import com.example.inout.utils.Helper;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Calendar;
import java.util.HashMap;

public class ReportFragment extends Fragment {
    private View view;
    private PieChart report_pieChart;


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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setData() {
        HashMap<String, Integer> reportData = Helper.getInstance().getReportData(
                Calendar.getInstance().get(Calendar.MONTH) + 1); // +1 for index 0 offset
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
}