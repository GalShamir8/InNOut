package com.example.inout.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyFirebase {
    private static final String DATABASE_URL = "https://innout-acc3b-default-rtdb.firebaseio.com";
    private static final String START_HOUR_KEY = "startHour";
    private static final String START_MINUTE_KEY = "startMin";
    private static final String END_HOUR_KEY = "endHour";
    private static final String END_MINUTE_KEY = "endMin";
    private static final String USERS_PATH = "users";
    private static MyFirebase firebaseInstance = null;
    FirebaseDatabase db;
    private MyFirebase() {
        db = FirebaseDatabase.getInstance();
    }

    public static MyFirebase getInstance(){
        if (firebaseInstance == null){
            firebaseInstance = new MyFirebase();
        }
        return firebaseInstance;
    }

}