package com.example.androidkpnew;

import com.google.firebase.database.Exclude;

public class Absen {

    @Exclude
    private String key;
    private String tanggal;
    private String nama;
    private String role;
    private int norek;
    private String halfDay;
    private String fullDay;

    public Absen(){}

    public Absen(String tanggal, String nama, String role, int norek, String halfDay, String fullDay) {
        this.tanggal = tanggal;
        this.nama = nama;
        this.role = role;
        this.norek = norek;
        this.halfDay = halfDay;
        this.fullDay = fullDay;
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


    public String  gethalfDay() {
        return halfDay;
    }

    public void serhalfDay(String halfDay) {
        this.halfDay = halfDay;
    }

    public String getfullDay() {
        return fullDay;
    }

    public void setfullDay(String chkBox_FullDay) {
        this.fullDay = fullDay;
    }
}
