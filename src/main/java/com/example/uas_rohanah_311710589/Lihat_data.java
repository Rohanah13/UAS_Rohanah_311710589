package com.example.uas_rohanah_311710589;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Lihat_data extends AppCompatActivity {

    Toolbar toolbar;
    TextView id_barang, kode_barang, nama_barang, size, jumlah;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lihat_data);

        id_barang = findViewById(R.id.id_barang);
        kode_barang=findViewById(R.id.kode_barang);
        nama_barang=findViewById(R.id.nama_barang);
        size=findViewById(R.id.size);
        jumlah=findViewById(R.id.jumlah);

        //ambil data intent

        id_barang.setText(getIntent().getStringExtra("id_barang"));
        kode_barang.setText(getIntent().getStringExtra("kode_barang"));
        nama_barang.setText(getIntent().getStringExtra("nama_barang"));
        size.setText(getIntent().getStringExtra("size"));
        jumlah.setText(getIntent().getStringExtra("jumlah"));

    }
}