package com.example.quanlymonan.Model;

public class Foods {
    private int id;
    private String nameFood;
    private int priceFood;
    private String typeFood;

    public void setId(int id) {
        this.id = id;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public Foods(int id, String nameFood, int priceFood, String typeFood) {
        this.id = id;
        this.nameFood = nameFood;
        this.priceFood = priceFood;
        this.typeFood = typeFood;
    }

    public void setPriceFood(int priceFood) {
        this.priceFood = priceFood;
    }

    public void setTypeFood(String typeFood) {
        this.typeFood = typeFood;
    }

    public int getId() {
        return id;
    }

    public String getNameFood() {
        return nameFood;
    }

    public int getPriceFood() {
        return priceFood;
    }

    public String getTypeFood() {
        return typeFood;
    }

    public Foods(String nameFood, int priceFood, String typeFood) {
        this.nameFood = nameFood;
        this.priceFood = priceFood;
        this.typeFood = typeFood;
    }

    public Foods() {

    }

}
