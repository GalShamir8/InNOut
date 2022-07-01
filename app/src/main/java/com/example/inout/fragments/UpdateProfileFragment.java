package com.example.inout.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.inout.R;
import com.example.inout.activities.MainActivity;
import com.example.inout.common.Callable;
import com.example.inout.utils.MyFirebase;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class UpdateProfileFragment extends Fragment {
    private View view;

    private EditText update_EDT_email;
    private EditText update_EDT_password;
    private MaterialTextView update_LBL_status;

    private Callable onUpdateCompleteCallback = null;

    public UpdateProfileFragment() { }

    public UpdateProfileFragment setOnUpdateCompleteCallback(Callable onUpdateCompleteCallback) {
        this.onUpdateCompleteCallback = onUpdateCompleteCallback;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_update_profile, container, false);
        setViews();
        return view;
    }

    private void setViews() {
        MaterialButton update_BTN_send =  view.findViewById(R.id.update_BTN_send);
        update_BTN_send.setOnClickListener(e -> handleUpdate());
        update_EDT_email = view.findViewById(R.id.update_EDT_email);
        update_EDT_password = view.findViewById(R.id.update_EDT_password);
        update_LBL_status = view.findViewById(R.id.update_LBL_status);
    }

    private void handleUpdate() {
        updateEmail();
        updatePassword();
    }

    private void updatePassword() {
        String password = update_EDT_password.getText().toString();
        if (!password.isEmpty()){
            MyFirebase.getInstance().updateUserPassword(password, params -> onSuccess(),
                    params -> onFail((String)params[0]));
        }
    }

    private boolean onFail(String message) {
        update_LBL_status.setText(message);
        update_LBL_status.setTextColor(Color.RED);
        return false;
    }

    private boolean onSuccess() {
        if (onUpdateCompleteCallback != null){
            return onUpdateCompleteCallback.call();
        }
        return false;
    }

    private void updateEmail() {
        String email = update_EDT_email.getText().toString();
        if (!email.isEmpty()){
            MyFirebase.getInstance().updateUserEmail(email, params -> onSuccess(),
                    params -> onFail((String)params[0]));
        }
    }
}