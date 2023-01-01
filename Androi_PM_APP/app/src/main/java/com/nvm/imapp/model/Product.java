package com.nvm.imapp.model;

import java.io.Serializable;

public class Product implements Serializable {
    String etName;
    String etCode;
    String etKL;
    String etSL;
    String etDonGia;
    String etHangSX;
    String etMadeIn;
    String edTime;
    String edDate;
    String nguoinhan;
    int thanhtien;
    String sdt;
    int key;

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getSdt() {
        return sdt;
    }


    // sell activity
    public Product(String etName, String etCode, String etKL, String etSL, String etDonGia, String etHangSX, String etMadeIn, String edTime, String edDate, String nguoinhan, int thanhtien, String sdt, int key) {
        this.etName = etName;
        this.etCode = etCode;
        this.etKL = etKL;
        this.etSL = etSL;
        this.etDonGia = etDonGia;
        this.etHangSX = etHangSX;
        this.etMadeIn = etMadeIn;
        this.edTime = edTime;
        this.edDate = edDate;
        this.nguoinhan = nguoinhan;
        this.thanhtien = thanhtien;
        this.sdt = sdt;
        this.key=key;
    }


// them san pham trong Add activity
    public Product(String etName, String etCode, String etKL, String etSL, String etDonGia, String etHangSX, String etMadeIn, String edTime, String edDate, int thanhtien, String sdt, int key) {
        this.etName = etName;
        this.etCode = etCode;
        this.etKL = etKL;
        this.etSL = etSL;
        this.etDonGia = etDonGia;
        this.etHangSX = etHangSX;
        this.etMadeIn = etMadeIn;
        this.edTime = edTime;
        this.edDate = edDate;
        this.thanhtien = thanhtien;
        this.sdt = sdt;
        this.key=key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(int thanhtien) {
        this.thanhtien = thanhtien;
    }

    public Product(){

    }

    public String getEtName() {
        return etName;
    }

    public String getEtCode() {
        return etCode;
    }

    public String getEtKL() {
        return etKL;
    }
    public String getEtSL() {
        return etSL;
    }

    public String getEtDonGia() {
        return etDonGia;
    }

    public String getEtHangSX() {
        return etHangSX;
    }
    public String getEtMadeIn() {
        return etMadeIn;
    }
    public String getEdTime() {
        return edTime;
    }
    public String getEdDate() {
        return edDate;
    }
    public String getNguoinhan() {
        return nguoinhan;
    }

    public void setEtName(String etName) {
        this.etName = etName;
    }

    public void setEtCode(String etCode) {
        this.etCode = etCode;
    }

    public void setEtKL(String etKL) {
        this.etKL = etKL;
    }

    public void setEtSL(String etSL) {
        this.etSL = etSL;
    }

    public void setEtDonGia(String etDonGia) {
        this.etDonGia = etDonGia;
    }

    public void setEtHangSX(String etHangSX) {
        this.etHangSX = etHangSX;
    }

    public void setEtMadeIn(String etMadeIn) {
        this.etMadeIn = etMadeIn;
    }

    public void setEdTime(String edTime) {
        this.edTime = edTime;
    }

    public void setEdDate(String edDate) {
        this.edDate = edDate;
    }
    public void setNguoinhan(String nguoinhan) {
        this.nguoinhan = nguoinhan;
    }

    @Override
    public String toString() {
        return "Product{" +
                "etName='" + etName + '\'' +
                ", etCode='" + etCode + '\'' +
                ", etKL='" + etKL + '\'' +
                ", etSL='" + etSL + '\'' +
                ", etDonGia='" + etDonGia + '\'' +
                ", etHangSX='" + etHangSX + '\'' +
                ", etMadeIn='" + etMadeIn + '\'' +
                ", edTime='" + edTime + '\'' +
                ", edDate='" + edDate + '\'' +
                '}';
    }
}

