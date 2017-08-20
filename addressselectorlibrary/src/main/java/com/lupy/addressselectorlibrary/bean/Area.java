package com.lupy.addressselectorlibrary.bean;

/**
 * Created by Lupy
 * on 2017/8/19.
 */

public class Area {
    public String id;
    public String name;

    public Area(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
