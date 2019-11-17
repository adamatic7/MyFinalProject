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
    public String image_url;
    public int image_width;
    public int image_height;

    @Override
    public String toString(){
        return "Product{" +
                ", id='" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", image_url='" + image_url + '\'' +
                ", image_width='" + image_width + '\'' +
                ", image_height='" + image_height + '\'' +
                '}';
    }

    public Product(int id, String title, String description, Double price, String image_url, int image_width, int image_height) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
        this.image_width = image_width;
        this.image_height = image_height;
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
        return image_url;
    }

    public int getImage_width() {
        return image_width;
    }

    public int getImage_height() {
        return image_height;
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

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setImage_width(int image_width) {
        this.image_width = image_width;
    }

    public void setImage_height(int image_height) {
        this.image_height = image_height;
    }
}
