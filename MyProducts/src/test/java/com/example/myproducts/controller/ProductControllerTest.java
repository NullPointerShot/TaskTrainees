package com.example.myproducts.controller;

import com.example.myproducts.models.Product;
import com.example.myproducts.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testGetAllProducts() throws Exception {

        Product product = new Product(1L, "Product 1", "Description 1", 100.0, 10, true);
        when(productService.getAllProducts()).thenReturn(Collections.singletonList(product));


        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[0].price").value(100.0))
                .andExpect(jsonPath("$[0].quantity").value(10))
                .andExpect(jsonPath("$[0].inStock").value(true));
    }

    @Test
    public void testGetProductById() throws Exception {

        Product product = new Product(1L, "Product 1", "Description 1", 100.0, 10, true);
        when(productService.getProductById(1L)).thenReturn(product);


        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.inStock").value(true));
    }

    @Test
    public void testCreateProduct() throws Exception {

        Product product = new Product(1L, "Product 1", "Description 1", 100.0, 10, true);
        when(productService.createProduct(any(Product.class))).thenReturn(product);


        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content("{\"name\":\"Product 1\", \"description\":\"Description 1\", \"price\":100.0, \"quantity\":10, \"inStock\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.description").value("Description 1"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.inStock").value(true));
    }

    @Test
    public void testUpdateProduct() throws Exception {

        Product updatedProduct = new Product(1L, "Updated Product", "Updated Description", 150.0, 5, false);
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(updatedProduct);


        mockMvc.perform(put("/products/1")
                        .contentType("application/json")
                        .content("{\"name\":\"Updated Product\", \"description\":\"Updated Description\", \"price\":150.0, \"quantity\":5, \"inStock\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.price").value(150.0))
                .andExpect(jsonPath("$.quantity").value(5))
                .andExpect(jsonPath("$.inStock").value(false));
    }

    @Test
    public void testDeleteProduct() throws Exception {

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());


        verify(productService, times(1)).deleteProductById(1L);
    }

    @Test
    public void testGetFilteredProducts() throws Exception {

        Product product = new Product(1L, "Product 1", "Description 1", 100.0, 10, true);
        when(productService.getFilteredAndSortedProducts(any(), any(), any(), any(), any(), any(), anyInt()))
                .thenReturn(Collections.singletonList(product));


        mockMvc.perform(get("/products/filter")
                        .param("name", "Product 1")
                        .param("minPrice", "50")
                        .param("maxPrice", "150")
                        .param("inStock", "true")
                        .param("sortField", "price")
                        .param("sortDirection", "asc")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"))
                .andExpect(jsonPath("$[0].price").value(100.0))
                .andExpect(jsonPath("$[0].quantity").value(10))
                .andExpect(jsonPath("$[0].inStock").value(true));
    }
}
