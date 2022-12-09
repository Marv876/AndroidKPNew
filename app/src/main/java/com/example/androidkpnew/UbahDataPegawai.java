package com.example.androidkpnew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class UbahDataPegawai extends AppCompatActivity {
    private EditText etNamapegawai;
    private Spinner spinnerRolepegawai;
    private EditText etNorekening;
    private EditText etGajipokok;
    private EditText etGajimingguan;
    private EditText etGajibulanan;
    private Button btnUbahDataPegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data_pegawai);

        etNamapegawai = findViewById(R.id.namapegawai_et);
        spinnerRolepegawai = findViewById(R.id.role_spinner);
        etNorekening = findViewById(R.id.norekening_et);
        etGajipokok = findViewById(R.id.gajipokok_etn);
        etGajimingguan = findViewById(R.id.gajimingguan_etn);
        etGajibulanan = findViewById(R.id.gajibulanan_etn);
        btnUbahDataPegawai = findViewById(R.id.ubahData_btn);

        etGajipokok.addTextChangedListener(onTextChangedListener());
        etGajimingguan.addTextChangedListener(onTextChangedListener2());
        etGajibulanan.addTextChangedListener(onTextChangedListener3());

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
            hashmap.put("namaPegawai", etNamapegawai.getText().toString());
            hashmap.put("rolePegawai", spinnerRolepegawai.getSelectedItem());
            hashmap.put("nomorRekening", Integer.parseInt(etNorekening.getText().toString()));
            hashmap.put("gajiPokok", Integer.parseInt(etGajipokok.getText().toString().replaceAll(",","")));
            hashmap.put("gajiMingguan", Integer.parseInt(etGajimingguan.getText().toString().replaceAll(",","")));
            hashmap.put("gajiBulanan", Integer.parseInt(etGajibulanan.getText().toString().replaceAll(",","")));
            konekDB.update(emp_edit.getKey(), hashmap).addOnSuccessListener(suc -> {
                Toast.makeText(this, "Berhasil Update data Pegawai", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(er -> {
                Toast.makeText(this, ""+ er.getMessage(), Toast.LENGTH_SHORT).show();
            });
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