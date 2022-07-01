package com.example.inout.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.inout.common.Callable;
import com.example.inout.common.TimeClock;
import com.example.inout.models.MonthData;

import com.example.inout.models.UserData;
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
    public static final String KEY_PREFIX = "_";
    private static final String USERS_PATH = "users";
    private static final String DATE_DELIMITER = "/";
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
        db = FirebaseDatabase.getInstance();
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

    public void logout() {
        auth.signOut();
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
        HashMap<String, UserData> data = new HashMap<>();
        data.put(getUser().getUid(), userData);
        root.child(USERS_PATH).setValue(data);

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
                UserData userDataSnapshot = snapshot.getValue(UserData.class);
                if (userDataSnapshot != null) {
                    userData = userDataSnapshot;
                }
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