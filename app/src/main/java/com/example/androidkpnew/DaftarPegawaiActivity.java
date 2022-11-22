package com.example.androidkpnew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class DaftarPegawaiActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    PegawaiViewAdapter pgwAdapter;
    ArrayList<Employee> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pegawai);
        ArrayAdapter<String> listadapter = new ArrayAdapter<String>(DaftarPegawaiActivity.this,
                R.layout.activity_ubah_data_pegawai);

        final Button btnUbahDataPGW = findViewById(R.id.ubahDataPegawai_btn);
        final Button btnHistoryPGW = findViewById(R.id.historyPegawai_btn);

        recyclerView = findViewById(R.id.data_list);
        databaseReference = FirebaseDatabase.getInstance().getReference("Employee");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        pgwAdapter = new PegawaiViewAdapter(this, list);
        recyclerView.setAdapter(pgwAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Employee pegawai = dataSnapshot.getValue(Employee.class);
                    pegawai.setKey(dataSnapshot.getKey());
                    list.add(pegawai);

                }
                pgwAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(pgwAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e(error.getMessage(), "onCancelled: ", error.toException());
            }
        });

        btnUbahDataPGW.setOnClickListener(view -> {
            Intent myIntent = new Intent(DaftarPegawaiActivity.this, UbahDataPegawai.class);
            try {
                DaftarPegawaiActivity.this.startActivity(myIntent);

            } catch (ActivityNotFoundException e) {
                // Define what your app should do if no activity can handle the intent.
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}