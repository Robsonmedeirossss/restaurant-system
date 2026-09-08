package com.dev.restaurant.specifications;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.dev.restaurant.dtos.requests.filters.ProductOrderFilter;
import com.dev.restaurant.entities.ProductOrder;
import com.dev.restaurant.enums.StatusProductOrder;

public class ProductOrderSpecification {
    
    public static Specification<ProductOrder> withFilters(ProductOrderFilter requestFilters) {
        return statusEquals(requestFilters.status());
    }

    private static Specification<ProductOrder> statusEquals(List<StatusProductOrder> statusList) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            
            if(statusList == null || statusList.isEmpty()) {
                return null;
            }

            return root.get("status").in(statusList);
        };

    }

}

