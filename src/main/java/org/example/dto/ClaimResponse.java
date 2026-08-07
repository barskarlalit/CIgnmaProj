package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Claim;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ClaimResponse {

    private Long id;
    private String title;
    private String description;
    private Double amount;
    private Claim.ClaimStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ClaimResponse(Claim claim) {
        this.id = claim.getId();
        this.title = claim.getTitle();
        this.description = claim.getDescription();
        this.amount = claim.getAmount();
        this.status = claim.getStatus();
        this.createdAt = claim.getCreatedAt();
        this.updatedAt = claim.getUpdatedAt();
    }
}
