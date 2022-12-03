package com.example.androidkpnew;

import android.app.LauncherActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.text.ParcelableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class AbsensiViewAdapter extends RecyclerView.Adapter<AbsensiViewAdapter.MyViewHolder> {

    Context context;

    ArrayList<Employee> listEMP;
    ArrayList<Absen> listABS = new ArrayList<>();
    CallDataAdapterAbsensi listenerAbsensi;
    String masukFull, masukHalf = "0";
    String getNama, getRole;
    int getNoRek;

    private static RecyclerViewClickListener listener;

    public AbsensiViewAdapter(Context context, ArrayList<Employee> list, ArrayList<Absen> abs, CallDataAdapterAbsensi listenerAbsensi) {
        this.context = context;
        this.listEMP = list;
        this.listABS = abs;
        this.listenerAbsensi = listenerAbsensi;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_absensi_anggota , parent, false);
        return new MyViewHolder(v, listenerAbsensi);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Employee pegawai = listEMP.get(position);

        holder.namaPegawai.setText("Nama : " + pegawai.getNamaPegawai());
        holder.rolePegawai.setText("Role : " +pegawai.getRolePegawai());
        holder.norekening.setText("Norek : " +pegawai.getnomorRekening());

        holder.halfDay.setOnCheckedChangeListener(null);
        holder.fullDay.setOnCheckedChangeListener(null);
        holder.halfDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.halfDay.isChecked()){
                    if(holder.fullDay.isChecked()){
                        holder.fullDay.setChecked(false);
                        masukFull = "0";
                        Toast.makeText(context, "tidak bisa pilih halfday setelah fullday", Toast.LENGTH_SHORT).show();
                    }
                    holder.halfDay.setChecked(true);
                    getNama = pegawai.getNamaPegawai();
                    getRole = pegawai.getRolePegawai();
                    getNoRek = pegawai.getnomorRekening();
//                    1 artinya masuk
                    masukHalf = "1";
                    Log.d("OUTPUT HalfDay", "checkbox HalfDay true dengan posisi nomor "+holder.getAdapterPosition()+" hasil : "+holder.halfDay.getText()+" "+getNama);

                    listenerAbsensi.addToList(getNama, getRole, getNoRek, masukFull, masukHalf);
                }else{
//                    0 artinya absen
                    masukHalf = "0";
                    holder.halfDay.setChecked(false);
                }
            }
        });
        holder.fullDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.fullDay.isChecked()){
                    if(holder.halfDay.isChecked()){
                        holder.halfDay.setChecked(false);
                        masukHalf = "0";
                        Toast.makeText(context, "tidak bisa pilih fullday setelah halfday", Toast.LENGTH_SHORT).show();
                    }
                    getNama = pegawai.getNamaPegawai();
                    getRole = pegawai.getRolePegawai();
                    getNoRek = pegawai.getnomorRekening();
//                    1 artinya masuk
                    masukFull = "1";
                    holder.fullDay.setChecked(true);
                    Log.d("OUTPUT FullDay", "checkbox FullDay true dengan posisi nomor "+holder.getAdapterPosition()+" hasil : "+holder.fullDay.getText()+" "+getNama);

                    listenerAbsensi.addToList(getNama, getRole, getNoRek, masukFull, masukHalf);
                }else{
//                    0 artinya absen
                    masukFull = "0";
                    holder.fullDay.setChecked(false);
                }
            }
        });
//        listenerAbsensi.addToList(holder.namaPegawai.getText().toString(), holder.namaPegawai.getText().toString(), Integer.parseInt(holder.norekening.getText().toString()), holder.halfDay.getText().toString(), holder.fullDay.getText().toString());


    }

    @Override
    public int getItemCount() {
        return listEMP.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView namaPegawai, rolePegawai, norekening;
        CheckBox halfDay, fullDay;

        CallDataAdapterAbsensi listenerAbsensi;

        public MyViewHolder(@NonNull View itemView, CallDataAdapterAbsensi listenerAbsensi) {
            super(itemView);

            this.listenerAbsensi = listenerAbsensi;

            namaPegawai = itemView.findViewById(R.id.list_nama_tv);
            rolePegawai = itemView.findViewById(R.id.list_role_tv);
            norekening = itemView.findViewById(R.id.list_norek_tv);
            halfDay = itemView.findViewById(R.id.checkBox_HalfDay);
            fullDay = itemView.findViewById(R.id.checkBox_FullDay);

            halfDay.setOnClickListener(this);
            fullDay.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }

    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }


}
