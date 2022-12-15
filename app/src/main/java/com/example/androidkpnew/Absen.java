package com.example.androidkpnew;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Absen implements Serializable {

    @Exclude
    private String key;
    private String tanggal;
    private String namaPegawai;
    private String rolePegawai;
    private int nomorRekening;
    private String halfDay;
    private String fullDay;

    public Absen(){}

    public Absen(String namaPegawai,String halfDay, String fullDay) {
        this.namaPegawai = namaPegawai;
        this.halfDay = halfDay;
        this.fullDay = fullDay;
    }

    public Absen(String tanggal, String namaPegawai, String rolePegawai, int nomorRekening, String halfDay, String fullDay) {
        this.tanggal = tanggal;
        this.namaPegawai = namaPegawai;
        this.rolePegawai = rolePegawai;
        this.nomorRekening = nomorRekening;
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

    public String getnamaPegawai() {
        return namaPegawai;
    }

    public void setnamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getrolePegawai() {
        return rolePegawai;
    }

    public void setrolePegawai(String rolePegawai) {
        this.rolePegawai = rolePegawai;
    }

    public int getnomorRekening() {
        return nomorRekening;
    }

    public void setnomorRekening(int nomorRekening) {
        this.nomorRekening = nomorRekening;
    }

    public String  gethalfDay() {
        return halfDay;
    }

    public void sethalfDay(String halfDay) {
        this.halfDay = halfDay;
    }

    public String getfullDay() {
        return fullDay;
    }

    public void setfullDay(String chkBox_FullDay) {
        this.fullDay = fullDay;
    }
}
