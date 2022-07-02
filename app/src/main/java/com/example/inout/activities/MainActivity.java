package com.example.inout.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.inout.R;
import com.example.inout.common.TimeClock;
import com.example.inout.fragments.CalendarFragment;
import com.example.inout.fragments.ReportFragment;
import com.example.inout.fragments.TableFragment;
import com.example.inout.fragments.UpdateHoursFragment;
import com.example.inout.fragments.UpdateProfileFragment;
import com.example.inout.utils.MyFirebase;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {
    private final String IMG_URL = "https://images.unsplash.com/photo-1609114214822-d7c54701055c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDE4fHx8ZW58MHx8fHw%3D&w=1000&q=80";
    private String chosenDate;
    private ImageView  main_IMG_background;
    private enum eViewState {background, center, bottom};
    private TextView main_TXT_appTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();
        setBackground();
        setAppTitle();
        setUserView();
    }

    private void setAppTitle() {
        main_TXT_appTitle = findViewById(R.id.main_TXT_appTitle);
    }


    private void setBackground() {
        main_IMG_background = (ImageView) findViewById(R.id.main_IMG_background);
        Glide.with(this).load(IMG_URL).into(main_IMG_background);
    }

    private void setUserView() {
        setViewVisibility(eViewState.background, View.VISIBLE);
        setViewVisibility(eViewState.center, View.VISIBLE);
        setViewVisibility(eViewState.bottom, View.VISIBLE);
        TableFragment tableFragment = new TableFragment();
        loadFragment(R.id.main_center_frame, tableFragment);
        ReportFragment reportFragment = new ReportFragment();
        loadFragment(R.id.main_bottom_frame, reportFragment);
    }

    private void setViewVisibility(eViewState viewStatus, int visibility) {
        switch(viewStatus){
            case background:
                main_IMG_background.setVisibility(visibility);
                main_TXT_appTitle.setVisibility(visibility);
                break;
            case bottom:
                findViewById(R.id.main_bottom_frame).setVisibility(visibility);
                break;
            case center:
                findViewById(R.id.main_center_frame).setVisibility(visibility);
                break;
        }
    }

    private void setActionBar() {
        MaterialToolbar toolBar = findViewById(R.id.app_actionBar);
        toolBar.setBackgroundColor(getResources().getColor(R.color.teal_700, this.getTheme()));
        setSupportActionBar(toolBar);
        setActionBarTitle(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setActionBarTitle(MaterialToolbar toolBar) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.app_name));
            actionBar.setSubtitle(getCurrentTitle());

            toolBar.setTitleCentered(true);
            toolBar.setSubtitleCentered(true);
        }
    }

    private String getCurrentTitle() {
        return MyFirebase.getInstance().getUser().getEmail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // back arrow pressed
            case android.R.id.home:
                super.onBackPressed();
                return true;

            // home icon pressed
            case R.id.home_action:
                setUserView();
                return true;

            case R.id.updateProfile_action:
                updateProfile();
                return true;

            case R.id.updateHours_action:
                updateHours();
                return true;

            case R.id.logout_action:
                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void logout() {
        MyFirebase.getInstance().logout();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void updateHours() {
        setViewVisibility(eViewState.center, View.VISIBLE);
        setViewVisibility(eViewState.bottom, View.INVISIBLE);
        setViewVisibility(eViewState.background, View.INVISIBLE);
        CalendarFragment calendarFragment = new CalendarFragment();
        calendarFragment.setOnDatePickCallback((params) -> openUpdateHoursForm(
                (int)params[0],
                (int)params[1],
                (int)params[2]
        ));
        loadFragment(R.id.main_center_frame, calendarFragment);
    }

    private boolean openUpdateHoursForm(int year, int month, int day) {
        chosenDate = String.format("%d/%d/%d", year, month, day);
        UpdateHoursFragment updateHoursFragment = new UpdateHoursFragment();
        updateHoursFragment.setOnSaveCallback(params -> saveNewTimeClock(
                (TimeClock)params[0],
                (TimeClock)params[1]
                )
        );
        loadFragment(R.id.main_bottom_frame, updateHoursFragment);
        setViewVisibility(eViewState.bottom, View.VISIBLE);
        return true;
    }

    private boolean saveNewTimeClock(TimeClock start, TimeClock end) {
        MyFirebase.getInstance().saveUserTimeClock(start, end, chosenDate);
        return renderUI();
    }

    private void loadFragment(int resource, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().
                replace(resource, fragment).commit();
    }

    private void updateProfile() {
        setViewVisibility(eViewState.center, View.VISIBLE);
        setViewVisibility(eViewState.bottom, View.INVISIBLE);
        UpdateProfileFragment updateProfileFragment = new UpdateProfileFragment();
        updateProfileFragment.setOnUpdateCompleteCallback(params -> renderUI());
        loadFragment(R.id.main_center_frame, updateProfileFragment);
    }

    private boolean renderUI() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
        return true;
    }
}