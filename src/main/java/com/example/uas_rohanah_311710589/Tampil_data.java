package com.example.uas_rohanah_311710589;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tampil_data extends AppCompatActivity {

    ArrayList<DataObjek>list;
    ListView listView;
    Toolbar toolbar;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampil_data);
        toolbar=findViewById(R.id.toolbar);
        listView=findViewById(R.id.listview);
        tampil_data();
    }



    void tampil_data()
    {
        list=new ArrayList<>();
        String url="http://192.168.1.4/barang/tampil_data.php";
        StringRequest request=new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("DataBarang");

                            for (int i=0; i<jsonArray.length(); i++)
                            {
                                JSONObject getData=jsonArray.getJSONObject(i);
                                String id_barang=getData.getString("id_barang");
                                String kode_barang=getData.getString("kode_barang");
                                String nama_barang=getData.getString("nama_barang");
                                String size=getData.getString("size");
                                String jumlah=getData.getString("jumlah");
                                list.add(new DataObjek(id_barang, kode_barang,nama_barang,size,jumlah));

                            }
                            Adapter adapter=new Adapter(Tampil_data.this, list);
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
        );
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}

class Adapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<DataObjek> model;
    public Adapter(Context context, ArrayList<DataObjek>model)
    {
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.model=model;

    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TextView id_barang, kode_barang, nama_barang, size, jumlah;
    Button lihat, edit, hapus;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(R.layout.list_data, parent, false);

        lihat=view.findViewById(R.id.lihat);
        edit=view.findViewById(R.id.edit);
        hapus=view.findViewById(R.id.hapus);

        id_barang=view.findViewById(R.id.id_barang);
        kode_barang=view.findViewById(R.id.kode);
        nama_barang=view.findViewById(R.id.nama);
        size=view.findViewById(R.id.sizel);
        jumlah=view.findViewById(R.id.jumlahl);

        id_barang.setText(model.get(position).getId_barang());
        kode_barang.setText(model.get(position).getKode_barang());
        nama_barang.setText(model.get(position).getNama_barang());
        size.setText(model.get(position).getSize());
        jumlah.setText(model.get(position).getJumlah());

        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Lihat_data.class);

                intent.putExtra("id_barang", model.get(position).getId_barang());
                intent.putExtra("kode_barang", model.get(position).getKode_barang());
                intent.putExtra("nama_barang", model.get(position).getNama_barang());
                intent.putExtra("size", model.get(position).getSize());
                intent.putExtra("jumlah", model.get(position).getJumlah());
                context.startActivity(intent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentx=new Intent(context, Update_data.class);
                intentx.putExtra("id_barang", model.get(position).getId_barang());
                intentx.putExtra("kode_barang", model.get(position).getKode_barang());
                intentx.putExtra("nama_barang", model.get(position).getNama_barang());
                intentx.putExtra("size", model.get(position).getSize());
                intentx.putExtra("jumlah", model.get(position).getJumlah());
                context.startActivity(intentx) ;
            }
        });
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus_data(model.get(position).getId_barang());
            }
        });
        return view;
    }

    void hapus_data(String id_barang)
    {
        String url = "http://192.168.1.4/barang/hapus_data.php?id_barang="+id_barang;
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            String status=jsonObject.getString("status");
                            if (status.equals("data_terhapus"))
                            {
                                Toast.makeText(context, "Data Berhasil Di Hapus", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context, Tampil_data.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}