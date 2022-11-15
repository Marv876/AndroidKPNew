package com.example.androidkpnew;

import android.content.Context;
import android.content.Intent;
import android.text.ParcelableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;

import java.util.ArrayList;

public class PegawaiViewAdapter extends RecyclerView.Adapter<PegawaiViewAdapter.MyViewHolder> {

    Context context;

    ArrayList<Employee> list;

//    private static RecyclerView.RecyclerListener itemListener;

    public PegawaiViewAdapter(Context context, ArrayList<Employee> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_anggota_pegawai , parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Employee pegawai = list.get(position);
        holder.namaPegawai.setText("Nama : " + pegawai.getNamaPegawai());
        holder.rolePegawai.setText("Role : " +pegawai.getRolePegawai());
        holder.norekPegawai.setText("Nomor Rekening : " +pegawai.getnomorRekening());
        holder.gapoPegawai.setText("Gaji Pokok : " +pegawai.getGajiPokok());
        holder.gaminPegawai.setText("Gaji Mingguan : " +pegawai.getGajiMingguan());
        holder.gabulPegawai.setText("Gaji Bulanan : " +pegawai.getGajiBulanan());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView namaPegawai, rolePegawai, norekPegawai, gapoPegawai, gaminPegawai, gabulPegawai;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namaPegawai = itemView.findViewById(R.id.list_nama_tv);
            rolePegawai = itemView.findViewById(R.id.list_role_tv);
            norekPegawai = itemView.findViewById(R.id.list_norek_tv);
            gapoPegawai = itemView.findViewById(R.id.list_gajiPokok_tv);
            gaminPegawai = itemView.findViewById(R.id.list_gajiMingguan_tv);
            gabulPegawai = itemView.findViewById(R.id.list_gajiBulanan_tv);
        }
    }



}
