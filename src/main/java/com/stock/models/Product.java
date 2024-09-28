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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String productName;

    private String productCode;

    private String description;

    private Long quantity;

    private Double unitBuyPrice;

    private Double unitSellPrice;

    private LocalDate buyDate;

    @ManyToOne
    private Category category;

}
