package com.altynbekova.banks.db.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.yandex.mapkit.geometry.Point;

@Entity (tableName = "banks")
public class Bank {
    @PrimaryKey
    private int id;
    private String name;
    private String address;
    private String status;
    private float lat;
    private float lon;
    @Ignore
    private Point point;

    public Bank() {
    }

    public Bank(String name, String address, String status, Point point) {
        this.name = name;
        this.address = address;
        this.status = status;
        this.point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
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
