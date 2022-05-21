package com.example.inout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

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
        toolBar.setSubtitleCentered(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        actionBar.setSubtitle(getCurrentTitle());
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.app_icon);
    }

    private String getCurrentTitle() {
        // TODO: 21/05/2022 add User resolving
        return false ? "" : "Logged in as Gal";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
}