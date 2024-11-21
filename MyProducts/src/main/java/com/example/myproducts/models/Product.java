package com.example.myproducts.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255, message = "Название товара должно быть не более 255 символов")
    @Column(nullable = false, length = 255)
    private String name;

    @Size(max = 4096, message = "Описание товара должно быть не более 4096 символов")
    @Column(length = 4096)
    private String description;

    @Min(value = 0, message = "Цена товара не может быть меньше 0")
    @Column(nullable = false)
    private double price;


    @Column(nullable = false, columnDefinition = "integer default 0")
    @Min(value = 0, message = "Кол-во товара не может быть меньше 0")
    private int quantity;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean inStock = false;

}


