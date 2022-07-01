package com.example.inout.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;

import com.example.inout.R;
import com.example.inout.utils.MyFirebase;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;


public class login_activity extends AppCompatActivity {

    private EditText login_EDT_password;
    private EditText login_EDT_username;
    private MaterialTextView login_LBL_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setViews();
    }

    private void setViews() {
        login_EDT_password = findViewById(R.id.login_EDT_password);
        login_EDT_username = findViewById(R.id.login_EDT_username);
        login_LBL_status = findViewById(R.id.login_LBL_status);
        MaterialButton login_BTN_send = findViewById(R.id.login_BTN_send);
        login_BTN_send.setOnClickListener(e -> handleLogin());
    }

    private void handleLogin() {
        String password = getPassword();
        String username = getEmail();
        MyFirebase.getInstance().handleLogin(
                username,
                password,
                params -> onSuccessCallback(),
                params -> onFailCallback((String) params[0])
        );
    }

    private boolean onFailCallback(String message) {
        setStatus(message);
        return true;
    }

    private void setStatus(String message) {
        login_LBL_status.setTextColor(Color.RED);
        login_LBL_status.setText(message);
    }

    private boolean onSuccessCallback() {
        openApp();
        return true;
    }


    private String getEmail() {
        return login_EDT_username.getText().toString();
    }

    private String getPassword() {
        return login_EDT_password.getText().toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(MyFirebase.getInstance().getUser() != null){
            openApp();
        }
    }

    private void openApp() {
        startActivity(new Intent(this, MainActivity.class));
    }
}