package com.stock.controllers;

import com.stock.StockManagementConstants;
import com.stock.api.controller.DashboardApi;
import com.stock.model.DashboardDTO;
import com.stock.services.IDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
@RequiredArgsConstructor
public class DashboardController implements DashboardApi {

    private final IDashboardService dashboardService;


    @Override
    public ResponseEntity<DashboardDTO> getAllInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dashboardService.getDashboard());
    }
}