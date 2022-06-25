package com.example.inout.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.example.inout.R;
import com.example.inout.common.Callable;
import com.example.inout.common.TimeClock;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Calendar;


public class UpdateHoursFragment extends Fragment {
    private enum eStatus {success, error};
    private static final String SUCCESS_MSG = "Valid Hours";
    private static final String ERROR_MSG = "Invalid End Hours";
    private static final String FAILED_SAVE_MSG = "Failed to save changes";
    private static final String SUCCESSFULLY_SAVED = "saved";
    private static final String ERROR_TAG = "error";

    private MaterialTextView updateHour_LBL_status;
    private TimePicker updateHour_start;
    private TimePicker updateHour_end;
    private MaterialButton updateHour_BTN_save;

    private TimeClock startTimeClock;
    private TimeClock endTimeClock;

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
        updateHour_LBL_status = view.findViewById(R.id.updateHour_LBL_status);
        updateHour_start = view.findViewById(R.id.updateHour_start);
        updateHour_end = view.findViewById(R.id.updateHour_end);
        updateHour_BTN_save = view.findViewById(R.id.updateHour_BTN_save);
        setTimeDefaultDisplay();
        setListeners();
    }

    private void setListeners() {
        updateHour_start.setOnTimeChangedListener((timePicker, hours, minutes) ->
                handleTimeChange(new TimeClock(
                        updateHour_end.getHour(),
                        updateHour_end.getMinute())
                ));
        updateHour_end.setOnTimeChangedListener((timePicker, hours, minutes) ->
                handleTimeChange(new TimeClock(hours, minutes)));
        updateHour_BTN_save.setOnClickListener(e -> save());
    }

    private void handleTimeChange(TimeClock endTimeClock) {
        TimeClock startTimeClock = new TimeClock(updateHour_start.getHour(),
                updateHour_start.getMinute());
        int condition = endTimeClock.compareTo(startTimeClock);
        if (condition > 0){
            setLabel(eStatus.success, SUCCESS_MSG);
            setTime(startTimeClock, endTimeClock);
        }else {
            setLabel(eStatus.error, ERROR_MSG);
        }
    }

    private void setTime(TimeClock startTimeClock, TimeClock endTimeClock) {
        this.startTimeClock = startTimeClock;
        this.endTimeClock = endTimeClock;
    }


    private void setLabel(eStatus lblStatus, String message) {
        int color = 0;
        switch (lblStatus){
            case success:
                color = Color.GREEN;
                break;
            case error:
                color = Color.RED;
                break;
        }
        updateHour_LBL_status.setText(message);
        updateHour_LBL_status.setTextColor(color);
    }

    private void save() {
        if(!onSaveCallback.call(startTimeClock, endTimeClock)){
            setLabel(eStatus.error, FAILED_SAVE_MSG);
            Log.e(ERROR_TAG, FAILED_SAVE_MSG);
        }else{
            setLabel(eStatus.success, SUCCESSFULLY_SAVED);
        }
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