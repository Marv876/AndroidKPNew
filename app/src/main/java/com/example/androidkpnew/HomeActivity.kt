package com.example.androidkpnew;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var btnAbsensi : Button = findViewById(R.id.absensi_btn)
        var btnGaji : Button = findViewById(R.id.gaji_btn)
        var btnAnggota : Button = findViewById(R.id.anggota_btn)

        btnAbsensi.setOnClickListener{
            val intent = Intent(this, AbsensiActivity::class.java)
            startActivity(intent)
        }

        btnGaji.setOnClickListener{
            val intent = Intent(this, GajiActivity::class.java)
            startActivity(intent)
        }

        btnAnggota.setOnClickListener{
            val intent = Intent(this, DaftarAnggotaActivity::class.java)
            startActivity(intent)
        }

    }
}