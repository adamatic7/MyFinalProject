package com.example.myfinalproject.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey
    public int id;
    public String title;
    public String description;
    public Double price;
    public String imageURL;
    public int imageWidth;
    public int imageHeight;

    @Override
    public String toString(){
        return "Product{" +
                ", id='" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", imageWidth='" + imageWidth + '\'' +
                ", imageHeight='" + imageHeight + '\'' +
                '}';
    }

    public Product(int id, String title, String description, Double price, String imageURL, int imageWidth, int imageHeight) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImage_url() {
        return imageURL;
    }

    public int getImage_width() {
        return imageWidth;
    }

    public int getImage_height() {
        return imageHeight;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setImage_url(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setImage_width(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setImage_height(int imageHeight) {
        this.imageHeight = imageHeight;
    }
}
