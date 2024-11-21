package com.example.myproducts.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255, message = "Название документа должно быть не более 255 символов")
    @Column(nullable = false, length = 255)
    private String documentName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Min(value = 1, message = "Количество проданного товара должно быть больше 0")
    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double totalPrice;
}
