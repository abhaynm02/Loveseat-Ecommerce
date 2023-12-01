package com.Abhay.Loveseat.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "product",uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private  String description;
    private  float price;
    private  float sellingPrice;
    private long stock;
    private long quantity;
    private  boolean isAvailable;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    private Category category;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private  String frontImage;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private  String sideImage;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String topImage;

    public Products() {
    }

    public Products(Long id, String name, String description, float price, float sellingPrice,
                    long stock, long quantity, boolean isAvailable,
                    Category category, String frontImage, String sideImage, String topImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.sellingPrice = sellingPrice;
        this.stock = stock;
        this.quantity = quantity;
        this.isAvailable = isAvailable;
        this.category = category;
        this.frontImage = frontImage;
        this.sideImage = sideImage;
        this.topImage = topImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getSideImage() {
        return sideImage;
    }

    public void setSideImage(String sideImage) {
        this.sideImage = sideImage;
    }

    public String getTopImage() {
        return topImage;
    }

    public void setTopImage(String topImage) {
        this.topImage = topImage;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", sellingPrice=" + sellingPrice +
                ", stock=" + stock +
                ", quantity=" + quantity +
                ", isAvailable=" + isAvailable +
                ", category=" + category +
                ", frontImage='" + frontImage + '\'' +
                ", sideImage='" + sideImage + '\'' +
                ", topImage='" + topImage + '\'' +
                '}';
    }
}
