package com.example.myproducts.repository;

import com.example.myproducts.models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
        List<Product> findByNameContainingIgnoreCaseAndPriceBetweenAndInStock(
                String name,
                Double minPrice,
                Double maxPrice,
                Boolean inStock,
                Pageable pageable
        );
}
