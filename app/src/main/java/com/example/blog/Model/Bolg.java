package com.example.blog.Model;

public class Bolg {
    private String Title;
    private String desc;
    private String image;
    private String timestmap;


    public Bolg(String title, String description, String imageurl, String timestamp, String user) {
    }

    public Bolg(String title, String desc, String image, String timestmap) {
        Title = title;
        this.desc = desc;
        this.image = image;
        this.timestmap = timestmap;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimestmap() {
        return timestmap;
    }

    public void setTimestmap(String timestmap) {
        this.timestmap = timestmap;
    }
}
