package com.example.inout.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.inout.R;
import com.example.inout.common.Callable;

import java.util.Calendar;


public class UpdateHoursFragment extends Fragment {
    private enum eValidTime {eSuccess, eError};
    private TimePicker updateHour_start;
    private TimePicker updateHour_end;

    public void setOnSaveCallback(Callable onSaveCallback) {
        this.onSaveCallback = onSaveCallback;
    }

    private Callable onSaveCallback;

    public UpdateHoursFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_hours, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        updateHour_start = view.findViewById(R.id.updateHour_start);
        updateHour_end = view.findViewById(R.id.updateHour_end);
        setTimeDefaultDisplay();
        setListeners();
    }

    private void setListeners() {
        updateHour_end.setOnTimeChangedListener((timePicker, hours, minutes) ->
                handleTimeChange(hours, minutes));
    }

    private void handleTimeChange(int hours, int minutes) {
        if (isValidEndTime(hours, minutes)){
            setLabel(eValidTime.eSuccess);
            save();
        }
        setLabel(eValidTime.eError);
    }

    private void setLabel(eValidTime eError) {
    }

    private boolean isValidEndTime(int endHour, int endMinute) {
        int startHour = updateHour_start.getHour();
        int startMinute = updateHour_start.getMinute();
        if(startHour == endHour){
            return startMinute < endMinute;
        }
        return startHour < endHour;
    }

    private void save() {
    }

    private void setTimeDefaultDisplay() {
        updateHour_start.setIs24HourView(true);
        updateHour_end.setIs24HourView(true);
        setCurrentTime(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE));
    }

    private void setCurrentTime(int hour, int minutes) {
        updateHour_start.setHour(hour);
        updateHour_start.setMinute(minutes);

        updateHour_end.setHour(hour);
        updateHour_end.setMinute(minutes);
    }
}