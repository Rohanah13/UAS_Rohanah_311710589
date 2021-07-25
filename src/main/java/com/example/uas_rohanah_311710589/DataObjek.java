package com.example.uas_rohanah_311710589;

public class DataObjek {
    String id_barang="", kode_barang="", nama_barang="", size="", jumlah="";
    public DataObjek(String id_barang, String kode_barang, String nama_barang, String size, String jumlah)
    {
        this.id_barang=id_barang;
        this.kode_barang=kode_barang;
        this.nama_barang=nama_barang;
        this.size=size;
        this.jumlah=jumlah;
    }

    public String getId_barang() {
        return id_barang;
    }

    public String getKode_barang() {
        return kode_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public String getSize() {
        return size;
    }

    public String getJumlah() {
        return jumlah;
    }
}
