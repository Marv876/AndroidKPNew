package com.example.androidkpnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryActivity extends AppCompatActivity {
    String dateStringStart, dateStringEnd;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    HistoryViewAdapter historyAdapter;
    ArrayList<Gaji> listGaji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final TextView tanggalRange_txt = findViewById(R.id.tanggalRange_txt);
        final Button kalender = findViewById(R.id.calendar_btn);

        recyclerView = findViewById(R.id.data_history);
        databaseReference = FirebaseDatabase.getInstance().getReference("Gaji");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                        tanggalRange_txt.setText(materialDatePicker.getHeaderText());
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        dateStringStart = formatter.format(new Date(startDate));
                        dateStringEnd = formatter.format(new Date(endDate));
                    }
                });
            }
        });

        listGaji = new ArrayList<>();
        historyAdapter = new HistoryViewAdapter(this, listGaji);
        historyAdapter.notifyDataSetChanged();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listGaji.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Gaji gaji = dataSnapshot.getValue(Gaji.class);
                    gaji.setKey(dataSnapshot.getKey());
                    listGaji.add(gaji);
//                    key = dataSnapshot.getKey();
                }
                historyAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(historyAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(error.getMessage(), "onCancelled: ", error.toException());
            }
        });

    }
}