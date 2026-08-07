package org.example.repository;

import org.example.model.Claim;
import org.example.model.Claim.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    List<Claim> findByStatus(ClaimStatus status);

    @Query("SELECT c FROM Claim c WHERE " +
           "LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Claim> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT c FROM Claim c WHERE c.status = :status AND " +
           "(LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Claim> searchByStatusAndKeyword(@Param("status") ClaimStatus status, @Param("keyword") String keyword);
}
