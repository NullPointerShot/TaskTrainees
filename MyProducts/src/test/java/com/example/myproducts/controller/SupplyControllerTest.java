package com.example.myproducts.controller;

import com.example.myproducts.models.Product;
import com.example.myproducts.models.Supply;
import com.example.myproducts.service.SupplyService;
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

public class SupplyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SupplyService supplyService;

    @InjectMocks
    private SupplyController supplyController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(supplyController).build();
    }

    @Test
    public void testCreateSupply() throws Exception {

        Product product = new Product(1L, "Product 1", "Description 1", 100.0, 10, true);
        Supply supply = new Supply(1L, "Supply Document", product, 5);
        when(supplyService.createSupply(any(Supply.class))).thenReturn(supply);


        mockMvc.perform(post("/supplies")
                        .contentType("application/json")
                        .content("{\"documentName\":\"Supply Document\", \"product\": {\"id\": 1}, \"quantity\": 5}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.documentName").value("Supply Document"))
                .andExpect(jsonPath("$.product.id").value(1))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    public void testGetAllSupplies() throws Exception {

        Product product = new Product(1L, "Product 1", "Description 1", 100.0, 10, true);
        Supply supply = new Supply(1L, "Supply Document", product, 5);
        when(supplyService.getAllSupplies()).thenReturn(Collections.singletonList(supply));


        mockMvc.perform(get("/supplies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].documentName").value("Supply Document"))
                .andExpect(jsonPath("$[0].product.id").value(1))
                .andExpect(jsonPath("$[0].quantity").value(5));
    }

    @Test
    public void testUpdateSupply() throws Exception {

        Product product = new Product(1L, "Product 1", "Description 1", 100.0, 10, true);
        Supply updatedSupply = new Supply(1L, "Updated Supply Document", product, 10);
        when(supplyService.updateSupply(eq(1L), any(Supply.class))).thenReturn(updatedSupply);


        mockMvc.perform(put("/supplies/1")
                        .contentType("application/json")
                        .content("{\"documentName\":\"Updated Supply Document\", \"product\": {\"id\": 1}, \"quantity\": 10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentName").value("Updated Supply Document"))
                .andExpect(jsonPath("$.product.id").value(1))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    public void testDeleteSupply() throws Exception {

        mockMvc.perform(delete("/supplies/1"))
                .andExpect(status().isNoContent());


        verify(supplyService, times(1)).deleteSupply(1L);
    }
}
