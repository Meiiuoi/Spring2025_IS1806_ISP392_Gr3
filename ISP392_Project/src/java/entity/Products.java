package entity;

import entity.*;
import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Products {
    private int id;
    private String name;
    private String image;
    private double price;
    private int quantity;
    private int zone_id;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    private boolean isDeleted;
    private Date deletedAt;
    private String status;
    private List<InvoiceDetail> invoiceDetails;

}
