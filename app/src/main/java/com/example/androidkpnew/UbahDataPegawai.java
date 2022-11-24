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
        final Button btnUbahDataPegawai = findViewById(R.id.ubahData_btn);

        DAOEmployee konekDB = new DAOEmployee();
        Employee emp_edit = (Employee)getIntent().getSerializableExtra("EDIT");
        if(emp_edit != null){
            btnUbahDataPegawai.setText("UPDATE");
            etNamapegawai.setText(emp_edit.getNamaPegawai());
            spinnerRolepegawai.getSelectedItem();

            etNorekening.setText(String.valueOf(emp_edit.getnomorRekening()));
            etGajipokok.setText(String.valueOf(emp_edit.getGajiPokok()));
            etGajimingguan.setText(String.valueOf(emp_edit.getGajiMingguan()));
            etGajibulanan.setText(String.valueOf(emp_edit.getGajiBulanan()));
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
            konekDB.update(emp_edit.getKey(), hashmap).addOnSuccessListener(suc -> {
                Toast.makeText(this, "Berhasil Update data Pegawai", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(er -> {
                Toast.makeText(this, ""+ er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });

    }
}