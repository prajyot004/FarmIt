package com.example.farmit;

public class demo {
    public String date,img,name;

    demo(){

    }

    public demo(String date, String img, String name) {
        this.date = date;
        this.img = img;
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
