package com.example.androidkpnew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
                    list.add(pegawai);

                }
                pgwAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(error.getMessage(), "onCancelled: ", error.toException());
            }
        });

        btnUbahDataPGW.setOnClickListener(view -> {
            final ArrayList<Employee> emp = new ArrayList<>();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Employee");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        emp.add(snapshot.getValue(Employee.class));
                    }

                    int itemPosition = this.getLayoutPosition();

                    Intent intent = new Intent(this, UbahAnggotaActivity.class);
                    intent.putExtra("position", itemPosition + "");
                    intent.putExtra("restaurants", Parcela.wrap(emp));

                    this.startActivity(intent);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        });
    }

    public void onTransferPosition(int position) {
        this.position= position;
    }

}