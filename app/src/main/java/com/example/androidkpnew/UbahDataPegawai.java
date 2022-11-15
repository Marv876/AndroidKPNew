package com.example.androidkpnew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Objects;

public class UbahDataPegawai extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data_pegawai);

        final EditText etNamapegawai = findViewById(R.id.namapegawai_et);
        final Spinner spinnerRolepegawai = findViewById(R.id.role_spinner);
        final EditText etNorekening = findViewById(R.id.norekening_et);
        final EditText etGajipokok = findViewById(R.id.gajipokok_etn);
        final EditText etGajimingguan = findViewById(R.id.gajimingguan_etn);
        final EditText etGajibulanan = findViewById(R.id.gajibulanan_etn);
        final Button btnUbahDataPegawai = findViewById(R.id.ubahDataPegawai_btn);

        DAOEmployee konekDB = new DAOEmployee();
        Employee emp = (Employee)getIntent().getSerializableExtra("EDIT");
        if(emp != null){
            btnUbahDataPegawai.setText("UPDATE");
            etNamapegawai.setText(emp.getNamaPegawai());
            spinnerRolepegawai.getSelectedItem();
            etNorekening.setText(emp.getnomorRekening());
            etGajipokok.setText(emp.getGajiPokok());
            etGajimingguan.setText(emp.getGajiMingguan());
            etGajibulanan.setText(emp.getGajiBulanan());
        }
        else{
            btnUbahDataPegawai.setText("SUBMIT");
        }

        btnUbahDataPegawai.setOnClickListener(view -> {
            HashMap<String, Object> hashmap = new HashMap<>();
            hashmap.put("name", etNamapegawai.getText().toString());
            hashmap.put("role", spinnerRolepegawai.getSelectedItem());
            hashmap.put("norek", Integer.parseInt(etNorekening.getText().toString()));
            hashmap.put("gapo", Integer.parseInt(etGajipokok.getText().toString()));
            hashmap.put("gamin", Integer.parseInt(etGajimingguan.getText().toString()));
            hashmap.put("gabul", Integer.parseInt(etGajibulanan.getText().toString()));
            konekDB.update(emp.getKey(), hashmap).addOnSuccessListener(suc -> {
                Toast.makeText(this, "Berhasil Update data Pegawai", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(er -> {
                Toast.makeText(this, ""+ er.getMessage(), Toast.LENGTH_SHORT).show();
            });

        });

    }
}