package com.example.inout.utils;

import com.example.inout.common.TimeClock;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MyFirebase {
    private static final String DATABASE_URL = "https://innout-acc3b-default-rtdb.firebaseio.com";
    private static final String START_HOUR_KEY = "startHour";
    private static final String START_MINUTE_KEY = "startMin";
    private static final String END_HOUR_KEY = "endHour";
    private static final String END_MINUTE_KEY = "endMin";
    private static final String USERS_PATH = "users";
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
        HashMap<String, Integer> data = new HashMap<>();
        data.put(START_HOUR_KEY, start.getHour());
        data.put(START_MINUTE_KEY, start.getMinute());
        data.put(END_HOUR_KEY, end.getHour());
        data.put(END_MINUTE_KEY, end.getMinute());
        root.child(USERS_PATH).child(userUid).setValue(data);
    }

}