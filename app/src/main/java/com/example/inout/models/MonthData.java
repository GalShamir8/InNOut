package com.example.inout.models;

import com.example.inout.common.TimeClock;
import com.example.inout.utils.MyFirebase;

import java.util.HashMap;

public class MonthData {
    private HashMap<String, DayData> daysData;

    public MonthData() {
        daysData = new HashMap<>();
    }

    public HashMap<String, DayData> getDaysData() {
        return daysData;
    }

    public MonthData setDaysData(HashMap<String, DayData> daysData) {
        this.daysData = daysData;
        return this;
    }

    public void setData(String day, TimeClock start, TimeClock end) {
        DayData dayData = daysData.get(day);
        if (dayData == null){
            dayData = new DayData();
        }
        dayData.setStart(start).setEnd(end);
        daysData.put(MyFirebase.KEY_PREFIX + day, dayData);
    }
}
