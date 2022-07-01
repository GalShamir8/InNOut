package com.example.inout.models;

import com.example.inout.common.TimeClock;

import java.util.HashMap;

public class YearDataHelper {
     private String year;
     private HashMap<String, MonthDataHelper> monthsData;

     public YearDataHelper() {
          monthsData = new HashMap<>();
     }

     public String getYear() {
          return year;
     }

     public YearDataHelper setYear(String year) {
          this.year = year;
          return this;
     }

     public HashMap<String, MonthDataHelper> getMonthsData() {
          return monthsData;
     }

     public YearDataHelper setMonthsData(HashMap<String, MonthDataHelper> monthsData) {
          this.monthsData = monthsData;
          return this;
     }

     public static class MonthDataHelper {
          private String month;
          private HashMap<String, HashMap<String, TimeClock>> daysData;

          public MonthDataHelper() {
               daysData = new HashMap<>();
          }

          public String getMonth() {
               return month;
          }

          public MonthDataHelper setMonth(String month) {
               this.month = month;
               return this;
          }

          public HashMap<String, HashMap<String, TimeClock>> getDaysData() {
               return daysData;
          }

          public MonthDataHelper setDaysData(HashMap<String, HashMap<String, TimeClock>> daysData) {
               this.daysData = daysData;
               return this;
          }

          public MonthDataHelper addDayData(String key, String day, TimeClock timeClock){
               HashMap<String, TimeClock> dayData = new HashMap<>();
               dayData.put(day, timeClock);
               daysData.put(key, dayData);
               return this;
          }
     }
}
