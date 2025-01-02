package com.example.aplikasilaundry.pelanggan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class PelangganEditActivity extends AppCompatActivity {

    String id, nama, email, hp;
    private EditText edPelEditNama, edPelEditEmail, edPelEditHp;
    private Button btnPelEditSimpan, btnPelEditHapus, btnPelEditBatal;
    private SQLiteHelper db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pelanggan_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new SQLiteHelper(this);

        id = getIntent().getStringExtra("id");
        nama = getIntent().getStringExtra("nama");
        email = getIntent().getStringExtra("email");
        hp = getIntent().getStringExtra("hp");

        Toast.makeText(this, "Id: "+id+"\nNama: "+nama+"\nEmail: "+email+"\nHp: "+hp, Toast.LENGTH_SHORT).show();

        // Initialize views
        edPelEditNama = findViewById(R.id.edPelEditNama);
        edPelEditEmail = findViewById(R.id.edPelEditEmail);
        edPelEditHp = findViewById(R.id.edPelEditHp);
        btnPelEditSimpan = findViewById(R.id.btnPelEditSimpan);
        btnPelEditHapus = findViewById(R.id.btnPelEditHapus);
        btnPelEditBatal = findViewById(R.id.btnPelEditBatal);

        // Set the existing customer data to EditText fields
        edPelEditNama.setText(nama);
        edPelEditEmail.setText(email);
        edPelEditHp.setText(hp);

        // Set listeners for buttons
        btnPelEditSimpan.setOnClickListener(v -> updateCustomer());
        btnPelEditHapus.setOnClickListener(v -> deleteCustomer());
        btnPelEditBatal.setOnClickListener(v -> cancelEdit());
    }

    // Update customer in the database
    private void updateCustomer() {
        String updatedName = edPelEditNama.getText().toString();
        String updatedEmail = edPelEditEmail.getText().toString();
        String updatedHp = edPelEditHp.getText().toString();

        // Check if fields are empty
        if (updatedName.isEmpty() || updatedEmail.isEmpty() || updatedHp.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the customer in the database
        boolean isUpdated = db.updatePelanggan(id, updatedName, updatedEmail, updatedHp);
        if (isUpdated) {
            Toast.makeText(this, "Customer updated successfully", Toast.LENGTH_SHORT).show();
            PelangganActivity pa = new PelangganActivity();
            pa.getData();
            finish(); // Close the activity after successful update
        } else {
            Toast.makeText(this, "Failed to update customer", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete customer from the database
    private void deleteCustomer() {
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