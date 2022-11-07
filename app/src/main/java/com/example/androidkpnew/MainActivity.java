package com.example.androidkpnew;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText satu = findViewById(R.id.satu);
        final EditText dua = findViewById(R.id.dua);
        Button gas = findViewById(R.id.gas);

        DAOEKaryawan jir = new DAOEKaryawan();
        gas.setOnClickListener(view -> {
            Karyawan orang = new Karyawan(satu.getText().toString(),dua.getText().toString());
            jir.add(orang).addOnSuccessListener(suc -> {
                Toast.makeText(this,"Telah Terinput", Toast.LENGTH_LONG).show();
            }).addOnFailureListener(er -> {
                Toast.makeText(this,""+er.getMessage(), Toast.LENGTH_LONG).show();
            });
        });
    }
}
