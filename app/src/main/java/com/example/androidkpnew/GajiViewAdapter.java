package com.example.androidkpnew;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class GajiViewAdapter extends RecyclerView.Adapter<GajiViewAdapter.MyViewHolder>{
    Context context;
    ArrayList<Absen> list;
    ArrayList<Gaji> listGaji;
    Locale id = new Locale("in","ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy",id);

    private static GajiViewAdapter.RecyclerViewClickListener listener;

    public GajiViewAdapter(Context context, ArrayList<Absen> list, ArrayList<Gaji> listGaji) {
        this.context = context;
        this.list = list;
        this.listGaji = listGaji;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        cara 1
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
//        cara 2
        View v = LayoutInflater.from(context).inflate(R.layout.list_anggota_pegawai , parent, false);
        return new GajiViewAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        Absen abs = list.get(position);
        Gaji gaji = listGaji.get(position);
        holder.namaPegawai.setText("Nama : " +abs.getnamaPegawai());
        holder.rolePegawai.setText("Role : " +abs.getrolePegawai());
        holder.norekPegawai.setText("Nomor Rekening : " +abs.getnomorRekening());

        holder.txtOption.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.txtOption);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.menu_ubah:
                        Intent myIntent = new Intent(context, UbahGajiPegawai.class);
                        myIntent.putExtra("UPDATE", gaji);
                        try {
                            context.startActivity(myIntent);
                        } catch (ActivityNotFoundException e) {
                            // Define what your app should do if no activity can handle the intent.
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.menu_tambah:
                        Intent startIntent = new Intent(context, BuatGajiPegawai.class);
                        try {
                            context.startActivity(startIntent);
                        } catch (ActivityNotFoundException e) {
                            // Define what your app should do if no activity can handle the intent.
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.menu_hapus:
                        DAOGaji dao = new DAOGaji();
                        dao.remove(listGaji.getKey()).addOnSuccessListener(suc -> {
                            Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
//                            notifyItemRemoved(position);
                        }).addOnFailureListener(er -> {
                            Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                        break;
                }
                return false;
            });
            popupMenu.show();
        });

    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView namaPegawai, rolePegawai, norekPegawai, jumlahAbsensi, txtOption;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namaPegawai = itemView.findViewById(R.id.namapegawai_et);
            rolePegawai = itemView.findViewById(R.id.list_role_tv);
            norekPegawai = itemView.findViewById(R.id.norekening_et);
            jumlahAbsensi = itemView.findViewById(R.id.list_jumlahAbsensi_tv);
            txtOption = itemView.findViewById(R.id.option2_txt);

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
