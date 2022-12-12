package com.example.androidkpnew;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
    ArrayList<Employee> listPegawai;
    CallDataAdapterGaji listenerGaji;
    Locale id = new Locale("in","ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy",id);

    private static GajiViewAdapter.RecyclerViewClickListener listener;

    public GajiViewAdapter(Context context, ArrayList<Absen> list, ArrayList<Gaji> listGaji, ArrayList<Employee> listPegawai, CallDataAdapterGaji listenerGaji) {
        this.context = context;
        this.list = list;
        this.listGaji = listGaji;
        this.listPegawai = listPegawai;
        this.listenerGaji = listenerGaji;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        cara 1
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
//        cara 2
        View v = LayoutInflater.from(context).inflate(R.layout.list_gaji_pegawai , parent, false);
        return new GajiViewAdapter.MyViewHolder(v, listenerGaji);
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
        holder.tanggal.setText("Tanggal : "+abs.getTanggal());
        holder.namaPegawai.setText("Nama : " +abs.getnamaPegawai());
        holder.rolePegawai.setText("Role : " +abs.getrolePegawai());
        holder.norekPegawai.setText("Nomor Rekening : " +abs.getnomorRekening());
//        int totalMasukFull = Integer.parseInt(abs.getfullDay());
//        int totalMasukHalf = Integer.parseInt(abs.gethalfDay());
        int totalMasukFull = 0, totalMasukHalf = 0;
        Log.d("chkBox", " : "+abs.getfullDay()+", "+abs.gethalfDay());
        if(abs.gethalfDay() == "1"){
            totalMasukHalf = 1;
        }
        if(abs.getfullDay() == "1"){
            totalMasukFull = 1;
        }
        Log.d("totalMasukFull", " : "+totalMasukFull);
        Log.d("totalMasukHalf", " : "+totalMasukHalf);
        float pemecah = totalMasukHalf/2;
        float jumlahAbsen = totalMasukFull+pemecah;
        holder.jumlahAbsensi.setText("Jumlah Absen : "+jumlahAbsen+" hari");
        listenerGaji.addToList(abs.getnamaPegawai(), abs.getrolePegawai(), abs.getnomorRekening(), jumlahAbsen);

        holder.txtOption.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.txtOption);
            popupMenu.inflate(R.menu.option_menu2);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.menu_tambah:
                        Intent startIntent = new Intent(context, BuatGajiPegawai.class);
                        startIntent.putExtra("ADD", abs);
                        try {
                            context.startActivity(startIntent);
                        } catch (ActivityNotFoundException e) {
                            // Define what your app should do if no activity can handle the intent.
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        break;
//                    case R.id.menu_ubah:
//                        Intent myIntent = new Intent(context, UbahGajiPegawai.class);
//                        myIntent.putExtra("UPDATE", gaji);
//                        try {
//                            context.startActivity(myIntent);
//                        } catch (ActivityNotFoundException e) {
//                            // Define what your app should do if no activity can handle the intent.
//                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                        break;
//                    case R.id.menu_hapus:
//                        DAOGaji dao = new DAOGaji();
//                        dao.remove(listGaji.getKey()).addOnSuccessListener(suc -> {
//                            Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
////                            notifyItemRemoved(position);
//                        }).addOnFailureListener(er -> {
//                            Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
//                        });
//                        break;
                }
                return false;
            });
            popupMenu.show();
        });

    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tanggal, namaPegawai, rolePegawai, norekPegawai, jumlahAbsensi, txtOption;

        CallDataAdapterGaji listenerGaji;

        public MyViewHolder(@NonNull View itemView, CallDataAdapterGaji listenerGaji) {
            super(itemView);

            this.listenerGaji =  listenerGaji;

            tanggal = itemView.findViewById(R.id.list_tanggal_tv);
            namaPegawai = itemView.findViewById(R.id.list_nama_tv);
            rolePegawai = itemView.findViewById(R.id.list_role_tv);
            norekPegawai = itemView.findViewById(R.id.list_norek_tv);
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
