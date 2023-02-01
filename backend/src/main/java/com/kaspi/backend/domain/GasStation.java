package com.kaspi.backend.domain;

public class GasStation {
    private String gasStationNo;
    private String area;
    private String name;
    private String address;
    private String brand;
    private boolean self;

    public GasStation(String gasStationNo, String area, String name, String address, String brand, boolean self) {
        this.gasStationNo = gasStationNo;
        this.area = area;
        this.name = name;
        this.address = address;
        this.brand = brand;
        this.self = self;
    }

    public String getGasStationNo() {
        return gasStationNo;
    }

    public void setGasStationNo(String gasStationNo) {
        this.gasStationNo = gasStationNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
