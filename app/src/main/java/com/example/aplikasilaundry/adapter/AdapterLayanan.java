package com.example.aplikasilaundry.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasilaundry.R;
import com.example.aplikasilaundry.model.ModelLayanan;

import java.util.List;
public class AdapterLayanan extends RecyclerView.Adapter<AdapterLayanan.LayananViewHolder> {
    private Context context;
    private List<ModelLayanan> layananList;

    // Konstruktor AdapterLayanan
    public AdapterLayanan(Context context, List<ModelLayanan> layananList) {
        this.context = context;
        this.layananList = layananList;
    }

    @NonNull
    @Override
    public LayananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout untuk item layanan
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layanan, parent, false);
        return new LayananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LayananViewHolder holder, int position) {
        // Menghubungkan data layanan dengan tampilan
        ModelLayanan layanan = layananList.get(position);
        holder.txtName.setText(layanan.getName());
        holder.txtPrice.setText(String.valueOf(layanan.getPrice()));
    }

    @Override
    public int getItemCount() {
        // Mengembalikan jumlah item dalam daftar layanan
        return layananList.size();
    }

    // ViewHolder untuk AdapterLayanan
    public class LayananViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice;

        public LayananViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.tvItemLayananName);
            txtPrice = itemView.findViewById(R.id.tvItemHarga);
        }
    }
}
