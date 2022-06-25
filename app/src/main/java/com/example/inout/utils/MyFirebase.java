package com.example.inout.utils;

import com.example.inout.common.TimeClock;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MyFirebase {
    private static final String DATABASE_URL = "https://innout-acc3b-default-rtdb.firebaseio.com";
    private static final String DAY_KEY = "day";
    private static final String START_HOUR_KEY = "startHour";
    private static final String START_MINUTE_KEY = "startMin";
    private static final String END_HOUR_KEY = "endHour";
    private static final String END_MINUTE_KEY = "endMin";
    private static final String USERS_PATH = "users";
    private static final String DATE_DELIMITER = "/";
    private static final int DAY_POS = 2;
    private static final int MONTH_POS = 1;
    private static final int YEAR_POS = 0;

    private static MyFirebase firebaseInstance = null;
    private FirebaseDatabase db;
    private DatabaseReference root;

    private MyFirebase() {
        db = FirebaseDatabase.getInstance(DATABASE_URL);
        root = db.getReference();
    }

    public static MyFirebase getInstance(){
        if (firebaseInstance == null){
            firebaseInstance = new MyFirebase();
        }
        return firebaseInstance;
    }

    public void saveUserTimeClock(TimeClock start, TimeClock end, String date) {
        // Get user data
        // TODO: 25/06/2022 add user uid resolve
        String userUid = "tmp_uid";
        String year = extractYearFromDateStr(date);
        String month = extractMonthFromDateStr(date);
        int day;
        try {
            day = Integer.parseInt(extractDayFromDateStr(date));
        }catch (NumberFormatException e){
            day = 0;
        }
        HashMap<String, Integer> data = new HashMap<>();
        data.put(DAY_KEY, day);
        data.put(START_HOUR_KEY, start.getHour());
        data.put(START_MINUTE_KEY, start.getMinute());
        data.put(END_HOUR_KEY, end.getHour());
        data.put(END_MINUTE_KEY, end.getMinute());
        root.child(USERS_PATH).child(userUid).child(year).child(month).setValue(data);
    }

    private String extractDayFromDateStr(String date) {
        return extractFromSplitStr(date, DATE_DELIMITER, DAY_POS);
    }

    private String extractMonthFromDateStr(String date) {
        return extractFromSplitStr(date, DATE_DELIMITER, MONTH_POS);
    }

    private String extractYearFromDateStr(String date) {
        return extractFromSplitStr(date, DATE_DELIMITER, YEAR_POS);
    }

    private String extractFromSplitStr(String str, String delimiter, int position) {
        try{
            return str.split(delimiter)[position];
        }catch (IndexOutOfBoundsException e){
            return "0";
        }
    }
}