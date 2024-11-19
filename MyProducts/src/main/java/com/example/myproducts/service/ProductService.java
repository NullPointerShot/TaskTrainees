package com.example.myproducts.service;

import com.example.myproducts.models.Product;
import com.example.myproducts.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));

    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {

        if (!productRepository.existsById(id)){
            throw new RuntimeException("Product with ID " + id + " not found");
        }
        product.setId(id);
        return productRepository.save(product);
    }


    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
    }

}
