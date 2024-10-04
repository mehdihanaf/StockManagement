package com.stock.pages;

import com.stock.model.SaleDTO;
import lombok.Data;

import java.util.List;

@Data
public class SalePage {

    private List<SaleDTO> sales;
    private long totalCount;
    private long pageIndex;

}
