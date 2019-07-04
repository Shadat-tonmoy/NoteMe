package com.stcodesapp.noteit.models;

public class ProductDetail {

    private String productId, productPrice;

    public ProductDetail(String productId, String productPrice) {
        this.productId = productId;
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "productId='" + productId + '\'' +
                ", productPrice='" + productPrice + '\'' +
                '}';
    }
}
