package com.example.inout.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.inout.R;
import com.example.inout.models.DayData;
import com.example.inout.models.MonthData;
import com.example.inout.utils.MyFirebase;
import com.google.android.material.textview.MaterialTextView;

import java.util.Calendar;
import java.util.Map;

public class TableFragment extends Fragment {
    private TableLayout table_TBL_monthTable;
    private MonthData monthData;
    private View view;

    public TableFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_table, container, false);
        setTableData();
        setViews();
        addTableRows();
        return view;
    }

    private void setViews() { table_TBL_monthTable = view.findViewById(R.id.table_TBL_monthTable); }

    private void setTableData() {
       monthData = MyFirebase.getInstance().
                getUserMonthData(Calendar.getInstance().get(Calendar.MONTH) + 1); // month are 0 offset index
    }

    private void addTableRows() {
        setHeaders();
        if(monthData == null){
            TableRow tableRow = new TableRow(view.getContext());
            MaterialTextView noDataTXT = new MaterialTextView(view.getContext());
            noDataTXT.setText("No records yet...");
            noDataTXT.setPadding(16, 16, 16, 16);
            tableRow.addView(noDataTXT);

            table_TBL_monthTable.addView(tableRow);
        }else {
            for (Map.Entry<String, DayData> e :  monthData.getDaysData().entrySet()){
                TableRow tableRow = new TableRow(view.getContext());
                MaterialTextView dayColData = new MaterialTextView(view.getContext());
                MaterialTextView startHourColData = new MaterialTextView(view.getContext());
                MaterialTextView endHourColData = new MaterialTextView(view.getContext());

                dayColData.setText(e.getKey());
                startHourColData.setText(e.getValue().getStart().toString());
                endHourColData.setText(e.getValue().getEnd().toString());

                dayColData.setPadding(16,16,16,16 );
                startHourColData.setPadding(16,16,16,16);
                endHourColData.setPadding(16,16,16,16);

                tableRow.addView(dayColData);
                tableRow.addView(startHourColData);
                tableRow.addView(endHourColData);

                table_TBL_monthTable.addView(tableRow);
            }
        }
    }

    private void setHeaders() {
        TableRow tableRow = new TableRow(view.getContext());

        MaterialTextView dayCol = new MaterialTextView(view.getContext());
        MaterialTextView startHourCol = new MaterialTextView(view.getContext());
        MaterialTextView endHourCol = new MaterialTextView(view.getContext());

        dayCol.setText(R.string.date_column);
        dayCol.setTextSize(20);
        dayCol.setPadding(16,16,16,16);

        startHourCol.setText(R.string.start_hour_column);
        startHourCol.setTextSize(20);
        startHourCol.setPadding(16,16,16,16);

        endHourCol.setText(R.string.end_hour_column);
        endHourCol.setTextSize(20);
        endHourCol.setPadding(16,16,16,16);

        tableRow.addView(dayCol);
        tableRow.addView(startHourCol);
        tableRow.addView(endHourCol);

        table_TBL_monthTable.addView(tableRow);
    }
}