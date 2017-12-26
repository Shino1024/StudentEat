package net.linuxutopia.studenteat.models;

public class IngredientModel {

    private String name;
    private MeasureType measureType;
    private String amount;
    private double price;

    public IngredientModel(String name, MeasureType measureType, String amount, double price) {
        this.name = name;
        this.measureType = measureType;
        this.amount = amount;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MeasureType getMeasureType() {
        return measureType;
    }

    public void setMeasureType(MeasureType measureType) {
        this.measureType = measureType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
