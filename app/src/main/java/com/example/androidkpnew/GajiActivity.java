package com.example.androidkpnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class GajiActivity extends AppCompatActivity{
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaji);

        final TextView tanggalRange_txt = findViewById(R.id.tanggalRange_txt);
        final Button kalender = findViewById(R.id.calendar_btn);
        recyclerView = findViewById(R.id.data_absensi);
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();

        kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "TAG_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair <Long, Long> selection) {
                        Long startDate = selection.first;
                        Long endDate = selection.second;
                        Log.d("tanggal awal", " : "+startDate);
                        Log.d("tanggal akhir", " : "+endDate);
                        tanggalRange_txt.setText(materialDatePicker.getHeaderText());
                        Calendar utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//                        utc.setTimeInMillis(selection);
//                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                        String formatted = format.startDate(utc.getTime());
                    }
                });
            }
        });
    }


}