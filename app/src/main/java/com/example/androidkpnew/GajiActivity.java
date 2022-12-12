package com.example.androidkpnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GajiActivity extends AppCompatActivity implements CallDataAdapterGaji{
    RecyclerView recyclerView;
    GajiViewAdapter gajiViewAdapter;
    DatabaseReference databaseReference;
    ArrayList<Absen> abs =  new ArrayList<>();
    ArrayList<Gaji> gaji =  new ArrayList<>();
    ArrayList<Employee> emp =  new ArrayList<>();
    String getNama, getRole;
    int getNorek;
    float getAbsen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaji);

        final TextView tanggalRange_txt = findViewById(R.id.tanggalRange_txt);
        final Button kalender = findViewById(R.id.calendar_btn);
        recyclerView = findViewById(R.id.data_gaji);
        databaseReference = FirebaseDatabase.getInstance().getReference("Absen");
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        gajiViewAdapter = new GajiViewAdapter(this, abs, gaji, emp, this);
        recyclerView.setAdapter(gajiViewAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Absen absen = dataSnapshot.getValue(Absen.class);
                    absen.setKey(dataSnapshot.getKey());
                    abs.add(absen);
//                    key = dataSnapshot.getKey();
                }
                gajiViewAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(gajiViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(error.getMessage(), "onCancelled: ", error.toException());
            }
        });

        kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "TAG_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair <Long, Long> selection) {
                        Long startDate = selection.first;
                        Long endDate = selection.second;
                        Log.d("tanggal awal millis", " : "+startDate);
                        Log.d("tanggal akhir millis", " : "+endDate);
                        tanggalRange_txt.setText(materialDatePicker.getHeaderText());
                        Log.d("tanggal range", " : "+tanggalRange_txt.getText());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String dateStringStart = formatter.format(new Date(startDate));
                        String dateStringEnd = formatter.format(new Date(endDate));
                        Log.d("tanggal awal convert", " : "+dateStringStart);
                        Log.d("tanggal akhir convert", " : "+dateStringEnd);
                    }
                });
            }
        });
    }

    @Override
    public void addToList(String namaPegawai, String rolePegawai, int norekening, float jumlahAbsen) {
        getNama = namaPegawai;
        getRole = rolePegawai;
        getNorek = norekening;
        getAbsen = jumlahAbsen;
    }

}