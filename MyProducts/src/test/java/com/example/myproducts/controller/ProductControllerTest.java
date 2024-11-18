package com.example.myproducts.controller;

import com.example.myproducts.controller.ProductController;
import com.example.myproducts.models.Product;
import com.example.myproducts.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        product = new Product(1L, "Laptop", "High-end laptop", 1500.00, true);
    }

    @Test
    void testGetAllProducts() throws Exception {

        List<Product> products = Arrays.asList(product);
        when(productService.getAllProducts()).thenReturn(products);


        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[0].description").value("High-end laptop"));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById() throws Exception {

        when(productService.getProductById(1L)).thenReturn(product);


        mockMvc.perform(get("/products/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.description").value("High-end laptop"));

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testCreateProduct() throws Exception {

        when(productService.createProduct(any(Product.class))).thenReturn(product);


        mockMvc.perform(post("/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Laptop\", \"description\": \"High-end laptop\", \"price\": 1500.00, \"inStock\": true }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.description").value("High-end laptop"));

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    void testUpdateProduct() throws Exception {

        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(product);


        mockMvc.perform(put("/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Laptop Pro\", \"description\": \"Updated laptop description\", \"price\": 2000.00, \"inStock\": false }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop Pro"))
                .andExpect(jsonPath("$.description").value("Updated laptop description"));

        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void testDeleteProduct() throws Exception {

        mockMvc.perform(delete("/products/{id}", 1L))
                .andExpect(status().isNoContent());


        verify(productService, times(1)).deleteProductById(1L);
    }
}
