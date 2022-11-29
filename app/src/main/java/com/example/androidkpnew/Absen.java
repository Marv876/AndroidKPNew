package com.example.androidkpnew;

public class Absen {
    private String tanggal;
    private String nama;
    private Boolean chkBox_HalfDay;
    private Boolean chkBox_FullDay;

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

    public Boolean getChkBox_HalfDay() {
        return chkBox_HalfDay;
    }

    public void setChkBox_HalfDay(Boolean chkBox_HalfDay) {
        this.chkBox_HalfDay = chkBox_HalfDay;
    }

    public Boolean getChkBox_FullDay() {
        return chkBox_FullDay;
    }

    public void setChkBox_FullDay(Boolean chkBox_FullDay) {
        this.chkBox_FullDay = chkBox_FullDay;
    }
}
