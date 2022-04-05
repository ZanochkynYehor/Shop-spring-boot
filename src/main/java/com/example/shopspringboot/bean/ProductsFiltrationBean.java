package com.example.shopspringboot.bean;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

public class ProductsFiltrationBean {
    private List<Integer> manufacturerId;
    private List<Integer> categoryId;

    @Pattern(regexp = "\\d+-\\d+")
    private String price;
    private String name;

    public ProductsFiltrationBean() {
        manufacturerId = new ArrayList<>();
        categoryId = new ArrayList<>();
    }

    public List<Integer> getManufacturerIds() {
        return manufacturerId;
    }

    public void setManufacturerId(List<Integer> manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public List<Integer> getCategoryIds() {
        return categoryId;
    }

    public void setCategoryId(List<Integer> categoryId) {
        this.categoryId = categoryId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductsFiltrationBean{" +
                "manufacturerIDs=" + manufacturerId +
                ", categoryIDs=" + categoryId +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}