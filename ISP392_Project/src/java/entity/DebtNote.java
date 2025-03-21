package entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class DebtNote {

    private int id;
    private String type;
    private BigDecimal amount;
    private String image;
    private String description;
    private int customer_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String status;

    public DebtNote(int id, String type, BigDecimal amount, String image, String description, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String status) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.image = image;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.status = status;
    }

    

}
