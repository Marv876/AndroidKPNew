package com.example.androidkpnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.androidkpnew.databinding.ActivityMainBinding;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AbsensiActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    AbsensiViewAdapter absAdapter;
    ArrayList<Employee> list;
    ArrayList<Absen> abs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);

        recyclerView = findViewById(R.id.data_absensi);
        final Button submitBtn = findViewById(R.id.submitAbsen_btn);
        final Button kalender = findViewById(R.id.calendar_btn);
        databaseReference = FirebaseDatabase.getInstance().getReference("Employee");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        absAdapter = new AbsensiViewAdapter(this, list, abs);
        recyclerView.setAdapter(absAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Employee pegawai = dataSnapshot.getValue(Employee.class);
                    Absen absen = dataSnapshot.getValue(Absen.class);
                    pegawai.setKey(dataSnapshot.getKey());
                    list.add(pegawai);
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


        kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Kalender");
            }
        });

        DAOAbsen konekDB = new DAOAbsen();
        submitBtn.setOnClickListener(v -> {
            Absen abs = new Absen();
            konekDB.add(abs).addOnSuccessListener(suc -> {
                Toast.makeText(this,"Absen telah tercatat!", Toast.LENGTH_LONG).show();
            }).addOnFailureListener(er -> {
                Toast.makeText(this,""+er.getMessage(), Toast.LENGTH_LONG).show();
            });
        });


    }

//    public void onCheckboxClicked_ForAll(View view) {
//        // Is the view now checked?
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked
//        switch(view.getId()) {
//            case R.id.checkBox_centangSemua:
//                if (checked){
//                    for(int i=0; i < recyclerView.getChildCount(); i++){
//                        RelativeLayout itemLayout = (RelativeLayout)recyclerView.getChildAt(i);
//                        CheckBox cb = (CheckBox)itemLayout.findViewById(R.id.checkBox_FullDay);
//                        cb.setChecked(true);
//                    }
//                }
//            else
//                break;
//        }
//    }


    @Override
    public void onDateSet(DatePicker view, int tahun, int bulan, int hari) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, tahun);
        c.set(Calendar.MONTH, bulan);
        c.set(Calendar.DAY_OF_MONTH, hari);
        String tglSekarang = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        Log.d("OUTPUT", "Tanggal sekarang : "+tglSekarang);
    }

}