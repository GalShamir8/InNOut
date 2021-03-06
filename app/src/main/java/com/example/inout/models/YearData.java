package com.example.inout.models;

import com.example.inout.common.TimeClock;
import com.example.inout.utils.MyFirebase;

import java.util.HashMap;

public class YearData {
    private HashMap<String, MonthData> monthsData;

    public YearData() {
        monthsData = new HashMap();
    }

    public HashMap<String, MonthData> getMonthsData() {
        return monthsData;
    }

    public YearData setMonthsData(HashMap<String, MonthData> monthsData) {
        this.monthsData = monthsData;
        return this;
    }

    public void setData(String month, String day, TimeClock start, TimeClock end) {
        MonthData monthData = monthsData.get(MyFirebase.keyWrapper(month));
        if (monthData == null){
            monthData = new MonthData();
        }
        monthData.setData(day, start, end);
        monthsData.put(MyFirebase.keyWrapper(month), monthData);
    }
}
