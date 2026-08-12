package org.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Prescription;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PrescriptionResponse {

    private Long id;
    private String iin;
    private String drugName;
    private String drugCode;
    private Integer quantity;
    private String dosage;
    private String prescriberName;
    private String prescriberId;
    private String memberName;
    private String memberId;
    private Prescription.PharmacyType pharmacyType;
    private Prescription.PrescriptionStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PrescriptionResponse(Prescription prescription) {
        this.id = prescription.getId();
        this.iin = prescription.getIin();
        this.drugName = prescription.getDrugName();
        this.drugCode = prescription.getDrugCode();
        this.quantity = prescription.getQuantity();
        this.dosage = prescription.getDosage();
        this.prescriberName = prescription.getPrescriberName();
        this.prescriberId = prescription.getPrescriberId();
        this.memberName = prescription.getMemberName();
        this.memberId = prescription.getMemberId();
        this.pharmacyType = prescription.getPharmacyType();
        this.status = prescription.getStatus();
        this.createdAt = prescription.getCreatedAt();
        this.updatedAt = prescription.getUpdatedAt();
    }
}
