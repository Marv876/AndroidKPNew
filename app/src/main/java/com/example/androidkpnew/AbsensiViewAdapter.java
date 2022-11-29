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

    private static RecyclerViewClickListener listener;

    public AbsensiViewAdapter(Context context, ArrayList<Employee> list, ArrayList<Absen> abs) {
        this.context = context;
        this.listEMP = list;
        this.listABS = abs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_absensi_anggota , parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Employee pegawai = listEMP.get(position);

        holder.namaPegawai.setText("Nama : " + pegawai.getNamaPegawai());
        holder.rolePegawai.setText("Role : " +pegawai.getRolePegawai());

        holder.halfDay.setOnCheckedChangeListener(null);
        holder.fullDay.setOnCheckedChangeListener(null);
        holder.halfDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.halfDay.isChecked()){
                    holder.halfDay.setChecked(true);
                    Log.d("OUTPUT HalfDay", "checkbox HalfDay true dengan posisi nomor "+holder.getAdapterPosition()+" hasil : "+holder.halfDay.getText());
                }else{
                    holder.halfDay.setChecked(false);
                }
            }
        });
        holder.fullDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.fullDay.isChecked()){
                    holder.fullDay.setChecked(true);
                    Log.d("OUTPUT FullDay", "checkbox FullDay true dengan posisi nomor "+holder.getAdapterPosition()+" hasil : "+holder.fullDay.getText());
                }else{
                    holder.fullDay.setChecked(false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listEMP.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView namaPegawai, rolePegawai;
        CheckBox halfDay, fullDay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namaPegawai = itemView.findViewById(R.id.list_nama_tv);
            rolePegawai = itemView.findViewById(R.id.list_role_tv);
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
