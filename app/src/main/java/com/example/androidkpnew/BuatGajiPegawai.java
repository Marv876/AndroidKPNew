package com.example.androidkpnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BuatGajiPegawai extends AppCompatActivity {
    private TextView txtNamaPegawai;
    private TextView txtRolePegawai;
    private TextView txtNorekPegawai;
    private TextView txtTotalFull;
    private TextView txtTotalHalf;
    private EditText etNominalTf;
    private CheckBox chkMingguan;
    private CheckBox chkBulanan;
    private TextView txtAbsens;
    private TextView txtGajiPokok;
    private TextView txtTotalAbsensi;
    private TextView txtTotalAbsensi2;
    private TextView txtTotalAbsensi3;
    private LinearLayout line1;
    private TextView txtGamin;
    private LinearLayout line2;
    private TextView txtGabul;
    private TextView txtNominalTf;
    private Button btnCreate;
    private Button test;
    int simpanGamin = 0, simpanGabul = 0, totalhabishitung = 0, simpanhabishitung = 0, getnominal = 0;
    int gapo, gamin, gabul;
    Double hitung;
    DatabaseReference databaseReference;
    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_gaji_pegawai);
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        txtNamaPegawai = findViewById(R.id.txt_namaPegawai);
        txtRolePegawai = findViewById(R.id.txt_rolePegawai);
        txtNorekPegawai = findViewById(R.id.txt_norekPegawai);
        txtTotalFull = findViewById(R.id.fullday_txt);
        txtTotalHalf = findViewById(R.id.halfday_txt);
        txtNamaPegawai = findViewById(R.id.txt_namaPegawai);
        etNominalTf = findViewById(R.id.et_nominalTransfer);
        txtNominalTf = findViewById(R.id.txt_nominalTransfer);
        chkMingguan = findViewById(R.id.chkBox_mingguan);
        chkBulanan = findViewById(R.id.chkBox_bulanan);
        txtAbsens = findViewById(R.id.txt_absen);
        txtGajiPokok = findViewById(R.id.txt_gapoPegawai);
        txtTotalAbsensi = findViewById(R.id.txt_totalAbsensi);
        txtTotalAbsensi2 = findViewById(R.id.txt_totalAbsensi2);
        txtTotalAbsensi3 = findViewById(R.id.txt_totalAbsensi3);
        line1 = findViewById(R.id.line1_gajiBonus);
        txtGamin = findViewById(R.id.txt_gaminPegawai);
        line2 = findViewById(R.id.line2_gajiBonus);
        txtGabul = findViewById(R.id.txt_gabulPegawai);
        btnCreate = findViewById(R.id.buatData_btn);

        etNominalTf.addTextChangedListener(onTextChangedListener());

        Absen abs_edit = (Absen)getIntent().getSerializableExtra("ADD");
        Employee emp_edit = (Employee) getIntent().getSerializableExtra("ADD2");
        ArrayList<String> half = (ArrayList<String>) getIntent().getSerializableExtra("HALF");
        ArrayList<String> full = (ArrayList<String>) getIntent().getSerializableExtra("FULL");
        int posisiSkrng = (Integer) getIntent().getSerializableExtra("POSITION");
        hitung = (Double.valueOf(half.get(posisiSkrng))/2.0)+Double.valueOf(full.get(posisiSkrng));
        databaseReference = FirebaseDatabase.getInstance().getReference("Employee");

        if(abs_edit != null){
            txtNamaPegawai.setText(abs_edit.getnamaPegawai());
            txtRolePegawai.setText(abs_edit.getrolePegawai());
            txtNorekPegawai.setText(String.valueOf(abs_edit.getnomorRekening()));
            txtTotalHalf.setText(half.get(posisiSkrng)+" hari");
            txtTotalFull.setText(full.get(posisiSkrng)+(" hari"));
            txtAbsens.setText(hitung.toString()+" hari");
        }
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d("datasnapshot cek emp", " : "+(emp_edit.getnomorRekening() == Integer.parseInt(dataSnapshot.child("nomorRekening").getValue().toString())));
                    if(emp_edit.getnomorRekening() == Integer.parseInt(dataSnapshot.child("nomorRekening").getValue().toString())){
                        gapo = Integer.parseInt(dataSnapshot.child("gajiPokok").getValue().toString());
                        gamin = Integer.parseInt(dataSnapshot.child("gajiMingguan").getValue().toString());
                        gabul = Integer.parseInt(dataSnapshot.child("gajiBulanan").getValue().toString());
                    }
                }
                txtGajiPokok.setText(kursIndonesia.format(gapo)+"");
                double total = gapo*hitung;
                totalhabishitung = (int)total;
                txtTotalAbsensi.setText(kursIndonesia.format((int) total));
                int subtotal = totalhabishitung+simpanGamin+simpanGabul;
                simpanhabishitung = subtotal;
                txtTotalAbsensi2.setText(kursIndonesia.format(subtotal));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(error.getMessage(), "onCancelled: ", error.toException());
            }
        });

        etNominalTf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtNominalTf.setText(0+"");
                txtTotalAbsensi3.setText("Rp. "+0);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                String resultRupiah = formatRupiah(Double.parseDouble(txtNominalTf.getText().toString()));
                String inputText = etNominalTf.getText().toString();
                txtNominalTf.setText("Rp. "+inputText.replaceAll(resultRupiah , ""));
                if (etNominalTf.getText().toString() == ""){
                    getnominal = 0;
                    txtNominalTf.setText("Rp. 0");
                }else{
                    try {
                        getnominal = Integer.parseInt(etNominalTf.getText().toString().replaceAll(",",""));
                    }catch (Exception er){
                        Log.d("error : ", er.getMessage());
                    }
                }
                int grandtotal = simpanhabishitung - getnominal;
                txtTotalAbsensi3.setText(kursIndonesia.format(grandtotal));
            }
        });

        chkMingguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkMingguan.isChecked() == true){
                    line1.setVisibility(View.VISIBLE);
                    if(hitung < 5.5){
                        Toast.makeText(BuatGajiPegawai.this, "Absen tidak memenuhi!", Toast.LENGTH_LONG).show();
                        txtGamin.setText(kursIndonesia.format(gamin)+"");
                        simpanGamin = gamin;
                        txtGamin.setTextColor(Color.parseColor("#ff0000"));
                        int subtotal = (int)(gapo*hitung)+simpanGamin+simpanGabul;
                        simpanhabishitung = subtotal;
                        txtTotalAbsensi2.setText(kursIndonesia.format(subtotal));
                        int grandtotal = simpanhabishitung - getnominal;
                        txtTotalAbsensi3.setText(kursIndonesia.format(grandtotal));
                    }else{
                        simpanGamin = gamin;
                        txtGamin.setText(kursIndonesia.format(gamin)+"");
                        txtGamin.setTextColor(Color.parseColor("#000000"));
                        int subtotal = (int)(gapo*hitung)+simpanGamin+simpanGabul;
                        simpanhabishitung = subtotal;
                        txtTotalAbsensi2.setText(kursIndonesia.format(subtotal));
                        int grandtotal = simpanhabishitung - getnominal;
                        txtTotalAbsensi3.setText(kursIndonesia.format(grandtotal));
                    }
                }else {
                    line1.setVisibility(View.GONE);
                    simpanGamin = 0;
                    int subtotal = (int)(gapo*hitung)+simpanGamin+simpanGabul;
                    simpanhabishitung = subtotal;
                    txtTotalAbsensi2.setText(kursIndonesia.format(subtotal));
                    txtGamin.setText("0");
                    int grandtotal = simpanhabishitung - getnominal;
                    txtTotalAbsensi3.setText(kursIndonesia.format(grandtotal));
                }
            }
        });

        chkBulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkBulanan.isChecked() == true){
                    line2.setVisibility(View.VISIBLE);
                    txtGabul.setText(kursIndonesia.format(gabul)+"");
                    simpanGabul = gabul;
                    int subtotal = (int)(gapo*hitung)+simpanGamin+simpanGabul;
                    simpanhabishitung = subtotal;
                    txtTotalAbsensi2.setText(kursIndonesia.format(subtotal));
                    int grandtotal = simpanhabishitung - getnominal;
                    txtTotalAbsensi3.setText(kursIndonesia.format(grandtotal));
                }else {
                    line2.setVisibility(View.GONE);
                    simpanGabul = 0;
                    int subtotal = (int)(gapo*hitung)+simpanGamin+simpanGabul;
                    simpanhabishitung = subtotal;
                    txtTotalAbsensi2.setText(kursIndonesia.format(subtotal));
                    txtGabul.setText("0");
                    int grandtotal = simpanhabishitung - getnominal;
                    txtTotalAbsensi3.setText(kursIndonesia.format(grandtotal));
                }
            }
        });
        
        btnCreate.setOnClickListener(view -> {
            if(etNominalTf.getText().toString().matches("")){
                Toast.makeText(this, "Isi Nominal transfernya!", Toast.LENGTH_SHORT).show();
            }else{
                Calendar c = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String tglSekarang = formatter.format(c.getTime());
                DAOGaji konekDB = new DAOGaji();
                Gaji gaji = new Gaji(tglSekarang, txtNamaPegawai.getText().toString(), txtRolePegawai.getText().toString(), Integer.parseInt(txtNorekPegawai.getText().toString()), hitung, (simpanhabishitung-getnominal), getnominal, ((int)(gapo*hitung)+simpanGamin+simpanGabul));
                konekDB.add(gaji).addOnSuccessListener(suc -> {
                    Toast.makeText(this,"Data Gaji Terinput", Toast.LENGTH_LONG).show();
                }).addOnFailureListener(er -> {
                    Toast.makeText(this,""+er.getMessage(), Toast.LENGTH_LONG).show();
                });
                finish();
            }
        });

    }

    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                etNominalTf.removeTextChangedListener(this);
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
                    etNominalTf.setText(formattedString);
                    etNominalTf.setSelection(etNominalTf.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                etNominalTf.addTextChangedListener(this);
            }
        };
    }
}