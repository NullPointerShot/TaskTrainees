package com.example.myproducts.service;

import com.example.myproducts.models.Product;
import com.example.myproducts.repository.ProductRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public List<Product> getFilteredAndSortedProducts(
            String name,
            Double minPrice,
            Double maxPrice,
            Boolean inStock,
            String sortField,
            String sortDirection,
            int limit
    ) {
        Pageable pageable = PageRequest.of(
                0,
                limit,
                "desc".equalsIgnoreCase(sortDirection)
                        ? Sort.by(sortField).descending()
                        : Sort.by(sortField).ascending()
        );

        return productRepository.findByNameContainingIgnoreCaseAndPriceBetweenAndInStock(
                name != null ? name : "",
                minPrice != null ? minPrice : 0.0,
                maxPrice != null ? maxPrice : Double.MAX_VALUE,
                inStock != null ? inStock : true,
                pageable
        );
    }

    public Product increaseQuantity(Long productId, int quantity) {
        Product product = getProductById(productId);
        product.setQuantity(product.getQuantity() + quantity);
        if (product.getQuantity() > 0) {
            product.setInStock(true);
        }
        return productRepository.save(product);
    }

    public Product decreaseQuantity(Long productId, int quantity) {
        Product product = getProductById(productId);

        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Not enough product in stock");
        }

        product.setQuantity(product.getQuantity() - quantity);

        if (product.getQuantity() == 0) {
            product.setInStock(false);
        }

        return productRepository.save(product);
    }

}
