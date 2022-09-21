package com.example.farmit;

public class dataholderjob {
    String contact,date,img,name,title,zdescription;

    public dataholderjob(String contact, String date, String img, String name, String title, String zdescription) {
        this.contact = contact;
        this.date = date;
        this.img = img;
        this.name = name;
        this.title = title;
        this.zdescription = zdescription;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getZdescription() {
        return zdescription;
    }

    public void setZdescription(String zdescription) {
        this.zdescription = zdescription;
    }
}
