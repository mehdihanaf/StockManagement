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

import java.util.List;

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

        Long countCategories = categoryRepository.count();
        Long countProducts = productRepository.count();
        Long countSales = saleRepository.count();
        Double profits = sales.stream()
                .mapToDouble(sale ->
                        (sale.getProduct().getUnitSellPrice() - sale.getProduct().getUnitBuyPrice())
                                * sale.getSaleQuantity()
                                - sale.getDiscount()
                )
                .sum();

        dashboardDTO.setCategoryCount(countCategories);
        dashboardDTO.setProductCount(countProducts);
        dashboardDTO.setSaleCount(countSales);
        dashboardDTO.setProfits(profits);
        return dashboardDTO;
    }
}
