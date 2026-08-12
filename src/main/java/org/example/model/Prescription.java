package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions")
@Data
@NoArgsConstructor
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "IIN is required")
    @Pattern(regexp = "^\\d{9}$", message = "IIN must be exactly 9 digits")
    @Column(unique = true)
    private String iin;

    @NotBlank(message = "Drug name is required")
    private String drugName;

    @NotBlank(message = "Drug code is required")
    private String drugCode;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotBlank(message = "Dosage is required")
    private String dosage;

    @NotBlank(message = "Prescriber name is required")
    private String prescriberName;

    @NotBlank(message = "Prescriber ID is required")
    private String prescriberId;

    @NotBlank(message = "Member name is required")
    private String memberName;

    @NotBlank(message = "Member ID is required")
    private String memberId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Pharmacy type is required")
    private PharmacyType pharmacyType;

    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status = PrescriptionStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum PharmacyType {
        RETAIL, MAIL_ORDER, E_PRESCRIPTION
    }

    public enum PrescriptionStatus {
        PENDING, VALIDATED, APPROVED, REJECTED
    }

    public Prescription(String iin, String drugName, String drugCode, Integer quantity, 
                       String dosage, String prescriberName, String prescriberId,
                       String memberName, String memberId, PharmacyType pharmacyType) {
        this.iin = iin;
        this.drugName = drugName;
        this.drugCode = drugCode;
        this.quantity = quantity;
        this.dosage = dosage;
        this.prescriberName = prescriberName;
        this.prescriberId = prescriberId;
        this.memberName = memberName;
        this.memberId = memberId;
        this.pharmacyType = pharmacyType;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
