package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.ClaimRequest;
import org.example.dto.ClaimResponse;
import org.example.model.Claim.ClaimStatus;
import org.example.service.ClaimService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping
    public ResponseEntity<ClaimResponse> createClaim(@Valid @RequestBody ClaimRequest request) {
        ClaimResponse response = claimService.createClaim(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{claimId}")
    public ResponseEntity<ClaimResponse> getClaimById(@PathVariable Long claimId) {
        ClaimResponse response = claimService.getClaimById(claimId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{claimId}")
    public ResponseEntity<ClaimResponse> updateClaim(
            @PathVariable Long claimId,
            @Valid @RequestBody ClaimRequest request) {
        ClaimResponse response = claimService.updateClaim(claimId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{claimId}")
    public ResponseEntity<Void> deleteClaim(@PathVariable Long claimId) {
        claimService.deleteClaim(claimId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClaimResponse>> searchClaims(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) ClaimStatus status) {
        List<ClaimResponse> responses = claimService.searchClaims(keyword, status);
        return ResponseEntity.ok(responses);
    }
}
