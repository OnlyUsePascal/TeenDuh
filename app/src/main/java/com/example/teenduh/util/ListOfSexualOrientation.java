package com.example.teenduh.util;

public class ListOfSexualOrientation {
    private String name;
    private boolean isSelected;

    public ListOfSexualOrientation(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public ListOfSexualOrientation() {
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
