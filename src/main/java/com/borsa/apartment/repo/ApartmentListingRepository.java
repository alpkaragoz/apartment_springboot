package com.borsa.apartment.repo;

import com.borsa.apartment.model.ApartmentListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApartmentListingRepository extends JpaRepository<ApartmentListing, Long>, JpaSpecificationExecutor<ApartmentListing> {
    List<ApartmentListing> findByUserId(Long userId);
}
