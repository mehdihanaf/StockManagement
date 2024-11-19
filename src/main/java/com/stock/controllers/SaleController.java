package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.SaleApi;
import com.stock.model.SaleDTO;
import com.stock.pages.SalePage;
import com.stock.services.ISaleService;
import com.stock.utils.PagingUtil;
import com.stock.utils.TextUtil;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import org.springframework.http.*;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
@RequiredArgsConstructor
public class SaleController implements SaleApi {

    private final ISaleService saleService;
    private final TextUtil textUtil;


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
    public ResponseEntity<Resource> exportPdfSales(Integer id) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sales_report.pdf");
        headers.setContentType(MediaType.APPLICATION_PDF);
        try {
            Resource report = saleService.exportPdf(id);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(report.contentLength())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(report);
        } catch (JRException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Resource> exportSalesAsCSVFile(String input, String sortField, String order, Integer pageNum, Integer limitPerPage) {
        Pageable pageable = PagingUtil.getPageable(sortField, "saleDate", order, pageNum, limitPerPage);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String filename = "Sales" + LocalDateTime.now().format(formatter) + ".csv";
        Resource resource = saleService.export(input, pageable);

        // Prepare response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
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
    public ResponseEntity<String> deleteSalesById(List<Integer> idList) {
        String sale_txt = textUtil.getMessage("sales");
        saleService.deleteSalesById(idList);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(textUtil.getMessage("deleteall.validation",sale_txt));
    }

    @Override
    public ResponseEntity<List<SaleDTO>> getSalesByName(@RequestParam String description) {

        List<SaleDTO> listSales = saleService.searchSale(description);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listSales);
    }

    @Override
    public ResponseEntity<SalePage> searchForSalesByAnyColumn(@RequestParam String input,
                                                              @RequestParam("sort") String sortField,
                                                              @RequestParam String order,
                                                              @RequestParam("page") Integer pageNum,
                                                              @RequestParam("per_page") Integer limitPerPage
    ) {

        Pageable pageable = PagingUtil.getPageable(sortField, "saleDate", order, pageNum, limitPerPage);


        SalePage salePage = saleService.searchForSalesByAnyColumn(input, pageable);
        salePage.setPageIndex((long) pageNum);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(salePage);
    }



}
