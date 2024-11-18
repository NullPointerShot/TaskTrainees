package com.example.myproducts.service;

import com.example.myproducts.models.Product;
import com.example.myproducts.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestBody;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ProductServiceTest {

    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        productService = new ProductService();
        product = new Product(null, "Laptop", "High-end laptop", 1500.00, true);

    }

    @Test
    void testCreateProduct() {
        Product createdProduct = productService.createProduct(product);


        assertNotNull(createdProduct.getId(), "ID продукта не должен быть null");
        assertEquals("Laptop", createdProduct.getName(), "Имя продукта должно быть 'Laptop'");
        assertEquals(1500.00, createdProduct.getPrice(), "Цена продукта должна быть 1500.00");
    }

    @Test
    void testUpdatedProductWithNullId() {
        assertThrows(RuntimeException.class,
                () -> productService.updateProduct(null, product),
                "Ожидается исключение IllegalArgumentException при передаче null ID");
    }



    @Test
    void testGetAllProducts() {
        Product product2 = new Product(null, "Phone", "Smartphone", 800.00, true);


        productService.createProduct(product);
        productService.createProduct(product2);


        List<Product> products = productService.getAllProducts();


        assertEquals(2, products.size(), "Количество продуктов должно быть 2");
    }

    @Test
    void testGetProductById() {
        Product createdProduct = productService.createProduct(product);
        Long productId = createdProduct.getId();


        Product foundProduct = productService.getProductById(productId);


        assertEquals(productId, foundProduct.getId(), "ID продукта должен совпадать");
        assertEquals("Laptop", foundProduct.getName(), "Имя продукта должно быть 'Laptop'");
    }


    @Test
    void testUpdateProduct() {

        Product createdProduct = productService.createProduct(product);
        Long productId = createdProduct.getId();


        Product updatedProduct = new Product(productId, "Laptop Pro", "Updated laptop description", 2000.00, false);
        Product result = productService.updateProduct(productId, updatedProduct);



        assertEquals("Laptop Pro", result.getName(), "Имя продукта должно быть обновлено");
        assertEquals("Updated laptop description", result.getDescription(), "Описание продукта должно быть обновлено");
        assertFalse(result.isInStock(), "Статус наличия товара должен быть false");
    }


    @Test
    void testDeleteProductById() {

        Product createdProduct = productService.createProduct(product);
        Long productId = createdProduct.getId();


        productService.deleteProductById(productId);


        assertThrows(RuntimeException.class, () -> productService.getProductById(productId), "Продукт с ID " + productId + " должен быть удален");
    }


}

