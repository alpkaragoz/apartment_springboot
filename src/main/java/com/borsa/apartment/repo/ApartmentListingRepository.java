package com.borsa.apartment.repo;

import com.borsa.apartment.model.ApartmentListing;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentListingRepository extends JpaRepository<ApartmentListing, Long> {
}