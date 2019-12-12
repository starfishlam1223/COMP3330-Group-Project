package com.example.yellowobjects.ui.ybmap;

public class Shop {
    private int id;
    private String name;
    private boolean isYellow;
    private int imageId;

    public Shop(int id, String name, boolean isYellow, int imageId) {
        this.id = id;
        this.name = name;
        this.isYellow = isYellow;
        this.imageId = imageId;
    }

    public int getId() {
        return id;
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
