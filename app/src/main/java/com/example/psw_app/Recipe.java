package com.example.psw_app;

import java.util.ArrayList;

class Recipe {
    private String name;
    private String description;
    private String type;
    private ArrayList<Component> components;
    private ArrayList <String> steps;
    private String owner;
    int have = 0;
    int dontHave = 0;

    Recipe() {
        name = "";
        description = "";
        type = "";
        components = new ArrayList<>();
        steps = new ArrayList<>();
    }

    Recipe(String name, String description, String type, ArrayList<Component> components, ArrayList<String> steps) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.components = components;
        this.steps = steps;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    String getType() {
        return type;
    }

    ArrayList<Component> getComponents() {
        return components;
    }

    ArrayList<String> getSteps() {
        return steps;
    }

    void setName(String name) {
        this.name = name;
    }

    void setType(String type) {
        this.type = type;
    }

    void setDescription(String description) {
        this.description = description;
    }

    void clearListAndMap() {
        components = new ArrayList<>();
        steps = new ArrayList<>();
    }

    void appendNewStep(String step) {
        steps.add(step);
    }

    void appendNewComponent(String name, String unit, double amount) {
        components.add(new Component(name, unit, amount));
    }

    String getOwner() {
        return owner;
    }

    void setOwner(String owner) {
        this.owner = owner;
    }

}
