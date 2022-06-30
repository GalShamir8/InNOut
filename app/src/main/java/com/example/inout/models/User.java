package com.example.inout.models;

import com.example.inout.common.TimeClock;
import com.example.inout.utils.MyFirebase;

import java.util.HashMap;

public class User {
    private String uuid = "";
    /*  the map structure is as follow:
        year: {
            month: [
                day: {
                    startTime: TimeClock,
                    endTime: TimeClock
                }
            ]
       }
     */
    private boolean isFirstLogin = false;
    private HashMap<String, YearDataHelper> timeClockData;

    public User() {
        timeClockData = new HashMap<>();
    }

    public void buildUserTimeClockData(String year, String month, String day, TimeClock start, TimeClock end){
        timeClockData.put(year, new YearDataHelper()
                .setYear(year)
                .addMonthData(new YearDataHelper
                        .MonthDataHelper()
                        .addDayData(MyFirebase.START_TIME_KEY, day, start)
                        .addDayData(MyFirebase.END_TIME_KEY, day, end)));
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

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }
}
