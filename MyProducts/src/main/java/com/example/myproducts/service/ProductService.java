package com.example.myproducts.service;

import com.example.myproducts.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final Map<Long, Product> productMap = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<Product> getAllProducts(){
        return new ArrayList<>(productMap.values());
    }

    public Product getProductById(Long id){

        Product product = productMap.get(id);
        if (product == null) {
            throw new RuntimeException("Product with ID " + id + " not found");
        }
        return product;
    }

    public Product createProduct(Product product){
        product.setId(idCounter.incrementAndGet());
        productMap.put(product.getId(), product);
        return product;
    }

    public Product updateProduct(Long id, Product product) {

        if (!productMap.containsKey(id)) {
            throw new RuntimeException("Product with ID " + id + " not found");
        }

        product.setId(id);
        productMap.put(id, product);
        return product;
    }


    public void deleteProductById(Long id){
        productMap.remove(id);
    }


}
