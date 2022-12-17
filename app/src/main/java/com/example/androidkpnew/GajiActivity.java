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
    ArrayList<Employee> emp =  new ArrayList<>();
    String getNama, getRole;
    int getNorek;
    Double getAbsen;
    Context context;
    CallDataAdapterGaji callGaji;
    String dateStringStart, dateStringEnd;
    ArrayList<Double> masukFull = new ArrayList<>();
    ArrayList<Double> masukHalf = new ArrayList<>();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaji);
        final TextView tanggalRange_txt = findViewById(R.id.tanggalRange_txt);
        final Button kalender = findViewById(R.id.calendar_btn);
        context = this;
        callGaji = this;
        dateStringStart = "14/12/2022";
        dateStringEnd = "15/12/2022";
        recyclerView = findViewById(R.id.data_gaji);
        databaseReference = FirebaseDatabase.getInstance().getReference("Absen");
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker().setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        gajiViewAdapter = new GajiViewAdapter(this, abs, gaji, emp, this, dateStringStart, dateStringEnd);
        recyclerView.setAdapter(gajiViewAdapter);
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
                        dateStringStart = formatter.format(new Date(startDate));
                        dateStringEnd = formatter.format(new Date(endDate));
                        gajiViewAdapter = new GajiViewAdapter(context, abs, emp, callGaji, dateStringStart, dateStringEnd, masukHalf, masukFull);
                        Log.d("tanggal awal convert", " : "+dateStringStart);
                        Log.d("tanggal akhir convert", " : "+dateStringEnd);
                        Query query = database.child("Absen").orderByChild("tanggal").startAt(dateStringStart).endAt(dateStringEnd);
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
    }

    @Override
    public void addToList(String namaPegawai, String rolePegawai, int norekening, Double jumlahAbsen) {
        getNama = namaPegawai;
        getRole = rolePegawai;
        getNorek = norekening;
        getAbsen = jumlahAbsen;

        Log.d("nama", " : "+getNama);
        Log.d("role", " : "+getRole);
        Log.d("norek", " : "+getNorek);
        Log.d("jumlahAbsen", " : "+getAbsen);
    }

    private void showListener(DataSnapshot snapshot) {
        abs.clear();
        masukHalf.clear();
        masukFull.clear();
        ArrayList<Absen> konstanta = new ArrayList<>();
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            String temp1 = dataSnapshot.child("namaPegawai").getValue().toString();
            String temp2 = dataSnapshot.child("halfDay").getValue().toString();
            String temp3 = dataSnapshot.child("fullDay").getValue().toString();
            Log.d("temp1", " : "+temp1);
            Log.d("temp2", " : "+temp2);
            Log.d("temp3", " : "+temp3);

            if(konstanta.isEmpty()){
                konstanta.add(new Absen(temp1, temp2, temp3));
//                konstanta.add(dataSnapshot.child("namaPegawai").getValue());
                Absen absen = dataSnapshot.getValue(Absen.class);
                absen.setKey(dataSnapshot.getKey());
                abs.add(absen);
                Employee employ = dataSnapshot.getValue(Employee.class);
                employ.setKey(dataSnapshot.getKey());
                emp.add(employ);
            }else{
                Boolean cek = false;
                int index = -1;
                for (int i = 0; i < konstanta.size(); i++) {
                    if(konstanta.get(i).getnamaPegawai().contains(dataSnapshot.child("namaPegawai").getValue().toString())){
                        cek = true;
                        index = i;
                    }
                }
                if(!cek){
                    konstanta.add(new Absen(temp1, temp2, temp3));
                    Absen absen = dataSnapshot.getValue(Absen.class);
                    absen.setKey(dataSnapshot.getKey());
                    abs.add(absen);
                    Employee employ = dataSnapshot.getValue(Employee.class);
                    employ.setKey(dataSnapshot.getKey());
                    emp.add(employ);
                }
                else{
                    int hitung = Integer.parseInt(konstanta.get(index).gethalfDay())+Integer.parseInt(dataSnapshot.child("halfDay").getValue().toString());
                    int hitung2 = Integer.parseInt(konstanta.get(index).getfullDay())+Integer.parseInt(dataSnapshot.child("fullDay").getValue().toString());
                    Log.d("integer", " data index: "+Integer.parseInt(konstanta.get(index).getfullDay()));
//                    Log.d("hitung2 nama :"+konstanta.get(index).getnamaPegawai(), " : "+hitung2);
                    konstanta.set(index, new Absen(temp1, hitung+"", hitung2+""));
                }
            }

        }
        for (int i = 0; i < konstanta.size(); i++) {
            masukHalf.add(Integer.parseInt(konstanta.get(i).gethalfDay())*1.0);
            masukFull.add(Integer.parseInt(konstanta.get(i).getfullDay())*1.0);
        }
        Log.d("snapshot masukhalf", " : "+masukHalf);
        Log.d("snapshot masukfull", " : "+masukFull);

        gajiViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(gajiViewAdapter);
    }

}