package com.example.androidkpnew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

        final SearchView cariData =  findViewById(R.id.simpleSearchView);
        recyclerView = findViewById(R.id.data_list);
        databaseReference = FirebaseDatabase.getInstance().getReference("Employee");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        pgwAdapter = new PegawaiViewAdapter(this, list);
        pgwAdapter.notifyDataSetChanged();

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
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Employee pegawai = dataSnapshot.getValue(Employee.class);
                    pegawai.setKey(dataSnapshot.getKey());
                    list.add(pegawai);
//                    key = dataSnapshot.getKey();
                }
                pgwAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(pgwAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e(error.getMessage(), "onCancelled: ", error.toException());
            }
        });

    }

    private void showListener(DataSnapshot snapshot) {
        list.clear();
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            Employee pegawai = dataSnapshot.getValue(Employee.class);
            pegawai.setKey(dataSnapshot.getKey());
            list.add(pegawai);
//                    key = dataSnapshot.getKey();
        }
        pgwAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(pgwAdapter);
    }

}