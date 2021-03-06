package com.example.wheregottimefind.data.pojo;

public class Product {
    String name;
    int id;

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
