package com.example.androidkpnew;

import android.app.LauncherActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.ParcelableSpan;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class PegawaiViewAdapter extends RecyclerView.Adapter<PegawaiViewAdapter.MyViewHolder> {

    Context context;

    ArrayList<Employee> list;

    private static RecyclerViewClickListener listener;

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

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        Employee pegawai = list.get(position);
        holder.namaPegawai.setText("Nama : " + pegawai.getNamaPegawai());
        holder.rolePegawai.setText("Role : " +pegawai.getRolePegawai());
        holder.norekPegawai.setText("Nomor Rekening : " +pegawai.getnomorRekening());
        holder.gapoPegawai.setText("Gaji Pokok : " +kursIndonesia.format(pegawai.getGajiPokok()));
        holder.gaminPegawai.setText("Gaji Mingguan : " +kursIndonesia.format(pegawai.getGajiMingguan()));
        holder.gabulPegawai.setText("Gaji Bulanan : " +kursIndonesia.format(pegawai.getGajiBulanan()));

        holder.txtOption.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.txtOption);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case R.id.menu_edit:
                        Intent myIntent = new Intent(context, UbahDataPegawai.class);
                        myIntent.putExtra("EDIT", pegawai);
                        try {
                            context.startActivity(myIntent);
                        } catch (ActivityNotFoundException e) {
                            // Define what your app should do if no activity can handle the intent.
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    case R.id.menu_hapus:
                        DAOEmployee dao = new DAOEmployee();
                        dao.remove(pegawai.getKey()).addOnSuccessListener(suc -> {
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
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView namaPegawai, rolePegawai, norekPegawai, gapoPegawai, gaminPegawai, gabulPegawai, txtOption;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namaPegawai = itemView.findViewById(R.id.list_nama_tv);
            rolePegawai = itemView.findViewById(R.id.list_role_tv);
            norekPegawai = itemView.findViewById(R.id.list_norek_tv);
            gapoPegawai = itemView.findViewById(R.id.list_gajiPokok_tv);
            gaminPegawai = itemView.findViewById(R.id.list_gajiMingguan_tv);
            gabulPegawai = itemView.findViewById(R.id.list_gajiBulanan_tv);
            txtOption = itemView.findViewById(R.id.option_txt);

//            itemView.setOnClickListener(this);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("OUTPUT", "onClick: data yang keluar adalah " + namaPegawai.getText());
//                    Intent myIntent = new Intent(view.getContext(), UbahDataPegawai.class);
//                    myIntent.putExtra("nama", namaPegawai.getText());
//                    myIntent.putExtra("role", rolePegawai.getText());
//                    myIntent.putExtra("norek", norekPegawai.getText());
//                    myIntent.putExtra("gapo", gaminPegawai.getText());
//                    myIntent.putExtra("gamin", gaminPegawai.getText());
//                    myIntent.putExtra("gabul", gabulPegawai.getText());
//                    try {
//                        view.getContext().startActivity(myIntent);
//                    } catch (ActivityNotFoundException er) {
//                        // Define what your app should do if no activity can handle the intent.
////                        Toast.makeText(this, ""+er.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
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
