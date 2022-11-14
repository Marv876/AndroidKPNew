package com.example.androidkpnew;

//import kotlin.Unit;

public class Employee {
    private String namaPegawai;
    private String rolePegawai;
    private int nomorRekening;
    private int gajiPokok;
    private int gajiMingguan;
    private int gajiBulanan;

    public Employee(){}

    public Employee(String namaPegawai, String rolePegawai, int nomorRekening, int gajiPokok, int gajiMingguan, int gajiBulanan){
        this.namaPegawai = namaPegawai;
        this.rolePegawai = rolePegawai;
        this.nomorRekening = nomorRekening;
        this.gajiPokok = gajiPokok;
        this.gajiMingguan = gajiMingguan;
        this.gajiBulanan = gajiBulanan;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    //    public String getRolePegawai(Unit onItemSelectedListener) {
//        return rolePegawai;
//    }
    public String getRolePegawai() { return rolePegawai; }

    public void setRolePegawai(String rolePegawai) {
        this.rolePegawai = rolePegawai;
    }

    public int getnomorRekening() { return nomorRekening; }

    public void setNomorRekening(int nomorRekening) {
        this.nomorRekening = nomorRekening;
    }

    public int getGajiPokok() {
        return gajiPokok;
    }

    public void setGajiPokok(int gajiPokok) {
        this.gajiPokok = gajiPokok;
    }

    public int getGajiMingguan() {
        return gajiMingguan;
    }

    public void setGajiMingguan(int gajiMingguan) {
        this.gajiMingguan = gajiMingguan;
    }

    public int getGajiBulanan() {
        return gajiBulanan;
    }

    public void setGajiBulanan(int gajiBulanan) {
        this.gajiBulanan = gajiBulanan;
    }
}