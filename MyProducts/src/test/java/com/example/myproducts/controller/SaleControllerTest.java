package com.example.myproducts.controller;

import com.example.myproducts.models.Product;
import com.example.myproducts.models.Sale;
import com.example.myproducts.service.SaleService;
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

public class SaleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SaleService saleService;

    @InjectMocks
    private SaleController saleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(saleController).build();
    }

    @Test
    public void testCreateSale() throws Exception {

        Product product = new Product(1L, "Product 1", "Description 1", 100.0, 10, true);
        Sale sale = new Sale(1L, "Sale Document", product, 2, 200.0);
        when(saleService.createSale(any(Sale.class))).thenReturn(sale);


        mockMvc.perform(post("/sales")
                        .contentType("application/json")
                        .content("{\"documentName\":\"Sale Document\", \"product\": {\"id\": 1}, \"quantity\": 2, \"totalPrice\": 200.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.documentName").value("Sale Document"))
                .andExpect(jsonPath("$.product.id").value(1))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.totalPrice").value(200.0));
    }

    @Test
    public void testGetAllSales() throws Exception {

        Product product = new Product(1L, "Product 1", "Description 1", 100.0, 10, true);
        Sale sale = new Sale(1L, "Sale Document", product, 2, 200.0);
        when(saleService.getAllSales()).thenReturn(Collections.singletonList(sale));


        mockMvc.perform(get("/sales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].documentName").value("Sale Document"))
                .andExpect(jsonPath("$[0].product.id").value(1))
                .andExpect(jsonPath("$[0].quantity").value(2))
                .andExpect(jsonPath("$[0].totalPrice").value(200.0));
    }

    @Test
    public void testUpdateSale() throws Exception {

        Product product = new Product(1L, "Product 1", "Description 1", 100.0, 10, true);
        Sale updatedSale = new Sale(1L, "Updated Sale Document", product, 3, 300.0);
        when(saleService.updateSale(eq(1L), any(Sale.class))).thenReturn(updatedSale);


        mockMvc.perform(put("/sales/1")
                        .contentType("application/json")
                        .content("{\"documentName\":\"Updated Sale Document\", \"product\": {\"id\": 1}, \"quantity\": 3, \"totalPrice\": 300.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentName").value("Updated Sale Document"))
                .andExpect(jsonPath("$.product.id").value(1))
                .andExpect(jsonPath("$.quantity").value(3))
                .andExpect(jsonPath("$.totalPrice").value(300.0));
    }

    @Test
    public void testDeleteSale() throws Exception {

        mockMvc.perform(delete("/sales/1"))
                .andExpect(status().isNoContent());


        verify(saleService, times(1)).deleteSale(1L);
    }
}
