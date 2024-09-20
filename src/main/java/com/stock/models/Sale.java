package com.stock.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sale {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private  String description;

    private LocalDate saleDate;

    private Long saleQuantity;

    private Double  discount;

    @ManyToOne
    private Product product;

    }


