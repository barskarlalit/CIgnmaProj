package org.example.repository;

import org.example.model.Prescription;
import org.example.model.Prescription.PrescriptionStatus;
import org.example.model.Prescription.PharmacyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Optional<Prescription> findByIin(String iin);

    List<Prescription> findByStatus(PrescriptionStatus status);

    List<Prescription> findByPharmacyType(PharmacyType pharmacyType);

    List<Prescription> findByMemberId(String memberId);

    List<Prescription> findByPrescriberId(String prescriberId);

    @Query("SELECT p FROM Prescription p WHERE " +
           "LOWER(p.drugName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.drugCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.memberName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.prescriberName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Prescription> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT p FROM Prescription p WHERE p.status = :status AND " +
           "(LOWER(p.drugName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.drugCode) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.memberName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.prescriberName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Prescription> searchByStatusAndKeyword(@Param("status") PrescriptionStatus status, @Param("keyword") String keyword);

    @Query("SELECT p FROM Prescription p WHERE p.pharmacyType = :pharmacyType AND p.status = :status")
    List<Prescription> findByPharmacyTypeAndStatus(@Param("pharmacyType") PharmacyType pharmacyType, @Param("status") PrescriptionStatus status);
}
