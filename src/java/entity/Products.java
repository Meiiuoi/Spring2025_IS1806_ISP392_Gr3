package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Products {
    private int productId;
    private String name;
    private String image;
    private BigDecimal price;
    private BigDecimal wholesalePrice;
    private BigDecimal retailPrice;
    private int weight;
    private String location;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private boolean isDelete;
    private Date deletedAt;
    private String status;
    private List<ProductZone> productZones;
    private List<InvoiceDetails> invoiceDetails;
    private List<ImportExportDetails> importExportDetails;
    // Constructors

    public Products() {
    }

    public Products(int productId, String name, String image, BigDecimal price, BigDecimal wholesalePrice, BigDecimal retailPrice, int weight, String location, String description, Date createdAt, Date updatedAt, boolean isDelete, Date deletedAt, String status) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.wholesalePrice = wholesalePrice;
        this.retailPrice = retailPrice;
        this.weight = weight;
        this.location = location;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDelete = isDelete;
        this.deletedAt = deletedAt;
        this.status = status;
    }

    public Products(int productId, String name, String image, BigDecimal price, BigDecimal wholesalePrice, BigDecimal retailPrice, int weight, String location, String description, Date createdAt, Date updatedAt, boolean isDelete, Date deletedAt, String status, List<ProductZone> productZones, List<InvoiceDetails> invoiceDetails, List<ImportExportDetails> importExportDetails) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.wholesalePrice = wholesalePrice;
        this.retailPrice = retailPrice;
        this.weight = weight;
        this.location = location;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDelete = isDelete;
        this.deletedAt = deletedAt;
        this.status = status;
        this.productZones = productZones;
        this.invoiceDetails = invoiceDetails;
        this.importExportDetails = importExportDetails;
    }




    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(BigDecimal wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public BigDecimal getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
        this.retailPrice = retailPrice;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
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
        public List<ProductZone> getProductZones() {
        return productZones;
    }

    public void setProductZones(List<ProductZone> productZones) {
        this.productZones = productZones;
    }

    public List<InvoiceDetails> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetails> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    public List<ImportExportDetails> getImportExportDetails() {
        return importExportDetails;
    }

    public void setImportExportDetails(List<ImportExportDetails> importExportDetails) {
        this.importExportDetails = importExportDetails;
    }


}
