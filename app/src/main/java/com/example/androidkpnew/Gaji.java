package com.example.androidkpnew;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Gaji implements Serializable {

    @Exclude
    private String key;
    private String tanggalBuat;
    private String namaPegawai;
    private String rolePegawai;
    private int nomorRekening;
    private int jumlahAbsen;
    private int nilaiTunai;
    private int nilaiTransfer;
    private int totalGaji;

    public Gaji(){}

    public Gaji(String tanggalBuat,  String namaPegawai, String rolePegawai, int nomorRekening, int jumlahAbsen, int nilaiTunai, int nilaiTransfer, int totalGaji) {
        this.tanggalBuat = tanggalBuat;
        this.namaPegawai = namaPegawai;
        this.rolePegawai = rolePegawai;
        this.nomorRekening = nomorRekening;
        this.jumlahAbsen = jumlahAbsen;
        this.nilaiTunai = nilaiTunai;
        this.nilaiTransfer = nilaiTransfer;
        this.totalGaji = totalGaji;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTanggalBuat() {
        return tanggalBuat;
    }

    public void setTanggalBuat(String tanggalBuat) {
        this.tanggalBuat = tanggalBuat;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getRolePegawai() {
        return rolePegawai;
    }

    public void setRolePegawai(String rolePegawai) {
        this.rolePegawai = rolePegawai;
    }

    public int getNomorRekening() {
        return nomorRekening;
    }

    public void setNomorRekening(int nomorRekening) {
        this.nomorRekening = nomorRekening;
    }

    public int getJumlahAbsen() {
        return jumlahAbsen;
    }

    public void setJumlahAbsen(int jumlahAbsen) {
        this.jumlahAbsen = jumlahAbsen;
    }

    public int getNilaiTunai() {
        return nilaiTunai;
    }

    public void setNilaiTunai(int nilaiTunai) {
        this.nilaiTunai = nilaiTunai;
    }

    public int getNilaiTransfer() {
        return nilaiTransfer;
    }

    public void setNilaiTransfer(int nilaiTransfer) {
        this.nilaiTransfer = nilaiTransfer;
    }

    public int getTotalGaji() {
        return totalGaji;
    }

    public void setTotalGaji(int totalGaji) {
        this.totalGaji = totalGaji;
    }

}
