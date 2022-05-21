package com.example.inout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();
    }

    private void setActionBar() {
        MaterialToolbar toolBar = findViewById(R.id.app_actionBar);
        toolBar.setBackgroundColor(0xFF018786);
        setSupportActionBar(toolBar);
        toolBar.setTitleCentered(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getCurrentTitle());
        actionBar.setIcon(R.drawable.app_icon);
    }

    private String getCurrentTitle() {
        String suffix =false ? "" : " | Logged in as Gal";
        return getResources().getString(R.string.app_name) + suffix;
    }


}