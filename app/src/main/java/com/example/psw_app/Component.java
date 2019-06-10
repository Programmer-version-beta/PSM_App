package com.example.psw_app;

class Component {
    private String name;
    private String unit;
    private double amount;

    Component(String name, String unit, double amount){
        this.name = name;
        this.unit = unit;
        this.amount = amount;
    }

    Component(){
        name = "";
        unit = "";
        amount = 0;
    }

    String getUnit() {
        return unit;
    }

    double getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }
}
