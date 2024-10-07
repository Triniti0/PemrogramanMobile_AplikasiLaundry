package com.example.aplikasilaundry.model;

public class ModelLayanan {
    private String id; // ID layanan
    private String name; // Nama layanan
    private int price; // Mengubah harga dari double ke int

    public ModelLayanan() {}

    public ModelLayanan(String id, String name, int price) { // Constructor yang diperbarui
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price; // Mengembalikan harga sebagai integer
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
