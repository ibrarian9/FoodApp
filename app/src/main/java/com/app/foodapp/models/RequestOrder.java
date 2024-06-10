package com.app.foodapp.models;

public class RequestOrder {
    private String nama;
    private int harga;
    private int jumlah;
    private int total_harga;

    public RequestOrder(String nama, int harga, int jumlah, int total_harga) {
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
        this.total_harga = total_harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getTotalHarga() {
        return total_harga;
    }

    public void setTotalHarga(int total_harga) {
        this.total_harga = total_harga;
    }
}
