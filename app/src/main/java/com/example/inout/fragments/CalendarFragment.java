package com.example.inout.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.fragment.app.Fragment;

import com.example.inout.R;
import com.example.inout.common.Callable;

public class CalendarFragment extends Fragment {

    public void setOnDatePickCallback(Callable onDatePickCallback) {
        this.onDatePickCallback = onDatePickCallback;
    }

    private Callable onDatePickCallback;
    public CalendarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        CalendarView calendar = view.findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener((calendarView, year, month, day) -> {
            if (onDatePickCallback != null){
                onDatePickCallback.call(year, month, day);
            }
        });
        return view;
    }
}