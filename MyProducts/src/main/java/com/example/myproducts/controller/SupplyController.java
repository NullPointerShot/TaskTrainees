package com.example.myproducts.controller;

import com.example.myproducts.models.Supply;
import com.example.myproducts.service.SupplyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SupplyController {

    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Supply createSupply(@RequestBody @Valid Supply supply) {
        return supplyService.createSupply(supply);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Supply> getAllSupplies() {
        return supplyService.getAllSupplies();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Supply updateSupply(@PathVariable Long id, @RequestBody @Valid Supply supply) {
        return supplyService.updateSupply(id, supply);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSupply(@PathVariable Long id) {
        supplyService.deleteSupply(id);
    }
}
