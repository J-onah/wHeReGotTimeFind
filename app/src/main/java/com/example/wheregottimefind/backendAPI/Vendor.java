package com.example.wheregottimefind.backendAPI;

public class Vendor {
    private String name;
    private String location;
    private int phone_no;

    public Vendor(String name, String location, int phone_no) {
        this.name = name;
        this.location = location;
        this.phone_no = phone_no;
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
        return "Vendor{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", phone_no=" + phone_no +
                '}';
    }

    public int getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(int phone_no) {
        this.phone_no = phone_no;
    }
}
