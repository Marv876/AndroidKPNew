package com.example.androidkpnew;

import com.google.firebase.database.Exclude;

public class Absen {

    @Exclude
    private String key;
    private String tanggal;
    private String nama;
    private String role;
    private int norek;
    private String chkBox_HalfDay;
    private String chkBox_FullDay;

    public Absen(){}

    public Absen(String tanggal, String nama, String role, int norek, String chkBox_HalfDay, String chkBox_FullDay) {
        this.tanggal = tanggal;
        this.nama = nama;
        this.role = role;
        this.norek = norek;
        this.chkBox_HalfDay = chkBox_HalfDay;
        this.chkBox_FullDay = chkBox_FullDay;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getNorek() {
        return norek;
    }

    public void setNorek(int norek) {
        this.norek = norek;
    }


    public String  getChkBox_HalfDay() {
        return chkBox_HalfDay;
    }

    public void setChkBox_HalfDay(String chkBox_HalfDay) {
        this.chkBox_HalfDay = chkBox_HalfDay;
    }

    public String getChkBox_FullDay() {
        return chkBox_FullDay;
    }

    public void setChkBox_FullDay(String chkBox_FullDay) {
        this.chkBox_FullDay = chkBox_FullDay;
    }
}
