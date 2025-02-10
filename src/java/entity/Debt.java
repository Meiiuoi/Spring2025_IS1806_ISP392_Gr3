/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author bsd12418
 */
public class Debt {

    private int id;
    private String type;
    private BigDecimal amount;
    private int customer_id;
    private String image;
    private String description;
    private String customerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String status;

    public Debt() {
    }

    public Debt(int id, String type, BigDecimal amount, String image, String description, String customerName, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String status) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.image = image;
        this.description = description;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.status = status;
    }

    public Debt(String customerName, String type, BigDecimal amount, String image, String description, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String status) {
        this.type = type;
        this.amount = amount;
        this.image = image;
        this.description = description;
        this.customerName = customerName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Debt{" + "id=" + id + ", type=" + type + ", amount=" + amount + ", customer_id=" + customer_id + ", image=" + image + ", description=" + description + ", customerName=" + customerName + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", createdBy=" + createdBy + ", status=" + status + '}';
    }

}
