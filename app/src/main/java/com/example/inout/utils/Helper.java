package com.example.inout.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.inout.models.DayData;
import com.example.inout.models.MonthData;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Helper {
    private static final String DAYS_MONTH_KEY = "daysInMonth";
     private static final String REPORTED_DAYS_KEY = "reportedDays";
     private static final String LEFT_DAYS_KEY = "leftDays";
     private static final String MISSING_DAYS_KEY = "missingDays";

    private static Helper helperInstance = null;

    private Helper(){ }

    public static Helper getInstance(){
        if (helperInstance == null){
            helperInstance = new Helper();
        }
        return helperInstance;
    }

    public String getDaysMonthKey() {
        return DAYS_MONTH_KEY;
    }

    public String getReportedDaysKey() {
        return REPORTED_DAYS_KEY;
    }

    public String getLeftDaysKey() {
        return LEFT_DAYS_KEY;
    }

    public String getMissingDaysKey() {
        return MISSING_DAYS_KEY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private int getMonthMaxDays(int month){
        YearMonth yearMonthObject = YearMonth.of(Calendar.getInstance().get(Calendar.YEAR), month);
        return yearMonthObject.lengthOfMonth();
    }

    private ArrayList<Integer> getWeekendDaysInMonth(int month, int maxDaysInMonth) {
        ArrayList<Integer> weekendDays = new ArrayList<>();
        for (int i = 1; i <= maxDaysInMonth ; i++) {
            Calendar c = Calendar.getInstance();
            c.set(c.get(Calendar.YEAR), month, i);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.FRIDAY){
                weekendDays.add(i);
            }
        }
        return weekendDays;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public HashMap<String, Integer> getReportData(int month){
        HashMap<String, Integer> data = new HashMap<>();
        int maxDaysInMonth = getMonthMaxDays(month);
        ArrayList<Integer> weekendDays = getWeekendDaysInMonth(month, maxDaysInMonth);
        data.put(DAYS_MONTH_KEY, maxDaysInMonth);
        data.put(REPORTED_DAYS_KEY, getReportedDaysInMonth(month));
        data.put(LEFT_DAYS_KEY, getLeftReportedDaysInMonth(month, maxDaysInMonth, weekendDays));
        data.put(MISSING_DAYS_KEY, getMissingReportedDaysInMonth(month, weekendDays));
        return data;
    }

    private int getMissingReportedDaysInMonth(int month, ArrayList<Integer> weekendDays) {
        MonthData monthData = MyFirebase.getInstance().getUserMonthData(month);
        if (monthData == null){
            int count = 0;
            for (int i = Calendar.getInstance().get(Calendar.DAY_OF_MONTH); i > 0 ; i--) {
                if (!weekendDays.contains(i)){
                    count++;
                }
            }
            return count;
        }
        int count = 0;
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        for (int i = currentDay; i > 0 ; i--) {
            DayData dayData = monthData.getDaysData().get(MyFirebase.keyWrapper(String.valueOf(i)));
            if (dayData == null && !weekendDays.contains(i)){
                count++;
            }
        }
        return count;
    }

    private int getLeftReportedDaysInMonth(int month, int maxDaysInMonth, ArrayList<Integer> weekendDays) {
        MonthData monthData = MyFirebase.getInstance().getUserMonthData(month);
        if (monthData == null){
            return maxDaysInMonth - weekendDays.size() - Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }
        int count = 0;
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        for (int i = currentDay; i <= maxDaysInMonth; i++) {
            DayData dayData = monthData.getDaysData().get(MyFirebase.keyWrapper(String.valueOf(i)));
            if (dayData == null && !weekendDays.contains(i)){
                count++;
            }
        }
        return count;
    }

    private int getReportedDaysInMonth(int month) {
        MonthData monthData = MyFirebase.getInstance().getUserMonthData(month);
        if (monthData == null){
            return 0;
        }
        return monthData.getDaysData().size();
    }
}
