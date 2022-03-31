package com.example.wheregottimefind.pojo;

import java.util.Objects;

public class Vendor {
    private String name;
    private String location;
    private int phone_no;
    private int id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vendor vendor = (Vendor) o;
        return phone_no == vendor.phone_no && id == vendor.id && name.equals(vendor.name) && Objects.equals(location, vendor.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, phone_no, id);
    }

    public Vendor(String name, String location, int phone_no, int id) {
        this.name = name;
        this.location = location;
        this.phone_no = phone_no;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(int phone_no) {
        this.phone_no = phone_no;
    }
}
