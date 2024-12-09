package com.example.finaltestm4.service;

import com.example.finaltestm4.model.Product;
import com.example.finaltestm4.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> findAllProducts(int page, int size){
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public Product findProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public void deleteAllProducts(){
        productRepository.deleteAll();
    }
}
