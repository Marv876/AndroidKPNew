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
    String dateStringStart, dateStringEnd;
    ArrayList<Double> masukFull;
    ArrayList<Double> masukHalf;
    int size = 0, ctr = 0;
    Locale id = new Locale("in","ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy",id);

    private static GajiViewAdapter.RecyclerViewClickListener listener;

    public GajiViewAdapter(Context context, ArrayList<Absen> list, ArrayList<Gaji> listGaji, ArrayList<Employee> listPegawai, CallDataAdapterGaji listenerGaji,  String dateStringStart, String dateStringEnd, ArrayList<Double> masukHalf, ArrayList<Double> masukFull) {
        this.context = context;
        this.list = list;
        this.listGaji = listGaji;
        this.listPegawai = listPegawai;
        this.listenerGaji = listenerGaji;
        this.dateStringStart = dateStringStart;
        this.dateStringEnd = dateStringEnd;
        this.masukHalf = masukHalf;
        this.masukFull = masukFull;
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
//        holder.tanggal.setText("Tanggal : "+abs.getTanggal());
        holder.namaPegawai.setText("Nama : " +abs.getnamaPegawai());
        holder.rolePegawai.setText("Role : " +abs.getrolePegawai());
        holder.norekPegawai.setText("Nomor Rekening : " +abs.getnomorRekening());
        size = masukHalf.size();
        Double pemecah;
        Double jumlahAbsen = 0.0;
        if(ctr < size){
            pemecah = masukHalf.get(ctr)/2.0;
            jumlahAbsen = (Double) masukFull.get(ctr) + pemecah;
            ctr++;
        }

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

        CallDataAdapterGaji listenerGaji;

        public MyViewHolder(@NonNull View itemView, CallDataAdapterGaji listenerGaji) {
            super(itemView);

            this.listenerGaji =  listenerGaji;

//            tanggal = itemView.findViewById(R.id.list_tanggal_tv);
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
