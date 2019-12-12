package com.example.yellowobjects.ui.ybmap;

public class Shop {
    private String name;
    private boolean isYellow;
    private int imageId;

    public Shop(String name, boolean isYellow, int imageId) {
        this.name = name;
        this.isYellow = isYellow;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public boolean isYellow() {
        return isYellow;
    }

    public int getImageId() {
        return imageId;
    }
}
