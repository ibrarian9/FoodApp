package com.app.foodapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.foodapp.databinding.ActivityNotaBinding;

public class NotaActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNotaBinding binding = ActivityNotaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.back.setOnClickListener( v -> {
            startActivity(new Intent(NotaActivity.this, MainActivity.class));
            finish();
        });

        String nama = getIntent().getStringExtra("nama");
        int harga = getIntent().getIntExtra("harga", 1);
        int jumlah = getIntent().getIntExtra("jumlah", 1);
        int total = getIntent().getIntExtra("total", 1);

        binding.nama.setText("Makanan : " + nama);
        binding.harga.setText("Harga : " + harga);
        binding.jumlah.setText("Jumlah : " + jumlah);
        binding.total.setText("Total Harga : " + total);
    }
}