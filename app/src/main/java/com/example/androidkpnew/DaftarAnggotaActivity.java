package com.example.androidkpnew;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DaftarAnggotaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_anggota);
        final EditText etNamapegawai = findViewById(R.id.namapegawai_et);
        final Spinner spinnerRolepegawai = findViewById(R.id.role_spinner);
        final EditText etNorekening = findViewById(R.id.norekening_et);
        final EditText etGajipokok = findViewById(R.id.gajipokok_etn);
        final EditText etGajimingguan = findViewById(R.id.gajimingguan_etn);
        final EditText etGajibulanan = findViewById(R.id.gajibulanan_etn);
        final Button btnSubmitpegawai = findViewById(R.id.submit_btn);
        final Button btnLihatDataPegawai = findViewById(R.id.lihatdaftarpegawai_btn);

        DAOEmployee konekDB = new DAOEmployee();
        btnSubmitpegawai.setOnClickListener(view -> {
            if(etNamapegawai.getText().toString() == ""){
                Toast.makeText(this,"Isi Nama Pegawai", Toast.LENGTH_LONG).show();
            }
            else if(etNorekening.getText().toString() == ""){
                Toast.makeText(this,"Isi Nomor Rekening pegawai", Toast.LENGTH_LONG).show();
            }
            else if(etGajipokok.getText().toString() == ""){
                Toast.makeText(this,"Isi Gaji Pokok Pegawai", Toast.LENGTH_LONG).show();
            }
            else if(etGajimingguan.getText().toString() == ""){
                Toast.makeText(this,"Isi Gaji Mingguan Pegawai", Toast.LENGTH_LONG).show();
            }
            else if(etGajibulanan.getText().toString() == ""){
                Toast.makeText(this,"Isi Gaji Bulanan Pegawai", Toast.LENGTH_LONG).show();
            }else{
                Employee emp = new Employee(etNamapegawai.getText().toString(),spinnerRolepegawai.getSelectedItem().toString(), Integer.parseInt(etNorekening.getText().toString()), Integer.parseInt(etGajipokok.getText().toString()), Integer.parseInt(etGajimingguan.getText().toString()), Integer.parseInt(etGajibulanan.getText().toString()));
                konekDB.add(emp).addOnSuccessListener(suc -> {
                    Toast.makeText(this,"Telah Terinput", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(er -> {
                    Toast.makeText(this,""+er.getMessage(), Toast.LENGTH_LONG).show();
                });
                etNamapegawai.setText("");
                etNorekening.setText("");
                etGajipokok.setText("");
                etGajimingguan.setText("");
                etGajibulanan.setText("");
            }
        });

        btnLihatDataPegawai.setOnClickListener(view -> {
//            pindah halaman ke DaftarPegawaiActivity
            Intent myIntent = new Intent(DaftarAnggotaActivity.this, DaftarPegawaiActivity.class);
            try {
                DaftarAnggotaActivity.this.startActivity(myIntent);
            } catch (ActivityNotFoundException e) {
                // Define what your app should do if no activity can handle the intent.
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}
