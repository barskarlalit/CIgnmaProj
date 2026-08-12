package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.Prescription.PharmacyType;

@Data
@NoArgsConstructor
public class PrescriptionRequest {

    @NotBlank(message = "IIN is required")
    @Pattern(regexp = "^\\d{9}$", message = "IIN must be exactly 9 digits")
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

    @NotNull(message = "Pharmacy type is required")
    private PharmacyType pharmacyType;

    public PrescriptionRequest(String iin, String drugName, String drugCode, Integer quantity,
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
}
