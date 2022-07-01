package com.example.inout.models;

import com.example.inout.common.TimeClock;
import com.example.inout.utils.MyFirebase;

import java.util.HashMap;

public class User {
    private String uuid = "";
    private HashMap<String, YearDataHelper> timeClockData;

    public User() {
        timeClockData = new HashMap<>();
    }

    public void buildUserTimeClockData(String year, String month, String day, TimeClock start, TimeClock end){
        HashMap<String, YearDataHelper.MonthDataHelper> monthsData = new HashMap<>();
        YearDataHelper.MonthDataHelper monthData = new YearDataHelper.MonthDataHelper()
                .addDayData(MyFirebase.START_TIME_KEY, day, start)
                .addDayData(MyFirebase.END_TIME_KEY, day, end)
                .setMonth(month);
        monthsData.put(month, monthData);
        timeClockData.put(year, new YearDataHelper().setYear(year).setMonthsData(monthsData));
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public HashMap<String, YearDataHelper> getTimeClockData() {
        return timeClockData;
    }

    public void setTimeClockData(HashMap<String, YearDataHelper> timeClockData) {
        this.timeClockData = timeClockData;
    }

    public YearDataHelper.MonthDataHelper getMonthData(int year, int month) {
        return timeClockData.get(String.valueOf(year)).getMonthsData().get(month);
    }
}