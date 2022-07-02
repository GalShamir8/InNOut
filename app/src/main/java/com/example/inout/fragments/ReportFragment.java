package com.example.inout.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inout.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class ReportFragment extends Fragment {
    private View view;
    private PieChart report_pieChart;


    public ReportFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

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

    private void setData() {
        report_pieChart.addPieSlice(new PieModel(
               getString(R.string.left), 10, Color.GRAY));
        report_pieChart.addPieSlice(new PieModel(
                getString(R.string.reported), 70, Color.GREEN));
        report_pieChart.addPieSlice(new PieModel(
                getString(R.string.missing_report),20 , Color.RED));
        report_pieChart.startAnimation();

    }
}