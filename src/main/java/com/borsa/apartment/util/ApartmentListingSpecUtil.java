package com.borsa.apartment.util;

import com.borsa.apartment.model.ApartmentListing;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class ApartmentListingSpecUtil {

    public static Specification<ApartmentListing> getApartmentListingSpecification(
            ApartmentListing.RentSaleEnum rentSale,
            double minPrice,
            double maxPrice,
            String address,
            String listingName
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (rentSale != null) {
                predicates.add(cb.equal(root.get("rentSale"), rentSale));
            }
            predicates.add(cb.between(root.get("price"), minPrice, maxPrice));

            if (address != null && !address.isEmpty()) {
                predicates.add(cb.like(root.get("address"), "%" + address + "%"));
            }

            if (listingName != null && !listingName.isEmpty()) {
                predicates.add(cb.like(root.get("listingName"), "%" + listingName + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

