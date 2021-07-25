package com.example.uas_rohanah_311710589;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Input_data extends AppCompatActivity {
    EditText kode_barang, nama_barang, size, jumlah;
    Button simpan_data;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        kode_barang = (EditText) findViewById(R.id.kode_barang);
        nama_barang = (EditText) findViewById(R.id.nama_barang);
        size = (EditText) findViewById(R.id.size);
        jumlah = (EditText) findViewById(R.id.jumlah);
        simpan_data = (Button) findViewById(R.id.simpan_data);
        progressDialog = new ProgressDialog(Input_data.this);

        // if (getSupportActionBar() != null) {
        //   getSupportActionBar().setTitle("PO.Murni Jaya Cibarusah");
        //}


        simpan_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String skode_barang = kode_barang.getText().toString();
                String snama_barang = nama_barang.getText().toString();
                String ssize = size.getText().toString();
                String sjumlah = jumlah.getText().toString();



                if (TextUtils.isEmpty(skode_barang)) {
                    kode_barang.setError("kode barang tidak boleh kosong");
                } else if (TextUtils.isEmpty(snama_barang)){
                    nama_barang.setError("nama barang tidak boleh kosong");
                    return;
                } else if (TextUtils.isEmpty(ssize)) {
                    size.setError("size tidak boleh kosong");
                    return;
                } else if (TextUtils.isEmpty(sjumlah)) {
                    jumlah.setError("jumlah tidak boleh kosong");
                }
                CreateDataToServer(skode_barang, snama_barang, ssize, sjumlah);
            }
        });
    }
    public void CreateDataToServer(final String kode_barang, final String nama_barang,final String size, final String jumlah) {
        if (checkNetworkConnection()) {
            progressDialog.show();
            StringRequest stringRequest= new StringRequest(Request.Method.POST, Contract.SERVER_INPUT,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String resp = jsonObject.getString("server_response");
                                if(resp.equals("[{\"status\":\"OK\"}]"))
                                {
                                    Toast.makeText(getApplicationContext(), "Data Berhasil Di Simpan", Toast.LENGTH_LONG).show();
                                    AlertDialog.Builder builder=
                                            new AlertDialog.Builder(Input_data.this);
                                    builder.setTitle("Sukses");
                                    builder.setMessage("Data Berhasil Di Simpan");
                                    builder.setPositiveButton("oke",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    finish();
                                                }
                                            });
                                    AlertDialog dialog= builder.create();
                                    dialog.show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                }
                            }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("kode_barang", kode_barang);
                    params.put("nama_barang", nama_barang);
                    params.put("size", size);
                    params.put("jumlah", jumlah);
                    return params;
                }
            };

            VolleyConnection.getInstance(Input_data.this).addToReuestQue(stringRequest);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.cancel();
                }
            }, 2000);
        } else {
            Toast.makeText(getApplicationContext(), "Tidak Ada Koneksi Internet ", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}

