package com.example.inout.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.inout.R;
import com.example.inout.common.TimeClock;
import com.example.inout.fragments.CalendarFragment;
import com.example.inout.fragments.UpdateHoursFragment;
import com.example.inout.utils.MyFirebase;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String chosenDate;
    private FirebaseUser user;
    private boolean isLoggedIn = false;
    private Bundle data;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();

        data = new Bundle();
    }

    private void loginPage() {

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            Log.d("pttt", "user signed in" + user.getUid());
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
        // TODO: 21/05/2022 add User resolving
        return isLoggedIn ? user.getEmail() : "";
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

    private void updateHours() {
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
        return true;
    }

    private boolean saveNewTimeClock(TimeClock start, TimeClock end) {
        return MyFirebase.getInstance().saveUserTimeClock(start, end, chosenDate);
    }

    private void loadFragment(int resource, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().
                replace(resource, fragment).commit();
    }

    private void updateProfile() { }

}