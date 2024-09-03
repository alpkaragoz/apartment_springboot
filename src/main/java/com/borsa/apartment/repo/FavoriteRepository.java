package com.borsa.apartment.repo;

import com.borsa.apartment.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByUserId(Long userId);
    long countByListingId(Long listingId);
    boolean existsByUserIdAndListingId(Long userId, Long listingId);
    Optional<Favorite> findByUserIdAndListingId(Long userId, Long listingId);
}
