package com.example.inout.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyFirebase {

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