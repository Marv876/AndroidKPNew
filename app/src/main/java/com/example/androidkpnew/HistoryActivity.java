package com.example.androidkpnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
    ArrayList<Gaji> listGaji = new ArrayList<>();
    Boolean start = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final TextView tanggalRange_txt = findViewById(R.id.tanggalRange_txt);
        final Button kalender = findViewById(R.id.calendar_btn);
        final SearchView cariData =  findViewById(R.id.simpleSearchView);

        recyclerView = findViewById(R.id.data_history);
        databaseReference = FirebaseDatabase.getInstance().getReference("Gaji");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();

        if(start == false){
            listGaji = new ArrayList<>();
            historyAdapter = new HistoryViewAdapter(this, listGaji);
            historyAdapter.notifyDataSetChanged();
        }

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
                        Query query = databaseReference.orderByChild("tanggalBuat").startAt(dateStringStart).endAt(dateStringEnd);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                showListener(snapshot);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e(error.getMessage(), "onCancelled: ", error.toException());
                            }
                        });
                    }
                });
            }
        });

        cariData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Query query = databaseReference.orderByChild("namaPegawai").startAt(newText).endAt(newText+"\uf8ff");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot getSnapshot: snapshot.getChildren()) {
                            showListener(snapshot);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(error.getMessage(), "onCancelled: ", error.toException());
                    }
                });

                return false;
            }
        });

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

    private void showListener(DataSnapshot snapshot) {
        listGaji.clear();
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            Gaji gaji = dataSnapshot.getValue(Gaji.class);
            gaji.setKey(dataSnapshot.getKey());
            listGaji.add(gaji);
        }
        start = true;
        historyAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(historyAdapter);
    }
}