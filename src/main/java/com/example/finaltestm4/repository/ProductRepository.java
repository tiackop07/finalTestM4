package com.example.finaltestm4.repository;
import com.example.finaltestm4.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByNameContaining(String name);
    List<Product> findByPrice(Long price);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContainingAndPriceAndCategoryId(String name, Long price, Long categoryId);
}
