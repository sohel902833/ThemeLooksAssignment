package com.sohel.themelookassignment.model;

public class ProductPrice {
    Double blPrice,bsPrice,wlPrice,wsPrice;

    public ProductPrice(){}
    public ProductPrice(Double blPrice, Double bsPrice, Double wlPrice, Double wsPrice) {
        this.blPrice = blPrice;
        this.bsPrice = bsPrice;
        this.wlPrice = wlPrice;
        this.wsPrice = wsPrice;
    }
    public Double getBlPrice() {
        return blPrice;
    }

    public void setBlPrice(Double blPrice) {
        this.blPrice = blPrice;
    }

    public Double getBsPrice() {
        return bsPrice;
    }

    public void setBsPrice(Double bsPrice) {
        this.bsPrice = bsPrice;
    }

    public Double getWlPrice() {
        return wlPrice;
    }

    public void setWlPrice(Double wlPrice) {
        this.wlPrice = wlPrice;
    }

    public Double getWsPrice() {
        return wsPrice;
    }

    public void setWsPrice(Double wsPrice) {
        this.wsPrice = wsPrice;
    }
}
