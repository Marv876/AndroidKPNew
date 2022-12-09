package com.example.androidkpnew;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DaftarAnggotaActivity extends AppCompatActivity {
    private EditText etNamapegawai;
    private Spinner spinnerRolepegawai;
    private EditText etNorekening;
    private EditText etGajipokok;
    private EditText etGajimingguan;
    private EditText etGajibulanan;
    private Button btnSubmitpegawai;
    private Button btnLihatDataPegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_anggota);

        etNamapegawai = findViewById(R.id.namapegawai_et);
        spinnerRolepegawai = findViewById(R.id.role_spinner);
        etNorekening = findViewById(R.id.norekening_et);
        etGajipokok = findViewById(R.id.gajipokok_etn);
        etGajimingguan = findViewById(R.id.gajimingguan_etn);
        etGajibulanan = findViewById(R.id.gajibulanan_etn);
        btnSubmitpegawai = findViewById(R.id.submit_btn);
        btnLihatDataPegawai = findViewById(R.id.lihatdaftarpegawai_btn);

        etGajipokok.addTextChangedListener(onTextChangedListener());
        etGajimingguan.addTextChangedListener(onTextChangedListener2());
        etGajibulanan.addTextChangedListener(onTextChangedListener3());

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
                Log.d("regex", ": "+etGajipokok.getText().toString().replaceAll(",",""));
                Employee emp = new Employee(etNamapegawai.getText().toString(),spinnerRolepegawai.getSelectedItem().toString(), Integer.parseInt(etNorekening.getText().toString()), Integer.parseInt(etGajipokok.getText().toString().replaceAll(",","")), Integer.parseInt(etGajimingguan.getText().toString().replaceAll(",","")), Integer.parseInt(etGajibulanan.getText().toString().replaceAll(",","")));
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

    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etGajipokok.removeTextChangedListener(this);
                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    etGajipokok.setText(formattedString);
                    etGajipokok.setSelection(etGajipokok.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                etGajipokok.addTextChangedListener(this);
            }
        };
    }

    private TextWatcher onTextChangedListener2() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etGajimingguan.removeTextChangedListener(this);
                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    etGajimingguan.setText(formattedString);
                    etGajimingguan.setSelection(etGajimingguan.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                etGajimingguan.addTextChangedListener(this);
            }
        };
    }

    private TextWatcher onTextChangedListener3() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                etGajibulanan.removeTextChangedListener(this);
                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    etGajibulanan.setText(formattedString);
                    etGajibulanan.setSelection(etGajibulanan.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                etGajibulanan.addTextChangedListener(this);
            }
        };
    }

}
