package com.example.inout.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inout.R;
import com.google.android.material.textview.MaterialTextView;



public class UpdateHoursFragment extends Fragment {

    public UpdateHoursFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_hours, container, false);
        MaterialTextView txt = view.findViewById(R.id.txt);

        return view;
    }

    public void setFormDate(int year, int month, int day){
    }
}