package com.example.androidkpnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.androidkpnew.databinding.ActivityMainBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AbsensiActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, CallDataAdapterAbsensi {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceAbsensi;
    AbsensiViewAdapter absAdapter;
    ArrayList<Employee> list;
    ArrayList<Absen> abs = new ArrayList<>();
    String tglSekarang;
    String ViewTanggal;
    Boolean sdhPilihTgl = false;
    String getNama, getRole, getHalf, getFull;
    int getNorek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);
        recyclerView = findViewById(R.id.data_absensi);
        final Button kalender = findViewById(R.id.calendar_btn);
        final CheckBox reset = findViewById(R.id.checkBox_centangSemua);

        databaseReference = FirebaseDatabase.getInstance().getReference("Employee");
        databaseReferenceAbsensi = FirebaseDatabase.getInstance().getReference("Absen");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        absAdapter = new AbsensiViewAdapter(this, list, abs, this);
        recyclerView.setAdapter(absAdapter);
        kalender.setPadding(0,0,18,0);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Employee pegawai = dataSnapshot.getValue(Employee.class);
                    Absen absen = dataSnapshot.getValue(Absen.class);
                    pegawai.setKey(dataSnapshot.getKey());
                    absen.setKey(dataSnapshot.getKey());
                    list.add(pegawai);
//                    abs.add(absen);
//                    key = dataSnapshot.getKey();
                }
                absAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(absAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(error.getMessage(), "onCancelled: ", error.toException());
            }
        });

        databaseReferenceAbsensi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Absen absen = dataSnapshot.getValue(Absen.class);
                    absen.setKey(dataSnapshot.getKey());
                    abs.add(absen);
//                    key = dataSnapshot.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(error.getMessage(), "onCancelled absensi error : ", error.toException());
            }
        });

        kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Kalender");
            }
        });

        reset.setOnClickListener(view -> {
            if(reset.isChecked() == true){
                if(sdhPilihTgl != true){
                    reset.setChecked(false);
                    Toast.makeText(this, "Pilih tanggalnya dulu", Toast.LENGTH_SHORT).show();
                }else{
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Absen");
                    Query query = database.orderByChild("tanggal").equalTo(tglSekarang);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot getSnapshot: dataSnapshot.getChildren()) {
                                getSnapshot.getRef().removeValue();
                            }
                            Toast.makeText(AbsensiActivity.this, "Data sudah dihapus, silahkan isi ulang absensi", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("error database : ", databaseError.toException().toString(), databaseError.toException());
                        }
                    });
                    reset.setChecked(false);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                            startActivity(getIntent());
                        }
                    }, 2000);

                }
            }

        });

    }

    @Override
    public void onDateSet(DatePicker view, int tahun, int bulan, int hari) {
        final TextView tanggalnya = findViewById(R.id.tanggal_txt);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, tahun);
        c.set(Calendar.MONTH, bulan);
        c.set(Calendar.DAY_OF_MONTH, hari);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat coba = new SimpleDateFormat("dd MMMM yyyy");
        tglSekarang = formatter.format(c.getTime());
        ViewTanggal = coba.format(c.getTime());
        sdhPilihTgl = true;
        if(sdhPilihTgl == true){
            tanggalnya.setText(ViewTanggal);
        }
        Log.d("Tanggal Sudah dipilih", "Boolean : "+sdhPilihTgl);
    }

    @Override
    public void addToList(String namaPegawai, String rolePegawai, int norekening, String halfDay, String fullDay) {
        Log.d("output nama", "nama : "+namaPegawai);
        Log.d("output role", "role : "+rolePegawai);
        Log.d("output norek", "norek : "+norekening);
        Log.d("output halfday", "halfday : "+halfDay);
        Log.d("output fullday", "fullday : "+fullDay);
        getNama = namaPegawai;
        getRole = rolePegawai;
        getNorek = norekening;
        getHalf = halfDay;
        getFull = fullDay;
        
        if(sdhPilihTgl != true){
            Toast.makeText(this, "Pilih tanggalnya dulu", Toast.LENGTH_SHORT).show();
        }else{
            DAOAbsen konekDB = new DAOAbsen();
            Log.d("array", "total isi array: "+abs.size());
//        Log.d("Output key all", " : "+abs.isEmpty());
            if (abs.isEmpty()){
                Absen absBaru = new Absen(tglSekarang, getNama, getRole, getNorek, getHalf, getFull);
                abs.add(new Absen(tglSekarang, getNama, getRole, getNorek, getHalf, getFull));
                konekDB.add(absBaru).addOnSuccessListener(suc -> {
                    Toast.makeText(this,"Absen Telah diCatat!", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er -> {
                    Toast.makeText(this,""+er.getMessage(), Toast.LENGTH_LONG).show();
                });
                Log.d("array", "total isi array: "+abs.size());
            }else{
                Boolean cek = true;
                Boolean berhasil = false;
                Log.d("array", "total isi array: "+abs.size());
                for (int i = 0; i < abs.size(); i++){
                    if(tglSekarang == abs.get(i).getTanggal() && norekening == abs.get(i).getnomorRekening() && abs.get(i).getKey() != null){
                        HashMap<String, Object> hashmap = new HashMap<>();
                        hashmap.put("tanggal", abs.get(i).getTanggal());
                        hashmap.put("namaPegawai", abs.get(i).getnamaPegawai());
                        hashmap.put("rolePegawai", abs.get(i).getrolePegawai());
                        hashmap.put("nomorRekening", abs.get(i).getnomorRekening());
                        hashmap.put("halfDay", getHalf);
                        hashmap.put("fullDay", getFull);
                        Log.d("halfday", " : "+getHalf);
                        Log.d("fullday", " : "+getFull);
                        konekDB.update(abs.get(i).getKey(), hashmap).addOnSuccessListener(suc -> {
                            Log.d("update", "absensi : true");
                        }).addOnFailureListener(er -> {
                            Toast.makeText(this, ""+ er.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                        berhasil = true;
                        cek = false;
                    }
                }
                if(berhasil){
                    Toast.makeText(this, "Berhasil Update Absensi!", Toast.LENGTH_SHORT).show();
//                finish();
                }
                if(cek){
                    Absen absBaru = new Absen(tglSekarang, getNama, getRole, getNorek, getHalf, getFull);
                    abs.add(new Absen(tglSekarang, getNama, getRole, getNorek, getHalf, getFull));
                    konekDB.add(absBaru).addOnSuccessListener(suc -> {
                        Toast.makeText(this,"Absen Telah diCatat!", Toast.LENGTH_LONG).show();
                    }).addOnFailureListener(er -> {
                        Toast.makeText(this,""+er.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            }
        }

    }

}