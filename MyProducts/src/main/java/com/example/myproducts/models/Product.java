package com.example.myproducts.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;
    @NotNull
    @Size(max = 255)
    private String name;
    @Size(max = 4094)
    private String description;
    @Min(0)
    private double price;
    private boolean inStock = false;

}
