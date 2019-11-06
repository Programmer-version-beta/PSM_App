package com.example.psw_app;

import java.util.ArrayList;

public class Recipe {
    public String name;
    public String description;
    public ArrayList<Component> components;
    public String owner;
    public int have = 0;
    public int dontHave = 0;

    Recipe() {
        name = "";
        description = "";
        components = new ArrayList<>();
    }

    Recipe(String name, String description, ArrayList<Component> components) {
        this.name = name;
        this.description = description;
        this.components = components;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    void setName(String name) {
        this.name = name;
    }

    void setOwner(String owner) {
        this.owner = owner;
    }

    String listToString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < components.size(); i++)
            result.append(components.get(i).getName()).append(": ").append(components.get(i).getAmount()).append("\n");
        return result.toString();
    }
}
