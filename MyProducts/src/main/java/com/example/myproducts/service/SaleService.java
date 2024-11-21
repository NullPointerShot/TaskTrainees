package com.example.myproducts.service;

import com.example.myproducts.models.Sale;
import com.example.myproducts.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductService productService;

    public SaleService(SaleRepository saleRepository, ProductService productService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
    }

    public Sale createSale(Sale sale) {
        productService.decreaseQuantity(sale.getProduct().getId(), sale.getQuantity());
        sale.setTotalPrice(productService.getProductById(sale.getProduct().getId()).getPrice() * sale.getQuantity());
        return saleRepository.save(sale);
    }


    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale updateSale(Long id, Sale sale) {
        if (!saleRepository.existsById(id)) {
            throw new RuntimeException("Продажа с ID " + id + " не найдена");
        }
        sale.setId(id);
        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}

