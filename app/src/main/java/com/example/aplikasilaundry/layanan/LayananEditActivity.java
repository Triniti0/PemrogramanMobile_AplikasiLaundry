package com.example.aplikasilaundry.layanan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.aplikasilaundry.pelanggan.PelangganActivity;

public class LayananEditActivity extends AppCompatActivity {

    String id, layanan, harga;
    private EditText edLayEditLayanan, edLayEditHarga;
    private Button btnLayEditSimpan, btnLayEditHapus, btnLayEditBatal;
    private SQLiteHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layanan_editactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new SQLiteHelper(this);

        id = getIntent().getStringExtra("id");
        layanan = getIntent().getStringExtra("layanan");
        harga = getIntent().getStringExtra("harga");

        Toast.makeText(this, "Id: " + id + "\nLayanan: " + layanan + "\nHarga: " + harga, Toast.LENGTH_SHORT).show();

        // Initialize views
        edLayEditLayanan = findViewById(R.id.edLayEditLayanan);
        edLayEditHarga = findViewById(R.id.edLayEditHarga);
        btnLayEditSimpan = findViewById(R.id.btnLayEditSimpan);
        btnLayEditHapus = findViewById(R.id.btnLayEditHapus);
        btnLayEditBatal = findViewById(R.id.btnLayEditBatal);

        // Set the existing customer data to EditText fields
        edLayEditLayanan.setText(layanan);
        edLayEditHarga.setText(harga);

        // Set listeners for buttons
        btnLayEditSimpan.setOnClickListener(v -> updateLayanan());
        btnLayEditHapus.setOnClickListener(v -> deleteLayanan());
        btnLayEditBatal.setOnClickListener(v -> cancelEdit());
    }
    // Update customer in the database
    private void updateLayanan() {
        String updatedLayanan = edLayEditLayanan.getText().toString();
        String updatedHarga = edLayEditHarga.getText().toString();

        // Check if fields are empty
        if (updatedLayanan.isEmpty() || updatedHarga.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the customer in the database
        boolean isUpdated = db.updateLayanan(id, updatedLayanan, updatedHarga);
        if (isUpdated) {
            Toast.makeText(this, "Layanan updated successfully", Toast.LENGTH_SHORT).show();
            LayananActivity pa = new LayananActivity();
            pa.getData();
            finish(); // Close the activity after successful update
        } else {
            Toast.makeText(this, "Failed to update layanan", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete customer from the database
    private void deleteLayanan() {
        // Confirmation dialog before deleting
        new AlertDialog.Builder(this)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this customer?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    boolean isDeleted = db.deletePelanggan(id);
                    if (isDeleted) {
                        Toast.makeText(this, "Customer deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, PelangganActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Close the current activity after deletion
                    } else {
                        Toast.makeText(this, "Failed to delete customer", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Cancel edit and go back to the previous screen
    private void cancelEdit() {
        finish(); // Close the activity without making any changes
    }
}