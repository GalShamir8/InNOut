package com.example.inout.models;

import com.example.inout.common.TimeClock;
import com.example.inout.utils.MyFirebase;

import java.util.HashMap;

public class UserData {
    private HashMap<String, YearData> hoursData;

    public UserData() {
        hoursData = new HashMap<>();
    }

    public HashMap<String, YearData> getHoursData() {
        return hoursData;
    }

    public UserData setHoursData(HashMap<String, YearData> hoursData) {
        this.hoursData = hoursData;
        return this;
    }

    public void setData(String year, String month, String day, TimeClock start, TimeClock end) {
        YearData yearData = hoursData.get(year);
        if (yearData == null){
            yearData = new YearData();
        }
        yearData.setData(month, day, start, end);
        hoursData.put(MyFirebase.KEY_PREFIX + year, yearData);
    }

    public MonthData getMonthData(int year, int month) {
        try{
            return hoursData.get(MyFirebase.KEY_PREFIX + year).getMonthsData()
                    .get(MyFirebase.KEY_PREFIX +month);
        }catch (NullPointerException e){
            return null;
        }
    }
}
