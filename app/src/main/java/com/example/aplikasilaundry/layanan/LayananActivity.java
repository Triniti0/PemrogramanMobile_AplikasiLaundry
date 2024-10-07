package com.example.aplikasilaundry.layanan;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasilaundry.R;
import com.example.aplikasilaundry.adapter.AdapterLayanan;
import com.example.aplikasilaundry.database.SQLiteHelper2;
import com.example.aplikasilaundry.model.ModelLayanan;

import java.util.ArrayList;
import java.util.List;

public class LayananActivity extends AppCompatActivity {

    private SQLiteHelper2 db; // Database helper untuk layanan
    private Button btnAddLayanan; // Tombol untuk menambah layanan
    private RecyclerView rvLayanan; // RecyclerView untuk menampilkan data layanan
    private AdapterLayanan adapterLayanan; // Adapter untuk RecyclerView
    private ArrayList<ModelLayanan> list; // List untuk menyimpan data layanan
    private ProgressDialog progressDialog; // Untuk menampilkan loading dialog
    private AlphaAnimation btnAnimasi = new AlphaAnimation(1F, 0.5F); // Animasi tombol

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layanan); // Pastikan ini sesuai dengan layout yang benar

        // Set window insets untuk system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi view dan event handling
        setView();
        eventHandling();
        getData();
    }

    private void eventHandling() {
        btnAddLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LayananActivity.this, LayananAddActivity.class));
            }
        });
    }

    private void getData() {
        list.clear(); // Bersihkan list sebelum mengambil data baru
        showMsg(); // Tampilkan loading dialog

        try {
            List<ModelLayanan> p = db.getAllServices(); // Ambil data dari database
            if (p != null && p.size() > 0) {
                list.addAll(p); // Tambahkan data ke list
                adapterLayanan.notifyDataSetChanged(); // Beritahu adapter bahwa ada data baru
            } else {
                Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            progressDialog.dismiss(); // Sembunyikan loading dialog
        }
    }

    private void setView() {
        db = new SQLiteHelper2(this); // Inisialisasi SQLiteHelper untuk layanan
        progressDialog = new ProgressDialog(this); // Inisialisasi ProgressDialog
        btnAddLayanan = findViewById(R.id.btnPLAdd); // Temukan tombol tambah layanan dari layout
        rvLayanan = findViewById(R.id.rvLayanan); // Temukan RecyclerView dari layout
        list = new ArrayList<>(); // Inisialisasi list untuk menampung data layanan

        // Inisialisasi LinearLayoutManager untuk RecyclerView
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvLayanan.setLayoutManager(llm); // Set layout manager ke RecyclerView
        rvLayanan.setHasFixedSize(true); // Optimalkan ukuran RecyclerView

        // Inisialisasi adapter untuk RecyclerView
        adapterLayanan = new AdapterLayanan(this, list);
        rvLayanan.setAdapter(adapterLayanan); // Set adapter ke RecyclerView
    }

    private void showMsg() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Informasi");
            progressDialog.setMessage("Loading Data...");
            progressDialog.setCancelable(false); // Tidak bisa dibatalkan dengan menekan di luar
        }
        progressDialog.show(); // Tampilkan dialog
    }
}