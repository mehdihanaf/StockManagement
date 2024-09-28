package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.SaleApi;
import com.stock.model.SaleDTO;
import com.stock.services.ISaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
public class SaleController implements SaleApi {

    private final ISaleService saleService;

    public SaleController(ISaleService saleService) {
        this.saleService = saleService;
    }

    @Override
    public ResponseEntity<SaleDTO> getSaleById(@PathVariable("id") Integer id) {

        SaleDTO saledto = saleService.getSaleById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saledto);
    }



    @Override
    public ResponseEntity<List<SaleDTO>> getAllSales() {

        List<SaleDTO> listSales = saleService.getAllSales();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listSales);
    }


    @Override
    public ResponseEntity<SaleDTO> addSale(@RequestBody SaleDTO saleDTO) {

            SaleDTO saleDTO1 = saleService.addSale(saleDTO);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(saleDTO1);
         //}catch (IllegalArgumentException ex) {
         //   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
         //}
    }

    @Override
    public ResponseEntity<SaleDTO> editSale(@PathVariable("id") Integer id, @RequestBody SaleDTO saleDTO) {

        SaleDTO saleDTO1 = saleService.editSale(id, saleDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saleDTO1);
    }

    @Override
    public ResponseEntity<String> deleteSale(@PathVariable("id") Integer id) {

        //todo validation
        saleService.deleteSale(id);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Sale is deleted");
    }

    @Override
    public ResponseEntity<List<SaleDTO>> getSalesByName(@RequestParam String description) {

        List<SaleDTO> listSales = saleService.searchSale(description);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listSales);
    }


}
