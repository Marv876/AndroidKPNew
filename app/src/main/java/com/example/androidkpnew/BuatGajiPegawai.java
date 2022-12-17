package com.example.androidkpnew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

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
    private LinearLayout line1;
    private TextView txtGamin;
    private LinearLayout line2;
    private TextView txtGabul;
    private Button btnCreate;
    int nominalTransfer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_gaji_pegawai);

        txtNamaPegawai = findViewById(R.id.txt_namaPegawai);
        txtRolePegawai = findViewById(R.id.txt_rolePegawai);
        txtNorekPegawai = findViewById(R.id.txt_norekPegawai);
        txtTotalFull = findViewById(R.id.fullday_txt);
        txtTotalHalf = findViewById(R.id.halfday_txt);
        txtNamaPegawai = findViewById(R.id.txt_namaPegawai);
        etNominalTf = findViewById(R.id.et_nominalTransfer);
        chkMingguan = findViewById(R.id.chkBox_mingguan);
        chkBulanan = findViewById(R.id.chkBox_bulanan);
        txtAbsens = findViewById(R.id.txt_absen);
        txtGajiPokok = findViewById(R.id.txt_gapoPegawai);
        txtTotalAbsensi = findViewById(R.id.txt_totalAbsensi);
        line1 = findViewById(R.id.line1_gajiBonus);
        txtGamin = findViewById(R.id.txt_gaminPegawai);
        line2 = findViewById(R.id.line2_gajiBonus);
        txtGabul = findViewById(R.id.txt_gabulPegawai);
        btnCreate = findViewById(R.id.buatData_btn);

        Absen abs_edit = (Absen)getIntent().getSerializableExtra("ADD");
        if(abs_edit != null){
            txtNamaPegawai.setText(abs_edit.getnamaPegawai());
            txtRolePegawai.setText(abs_edit.getrolePegawai());
            txtNorekPegawai.setText(String.valueOf(abs_edit.getnomorRekening()));
            txtTotalFull.setText(abs_edit.getfullDay());
            txtTotalHalf.setText(abs_edit.gethalfDay());
        }

        if(etNominalTf.getText().toString() != ""){
            nominalTransfer = Integer.parseInt(etNominalTf.getText().toString());
        }else{
            nominalTransfer = 0;
        }
//

        chkMingguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkMingguan.isChecked() == true){
                    line1.setVisibility(View.VISIBLE);
                }else {
                    line1.setVisibility(View.GONE);
                }
            }
        });
        chkBulanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkBulanan.isChecked() == true){
                    line2.setVisibility(View.VISIBLE);
                }else {
                    line2.setVisibility(View.GONE);
                }
            }
        });
//        if(chkMingguan.isChecked()){
//            line1.setVisibility(View.VISIBLE);
//        }
//        if(chkBulanan.isChecked()){
//            line2.setVisibility(View.VISIBLE);
//        }


    }
}