package com.Abhay.Loveseat.Model;

import com.Abhay.Loveseat.Enums.ProductsStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Products products;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id",referencedColumnName ="order_id" )
    private Orders orders;
    @Enumerated(EnumType.STRING)
    @Column(name = "products_status", nullable = false)
    @ColumnDefault("PENDING")

    private ProductsStatus productsStatus;

    private int quantity;
    private float totalPrice;
    private boolean isCancelled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public ProductsStatus getProductsStatus() {
        return productsStatus;
    }

    public void setProductsStatus(ProductsStatus productsStatus) {
        this.productsStatus = productsStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", products=" + products +
                ", orders=" + orders +
                ", productsStatus=" + productsStatus +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", isCancelled=" + isCancelled +
                '}';
    }
}
