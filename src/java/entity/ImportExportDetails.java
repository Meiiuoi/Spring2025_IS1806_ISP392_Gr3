/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;

/**
 *
 * @author bsd12418
 */
public class ImportExportDetails {
    private int id;
    private ImportExportProduct importExportProduct;
    private Products product;
    private int quantity;
    private BigDecimal price;

    public ImportExportDetails() {
    }

    public ImportExportDetails(int id, ImportExportProduct importExportProduct, Products product, int quantity, BigDecimal price) {
        this.id = id;
        this.importExportProduct = importExportProduct;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImportExportProduct getImportExportProduct() {
        return importExportProduct;
    }

    public void setImportExportProduct(ImportExportProduct importExportProduct) {
        this.importExportProduct = importExportProduct;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
}
