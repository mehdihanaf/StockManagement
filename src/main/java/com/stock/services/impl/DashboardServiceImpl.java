package com.stock.services.impl;


import com.stock.exceptions.TMNotFoundException;
import com.stock.model.DashboardDTO;
import com.stock.models.Sale;
import com.stock.repository.ICategoryRepository;
import com.stock.repository.IProductRepository;
import com.stock.repository.ISaleRepository;
import com.stock.services.IDashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DashboardServiceImpl implements IDashboardService {

    private final ISaleRepository saleRepository;
    private final IProductRepository productRepository;

    private final ICategoryRepository categoryRepository;


    public DashboardServiceImpl(ISaleRepository saleRepository,
                                IProductRepository productRepository,
                                ICategoryRepository categoryRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public DashboardDTO getDashboard() {

        DashboardDTO dashboardDTO = new DashboardDTO();
        List<Sale> sales = saleRepository.findAll();
        Month currentMonth = LocalDate.now().getMonth();
        Long countCategories = categoryRepository.count();
        Long countProducts = productRepository.count();
        Long countSales = saleRepository.count();
        Double profits = sales.stream()
                .filter(sale -> sale.getSaleDate().getMonth() == currentMonth)
                .mapToDouble(sale ->
                        (sale.getProduct().getUnitSellPrice() - sale.getProduct().getUnitBuyPrice())
                                * sale.getSaleQuantity()
                                - Optional.ofNullable(sale.getDiscount()).orElse(0.0)
                )
                .sum();

        dashboardDTO.setCategoryCount(countCategories);
        dashboardDTO.setProductCount(countProducts);
        dashboardDTO.setSaleCount(countSales);
        dashboardDTO.setProfits(profits);
        return dashboardDTO;
    }
}
