package com.example.inout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
        // TODO: 21/05/2022 add User resolving
        return false ? "" : "Logged in as Gal";
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

            // TODO: 28/05/2022 attached to home page
            // home icon pressed
            case R.id.home_action:
                NavUtils.navigateUpFromSameTask(this);
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

    private void logout() { }

    private void updateHours() { }

    private void updateProfile() { }

}