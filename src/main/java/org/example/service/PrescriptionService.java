package org.example.service;

import org.example.dto.PrescriptionRequest;
import org.example.dto.PrescriptionResponse;
import org.example.model.Prescription;
import org.example.model.Prescription.PrescriptionStatus;
import org.example.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    public PrescriptionResponse createPrescription(PrescriptionRequest request) {
        validatePrescriptionRequest(request);
        
        checkDuplicateIin(request.getIin());
        
        Prescription prescription = new Prescription(
            request.getIin(),
            request.getDrugName(),
            request.getDrugCode(),
            request.getQuantity(),
            request.getDosage(),
            request.getPrescriberName(),
            request.getPrescriberId(),
            request.getMemberName(),
            request.getMemberId(),
            request.getPharmacyType()
        );
        
        prescription.setStatus(PrescriptionStatus.VALIDATED);
        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return new PrescriptionResponse(savedPrescription);
    }

    public PrescriptionResponse getPrescriptionById(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        return new PrescriptionResponse(prescription);
    }

    public PrescriptionResponse getPrescriptionByIin(String iin) {
        Prescription prescription = prescriptionRepository.findByIin(iin)
                .orElseThrow(() -> new RuntimeException("Prescription not found with IIN: " + iin));
        return new PrescriptionResponse(prescription);
    }

    public PrescriptionResponse updatePrescription(Long id, PrescriptionRequest request) {
        validatePrescriptionRequest(request);
        
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        
        if (!prescription.getIin().equals(request.getIin())) {
            checkDuplicateIin(request.getIin());
        }
        
        prescription.setIin(request.getIin());
        prescription.setDrugName(request.getDrugName());
        prescription.setDrugCode(request.getDrugCode());
        prescription.setQuantity(request.getQuantity());
        prescription.setDosage(request.getDosage());
        prescription.setPrescriberName(request.getPrescriberName());
        prescription.setPrescriberId(request.getPrescriberId());
        prescription.setMemberName(request.getMemberName());
        prescription.setMemberId(request.getMemberId());
        prescription.setPharmacyType(request.getPharmacyType());
        
        Prescription updatedPrescription = prescriptionRepository.save(prescription);
        return new PrescriptionResponse(updatedPrescription);
    }

    public PrescriptionResponse updatePrescriptionStatus(Long id, PrescriptionStatus status) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found with id: " + id));
        
        prescription.setStatus(status);
        Prescription updatedPrescription = prescriptionRepository.save(prescription);
        return new PrescriptionResponse(updatedPrescription);
    }

    public void deletePrescription(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new RuntimeException("Prescription not found with id: " + id);
        }
        prescriptionRepository.deleteById(id);
    }

    public List<PrescriptionResponse> getAllPrescriptions() {
        return prescriptionRepository.findAll().stream()
                .map(PrescriptionResponse::new)
                .collect(Collectors.toList());
    }

    public List<PrescriptionResponse> getPrescriptionsByMemberId(String memberId) {
        return prescriptionRepository.findByMemberId(memberId).stream()
                .map(PrescriptionResponse::new)
                .collect(Collectors.toList());
    }

    public List<PrescriptionResponse> getPrescriptionsByPrescriberId(String prescriberId) {
        return prescriptionRepository.findByPrescriberId(prescriberId).stream()
                .map(PrescriptionResponse::new)
                .collect(Collectors.toList());
    }

    public List<PrescriptionResponse> searchPrescriptions(String keyword, PrescriptionStatus status) {
        List<Prescription> prescriptions;
        
        if (status != null && keyword != null && !keyword.isEmpty()) {
            prescriptions = prescriptionRepository.searchByStatusAndKeyword(status, keyword);
        } else if (status != null) {
            prescriptions = prescriptionRepository.findByStatus(status);
        } else if (keyword != null && !keyword.isEmpty()) {
            prescriptions = prescriptionRepository.searchByKeyword(keyword);
        } else {
            prescriptions = prescriptionRepository.findAll();
        }
        
        return prescriptions.stream()
                .map(PrescriptionResponse::new)
                .collect(Collectors.toList());
    }

    private void validatePrescriptionRequest(PrescriptionRequest request) {
        if (request.getIin() == null || !request.getIin().matches("^\\d{9}$")) {
            throw new IllegalArgumentException("IIN must be exactly 9 digits");
        }
        
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        
        if (request.getDosage() == null || request.getDosage().trim().isEmpty()) {
            throw new IllegalArgumentException("Dosage is required");
        }
        
        if (request.getPrescriberName() == null || request.getPrescriberName().trim().isEmpty()) {
            throw new IllegalArgumentException("Prescriber name is required");
        }
        
        if (request.getPrescriberId() == null || request.getPrescriberId().trim().isEmpty()) {
            throw new IllegalArgumentException("Prescriber ID is required");
        }
        
        if (request.getMemberName() == null || request.getMemberName().trim().isEmpty()) {
            throw new IllegalArgumentException("Member name is required");
        }
        
        if (request.getMemberId() == null || request.getMemberId().trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID is required");
        }
    }

    private void checkDuplicateIin(String iin) {
        prescriptionRepository.findByIin(iin).ifPresent(p -> {
            throw new IllegalArgumentException("Prescription with IIN " + iin + " already exists");
        });
    }
}
