package com.example.androidkpnew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UbahGajiPegawai extends AppCompatActivity {
    private TextView etNamapegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_gaji_pegawai);
        etNamapegawai = findViewById(R.id.txt_namaPegawai);

        DAOGaji konekDB = new DAOGaji();
        Absen emp_edit = (Absen) getIntent().getSerializableExtra("UPDATE");
        if(emp_edit != null){
//            btnUbahDataPegawai.setText("UPDATE");
//            spinnerRolepegawai.getSelectedItem();
//            etNorekening.setText(String.valueOf(emp_edit.getnomorRekening()));
//            etGajipokok.setText(String.valueOf(emp_edit.getGajiPokok()));
//            etGajimingguan.setText(String.valueOf(emp_edit.getGajiMingguan()));
//            etGajibulanan.setText(String.valueOf(emp_edit.getGajiBulanan()));
        }
        else{
//            btnUbahDataPegawai.setText("SUBMIT");
        }

    }
}