package org.issoft.test;

public class Price {
    private int price;

    public Price(int price) {
        this.price = price;
    }

    public void addPrice(int price) {
        this.price += price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
