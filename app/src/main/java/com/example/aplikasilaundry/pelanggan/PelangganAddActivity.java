package com.example.aplikasilaundry.pelanggan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplikasilaundry.R;
import com.example.aplikasilaundry.database.SQLiteHelper;
import com.example.aplikasilaundry.model.ModelPelanggan;

import java.util.UUID;

public class PelangganAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pelanggan_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText nama, email, hp;
        Button btnSimpan, btnBatal;

        nama = (EditText) findViewById(R.id.edPelAddNama);
        email = (EditText) findViewById(R.id.edPelAddEmail);
        hp = (EditText) findViewById(R.id.edPelAddHp);
        btnSimpan = (Button) findViewById(R.id.btnPelAddSimpan);
        btnBatal = (Button) findViewById(R.id.btnPelAddBatal);


        //membuat event handler untuk menyimpan data pelanggan

        SQLiteHelper db;
        db = new SQLiteHelper(PelangganAddActivity.this);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelPelanggan mp = new ModelPelanggan();
                String uniqueID = UUID.randomUUID().toString();
                mp.setId(""+uniqueID);
                mp.setNama(nama.getText().toString());
                mp.setEmail(email.getText().toString());
                mp.setHp(hp.getText().toString());

                Toast.makeText(PelangganAddActivity.this, ""+mp.getId()+mp.getNama()+mp.getEmail()+mp.getHp(), Toast.LENGTH_SHORT).show();
                boolean cek = db.insertPelanggan(mp);
                if (cek == true){
                    Toast.makeText(PelangganAddActivity.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PelangganAddActivity.this, PelangganActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(PelangganAddActivity.this, "Data Gagal Disimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //event handling ketika tombol batal diklik
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}