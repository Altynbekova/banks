package com.altynbekova.banks;

import com.yandex.mapkit.geometry.Point;

public class Bank {
    private String name;
    private String address;
    private String status;

    private Point point;

    public Bank(String name, String address, String status, Point point) {
        this.name = name;
        this.address = address;
        this.status = status;
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", point=" + point +
                '}';
    }
}
