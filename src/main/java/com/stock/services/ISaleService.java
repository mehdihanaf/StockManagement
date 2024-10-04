package com.stock.services;

import com.stock.model.SaleDTO;
import com.stock.pages.SalePage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISaleService {



    SaleDTO getSaleById(Integer id);

    List<SaleDTO> getAllSales();

    SaleDTO addSale(SaleDTO saleDTO);

    SaleDTO editSale( Integer id, SaleDTO saleDTO);

    void deleteSale(Integer id);

    //SalePage getByPage(int page);

    List<SaleDTO> searchSale(String name);

    SalePage searchForSalesByAnyColumn(String name, Pageable pageable);
}
