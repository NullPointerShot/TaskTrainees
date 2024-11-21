package com.example.myproducts.controller;

import com.example.myproducts.models.Sale;
import com.example.myproducts.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sale createSale(@RequestBody @Valid Sale sale) {
        return saleService.createSale(sale);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Sale updateSale(@PathVariable Long id, @RequestBody @Valid Sale sale) {
        return saleService.updateSale(id, sale);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSale(@PathVariable Long id) {
        saleService.deleteSale(id);
    }
}
