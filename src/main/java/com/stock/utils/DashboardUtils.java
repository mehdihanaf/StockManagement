package com.stock.utils;

import com.stock.models.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DashboardUtils {


    public static Double   calculateMonthlyProfit(List<Sale> sales, Month month) {

        return sales.stream()
                .filter(sale -> sale.getSaleDate().getMonth() == month)
                .mapToDouble(sale ->
                        (sale.getProduct().getUnitSellPrice() - sale.getProduct().getUnitBuyPrice())
                                * sale.getSaleQuantity()
                                - Optional.ofNullable(sale.getDiscount()).orElse(0.0)
                )
                .sum();


    }

}
