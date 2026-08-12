package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.PrescriptionRequest;
import org.example.dto.PrescriptionResponse;
import org.example.model.Prescription.PrescriptionStatus;
import org.example.service.PrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<PrescriptionResponse> createPrescription(@Valid @RequestBody PrescriptionRequest request) {
        PrescriptionResponse response = prescriptionService.createPrescription(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<PrescriptionResponse> getPrescriptionById(@PathVariable Long prescriptionId) {
        PrescriptionResponse response = prescriptionService.getPrescriptionById(prescriptionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/iin/{iin}")
    public ResponseEntity<PrescriptionResponse> getPrescriptionByIin(@PathVariable String iin) {
        PrescriptionResponse response = prescriptionService.getPrescriptionByIin(iin);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{prescriptionId}")
    public ResponseEntity<PrescriptionResponse> updatePrescription(
            @PathVariable Long prescriptionId,
            @Valid @RequestBody PrescriptionRequest request) {
        PrescriptionResponse response = prescriptionService.updatePrescription(prescriptionId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{prescriptionId}/status")
    public ResponseEntity<PrescriptionResponse> updatePrescriptionStatus(
            @PathVariable Long prescriptionId,
            @RequestBody PrescriptionStatus status) {
        PrescriptionResponse response = prescriptionService.updatePrescriptionStatus(prescriptionId, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long prescriptionId) {
        prescriptionService.deletePrescription(prescriptionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionResponse>> getAllPrescriptions() {
        List<PrescriptionResponse> responses = prescriptionService.getAllPrescriptions();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByMemberId(@PathVariable String memberId) {
        List<PrescriptionResponse> responses = prescriptionService.getPrescriptionsByMemberId(memberId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/prescriber/{prescriberId}")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByPrescriberId(@PathVariable String prescriberId) {
        List<PrescriptionResponse> responses = prescriptionService.getPrescriptionsByPrescriberId(prescriberId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PrescriptionResponse>> searchPrescriptions(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) PrescriptionStatus status) {
        List<PrescriptionResponse> responses = prescriptionService.searchPrescriptions(keyword, status);
        return ResponseEntity.ok(responses);
    }
}
