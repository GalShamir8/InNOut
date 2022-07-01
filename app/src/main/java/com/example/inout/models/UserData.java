package com.example.inout.models;

import com.example.inout.common.TimeClock;

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
           hoursData.put(year, yearData);
        }
        yearData.setData(month, day, start, end);
    }

    public MonthData getMonthData(int year, int month) {
        try{
            return hoursData.get(year).getMonthsData().get(month);
        }catch (NullPointerException e){
            return null;
        }
    }
}
