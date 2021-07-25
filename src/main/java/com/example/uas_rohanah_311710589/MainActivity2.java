package com.example.uas_rohanah_311710589;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    LinearLayout input_data, tampil_data, keluar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tampil_data=findViewById(R.id.tampil_data);
        input_data=findViewById(R.id.input_data);
        keluar=findViewById(R.id.keluar);

        input_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Input_data.class));

            }
        });
        tampil_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Tampil_data.class));
            }
        });
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                Toast.makeText(getApplicationContext(),"Thanks", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

