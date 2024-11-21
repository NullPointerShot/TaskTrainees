package com.example.myproducts.service;

import com.example.myproducts.models.Supply;
import com.example.myproducts.repository.SupplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final ProductService productService;


    public SupplyService(SupplyRepository supplyRepository, ProductService productService) {
        this.supplyRepository = supplyRepository;
        this.productService = productService;
    }

    public Supply createSupply(Supply supply) {
        productService.increaseQuantity(supply.getProduct().getId(), supply.getQuantity());
        return supplyRepository.save(supply);
    }


    public List<Supply> getAllSupplies() {
        return supplyRepository.findAll();
    }

    public Supply updateSupply(Long id, Supply supply) {
        if (!supplyRepository.existsById(id)) {
            throw new RuntimeException("Поставка с ID " + id + " не найдена");
        }
        supply.setId(id);
        return supplyRepository.save(supply);
    }

    public void deleteSupply(Long id) {
        supplyRepository.deleteById(id);
    }
}

