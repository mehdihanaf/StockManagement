package com.stock.services.impl;



import com.stock.model.DashboardDTO;
import com.stock.models.Sale;
import com.stock.repository.ICategoryRepository;
import com.stock.repository.IProductRepository;
import com.stock.repository.ISaleRepository;
import com.stock.services.IDashboardService;
import com.stock.utils.DashboardUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


@Service
@Slf4j
public class DashboardServiceImpl implements IDashboardService {

    private final ISaleRepository saleRepository;
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final DashboardUtils dashboardUtils;


    public DashboardServiceImpl(ISaleRepository saleRepository,
                                IProductRepository productRepository,
                                ICategoryRepository categoryRepository, DashboardUtils dashboardUtils) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.dashboardUtils = dashboardUtils;
    }


    @Override
    public DashboardDTO getDashboard() {

        DashboardDTO dashboardDTO = new DashboardDTO();
        List<Sale> sales = saleRepository.findAll();
        Month currentMonth =LocalDate.now().getMonth();
        Month lastMonth = LocalDate.now().minusMonths(1).getMonth();
        Long countCategories = categoryRepository.count();
        Long countProducts = productRepository.count();
        Long countSales = saleRepository.count();
        Double profitsCurrentMonth = DashboardUtils.calculateMonthlyProfit( sales, currentMonth);
        Double profitsLastMonth = DashboardUtils.calculateMonthlyProfit( sales, lastMonth );
        Double margeProfits = ((profitsCurrentMonth - profitsLastMonth)/profitsLastMonth)*100;

        dashboardDTO.setCategoryCount(countCategories);
        dashboardDTO.setProductCount(countProducts);
        dashboardDTO.setSaleCount(countSales);
        dashboardDTO.setProfits(profitsCurrentMonth);
        dashboardDTO.setProfitsMarge(margeProfits);

        return dashboardDTO;
    }
}