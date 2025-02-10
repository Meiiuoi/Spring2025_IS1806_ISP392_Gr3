/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author bsd12418
 */
public class Customers {
    private int id;
    private String type;
    private String name;
    private String phone;
    private String address;
    private BigDecimal balance;
    private Date createdAt;
    private Date updatedAt;
    private String updatedBy;
    private String createdBy;
    private boolean isDelete;
    private String deletedBy;
    private Date deletedAt;
    private String status;
    private List<Invoice> invoices;
    private List<Debt> debts;

    public Customers() {
    }

    public Customers(int id, String type, String name, String phone, String address, Date createdAt, Date updatedAt, String updatedBy, String createdBy, boolean isDelete, String deletedBy, Date deletedAt, String status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.createdBy = createdBy;
        this.isDelete = isDelete;
        this.deletedBy = deletedBy;
        this.deletedAt = deletedAt;
        this.status = status;
    }

    public Customers(int id, String type, String name, String phone, String address, BigDecimal balance, Date createdAt, Date updatedAt, String updatedBy, String createdBy, boolean isDelete, String deletedBy, Date deletedAt, String status, List<Invoice> invoices, List<Debt> debts) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.createdBy = createdBy;
        this.isDelete = isDelete;
        this.deletedBy = deletedBy;
        this.deletedAt = deletedAt;
        this.status = status;
        this.invoices = invoices;
        this.debts = debts;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Debt> getDebts() {
        return debts;
    }

    public void setDebts(List<Debt> debts) {
        this.debts = debts;
    }

    @Override
    public String toString() {
        return "Customers{" + "id=" + id + ", type=" + type + ", name=" + name + ", phone=" + phone + ", address=" + address + ", balance=" + balance + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + ", createdBy=" + createdBy + ", isDelete=" + isDelete + ", deletedBy=" + deletedBy + ", deletedAt=" + deletedAt + ", status=" + status + ", invoices=" + invoices + ", debts=" + debts + '}';
    }


    
    
}
