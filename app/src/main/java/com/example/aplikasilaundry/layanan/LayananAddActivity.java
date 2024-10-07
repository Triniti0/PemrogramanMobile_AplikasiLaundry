package com.example.aplikasilaundry.layanan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasilaundry.R;
import com.example.aplikasilaundry.database.SQLiteHelper2;
import com.example.aplikasilaundry.model.ModelLayanan;

import java.util.UUID;


public class LayananAddActivity extends AppCompatActivity{
    EditText edtLayananName, edtLayananPrice; // Variabel untuk nama dan harga layanan
    Button btnAddLayanan, btnCancel;
    SQLiteHelper2 db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_add);

        // Inisialisasi Views
        edtLayananName = findViewById(R.id.edLayananName);
        edtLayananPrice = findViewById(R.id.edLayananPrice);
        btnAddLayanan = findViewById(R.id.btnPelAddSimpan);
        btnCancel = findViewById(R.id.btnPelAddBatal);

        // Inisialisasi SQLiteHelper
        db = new SQLiteHelper2(LayananAddActivity.this);

        // Set action untuk tombol Simpan
        btnAddLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelLayanan ml = new ModelLayanan();
                String uuid = UUID.randomUUID().toString(); // Generate unique ID
                ml.setId(uuid); // Set ID sebagai String UUID
                ml.setName(edtLayananName.getText().toString());

                // Parsing harga ke integer
                String priceText = edtLayananPrice.getText().toString();
                int price;

                try {
                    price = Integer.parseInt(priceText); // Mengonversi input menjadi integer
                } catch (NumberFormatException e) {
                    Toast.makeText(LayananAddActivity.this, "Harga tidak valid", Toast.LENGTH_SHORT).show();
                    return; // Menghentikan eksekusi jika harga tidak valid
                }

                ml.setPrice(price); // Mengatur harga ke model layanan

                // Menyimpan ke database dan memeriksa apakah berhasil
                boolean cek = db.insertService(ml.getId(), ml.getName(), ml.getPrice());
                if (cek) {
                    Toast.makeText(LayananAddActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LayananAddActivity.this, LayananActivity.class));
                    finish();
                } else {
                    Toast.makeText(LayananAddActivity.this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Fungsi untuk tombol Batal
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Menutup activity jika tombol dibatalkan
            }
        });
    }
}
