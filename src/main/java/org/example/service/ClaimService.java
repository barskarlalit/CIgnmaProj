package org.example.service;

import org.example.dto.ClaimRequest;
import org.example.dto.ClaimResponse;
import org.example.model.Claim;
import org.example.model.Claim.ClaimStatus;
import org.example.repository.ClaimRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public ClaimResponse createClaim(ClaimRequest request) {
        Claim claim = new Claim(request.getTitle(), request.getDescription(), request.getAmount());
        Claim savedClaim = claimRepository.save(claim);
        return new ClaimResponse(savedClaim);
    }

    public ClaimResponse getClaimById(Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found with id: " + id));
        return new ClaimResponse(claim);
    }

    public ClaimResponse updateClaim(Long id, ClaimRequest request) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found with id: " + id));
        
        claim.setTitle(request.getTitle());
        claim.setDescription(request.getDescription());
        claim.setAmount(request.getAmount());
        
        Claim updatedClaim = claimRepository.save(claim);
        return new ClaimResponse(updatedClaim);
    }

    public ClaimResponse updateClaimStatus(Long id, ClaimStatus status) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found with id: " + id));
        
        claim.setStatus(status);
        Claim updatedClaim = claimRepository.save(claim);
        return new ClaimResponse(updatedClaim);
    }

    public void deleteClaim(Long id) {
        if (!claimRepository.existsById(id)) {
            throw new RuntimeException("Claim not found with id: " + id);
        }
        claimRepository.deleteById(id);
    }

    public List<ClaimResponse> getAllClaims() {
        return claimRepository.findAll().stream()
                .map(ClaimResponse::new)
                .collect(Collectors.toList());
    }

    public List<ClaimResponse> searchClaims(String keyword, ClaimStatus status) {
        List<Claim> claims;
        
        if (status != null && keyword != null && !keyword.isEmpty()) {
            claims = claimRepository.searchByStatusAndKeyword(status, keyword);
        } else if (status != null) {
            claims = claimRepository.findByStatus(status);
        } else if (keyword != null && !keyword.isEmpty()) {
            claims = claimRepository.searchByKeyword(keyword);
        } else {
            claims = claimRepository.findAll();
        }
        
        return claims.stream()
                .map(ClaimResponse::new)
                .collect(Collectors.toList());
    }
}
