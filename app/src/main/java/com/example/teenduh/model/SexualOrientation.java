package com.example.teenduh.model;

public class SexualOrientation {
    private String name;
    private boolean isSelected;

    public SexualOrientation(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public SexualOrientation() {
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
