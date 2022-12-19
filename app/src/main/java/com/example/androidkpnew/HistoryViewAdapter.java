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
import java.util.ArrayList;

public class HistoryViewAdapter extends RecyclerView.Adapter<HistoryViewAdapter.MyViewHolder>{
    Context context;
    ArrayList<Gaji> listGaji;

    private static RecyclerViewClickListener listener;

    public HistoryViewAdapter(Context context, ArrayList<Gaji> listGaji) {
        this.context = context;
        this.listGaji = listGaji;
    }

    @NonNull
    @Override
    public HistoryViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_history_gaji , parent, false);
        return new HistoryViewAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewAdapter.MyViewHolder holder, int position) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        Gaji gaji = listGaji.get(position);
        holder.tanggal.setText("Tanggal : " + gaji.getTanggalBuat());
        holder.namaPegawai.setText("Nama : " + gaji.getNamaPegawai());
        holder.rolePegawai.setText("Role : " +gaji.getRolePegawai());
        holder.norekPegawai.setText("Nomor Rekening : " +gaji.getNomorRekening());
        holder.totalAbsen.setText("Jumlah Absen : " +gaji.getJumlahAbsen());
        holder.totalTransfer.setText("Nominal Transfer : " +gaji.getNilaiTransfer());
        holder.totalCash.setText("Nominal Cash : " +gaji.getNilaiTunai());
        holder.grandTotal.setText("Granf Total : " +gaji.getTotalGaji());

        holder.txtOption3.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.txtOption3);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.menu_hapus:
                        DAOGaji dao = new DAOGaji();
                        dao.remove(gaji.getKey()).addOnSuccessListener(suc -> {
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
    public int getItemCount() {
            return listGaji.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tanggal, namaPegawai, rolePegawai, norekPegawai, totalAbsen, totalTransfer, totalCash, grandTotal, txtOption3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tanggal = itemView.findViewById(R.id.list_tanggal_tv);
            namaPegawai = itemView.findViewById(R.id.list_nama_tv);
            rolePegawai = itemView.findViewById(R.id.list_role_tv);
            norekPegawai = itemView.findViewById(R.id.list_norek_tv);
            totalAbsen = itemView.findViewById(R.id.list_jumlahAbsensi_tv);
            totalTransfer = itemView.findViewById(R.id.list_nominaltransfer_tv);
            totalCash = itemView.findViewById(R.id.list_totalcash_tv);
            grandTotal = itemView.findViewById(R.id.list_grandtotal_tv);
            txtOption3 = itemView.findViewById(R.id.option3_txt);

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
