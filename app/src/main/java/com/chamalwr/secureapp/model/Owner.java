package com.chamalwr.secureapp.model;

import androidx.annotation.NonNull;

public class Owner {
    private String _id;
    private String name;
    private String phone;
    private String title;
    private String car;

    public Owner() {
    }

    public Owner(String _id, String name, String phone, String title, String car) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.title = title;
        this.car = car;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    @NonNull
    @Override
    public String toString() {
        return  "Name : " + title + " " + name + "\n" +
                "Phone: " + phone + "\n" +
                "Car  : " + car;
    }

}
