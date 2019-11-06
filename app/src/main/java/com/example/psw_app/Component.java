package com.example.psw_app;

public class Component {
    private String name;
    private String amount;
    private String owner;

    public Component(String name, String amount, String owner){
        this.name = name;
        this.amount = amount;
        this.owner = owner;
    }

    Component(){
    }

    public String getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}
