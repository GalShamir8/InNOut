package com.example.inout.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.inout.common.Callable;
import com.example.inout.common.TimeClock;
import com.example.inout.models.MonthData;
import com.example.inout.models.User;

import com.example.inout.models.UserData;
import com.example.inout.models.YearDataHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;


public class MyFirebase {
    private static final String DATABASE_URL = "https://innout-acc3b-default-rtdb.firebaseio.com";
    private static final String USERS_PATH = "users";
    private static final String DATE_DELIMITER = "/";
    private static final String ENTER_FLAG = "isFirstEnter";
    public static final String START_TIME_KEY = "start";
    public static final String END_TIME_KEY = "end";
    private static final int DAY_POS = 2;
    private static final int MONTH_POS = 1;
    private static final int YEAR_POS = 0;

    private static MyFirebase firebaseInstance = null;
    private FirebaseDatabase db;
    private DatabaseReference root;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private UserData userData;

    private MyFirebase() {
        db = FirebaseDatabase.getInstance(DATABASE_URL);
        root = db.getReference();
        auth = FirebaseAuth.getInstance();
        userData = new UserData();
    }

    public static MyFirebase getInstance(){
        if (firebaseInstance == null){
            firebaseInstance = new MyFirebase();
        }
        return firebaseInstance;
    }

    public void setUserData(){
        if (getUser() != null){
            readUserData();
        }
    }

    public MonthData getUserMonthData(int month) {
        readUserData();
        return userData.getMonthData(Calendar.getInstance().get(Calendar.YEAR), month);
    }

    public boolean saveUserTimeClock(TimeClock start, TimeClock end, String date) {
        String year;
        String month;
        String day;
        try {
            year = extractYearFromDateStr(date);
            month = extractMonthFromDateStr(date);
            day = extractDayFromDateStr(date);
        }catch(IndexOutOfBoundsException e){
            return false;
        }
        userData.setData(year, month, day, start, end);
        root.child(USERS_PATH).child(getUser().getUid()).setValue(userData);

        return true;
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
        return str.split(delimiter)[position];
    }

    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

    public void readUserData() {
        root.child(USERS_PATH).child(getUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getValue(UserData.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public void handleLogin(String username, String password, Callable onSuccess, Callable onFail){
        auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onSuccess.call();
            } else {
                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                onFail.call(task.getException().getMessage());
            }
        });
    }
}