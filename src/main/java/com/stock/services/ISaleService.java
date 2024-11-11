package com.stock.services;

import com.stock.model.SaleDTO;
import com.stock.pages.SalePage;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;
import java.util.List;

public interface ISaleService {



    SaleDTO getSaleById(Integer id);

    List<SaleDTO> getAllSales();

    SaleDTO addSale(SaleDTO saleDTO);

    SaleDTO editSale( Integer id, SaleDTO saleDTO);

    void deleteSale(Integer id);

    void deleteSalesById(List<Integer> idList);

    //SalePage getByPage(int page);

    List<SaleDTO> searchSale(String name);

    SalePage searchForSalesByAnyColumn(String name, Pageable pageable);

    Resource exportPdf() throws JRException, FileNotFoundException;
}
