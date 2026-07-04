package com.keningarcia.restaurant_management_system.service;

import com.keningarcia.restaurant_management_system.repository.OrderRepository;
import com.keningarcia.restaurant_management_system.dto.SalesReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public SalesReportResponse getDailySales(LocalDate date) {
        var start = date.atStartOfDay();
        var end = date.atTime(LocalTime.MAX);

        var orders = orderRepository.findAll().stream()
                .filter(o -> o.getCreatedAt() != null
                        && !o.getCreatedAt().isBefore(start)
                        && !o.getCreatedAt().isAfter(end)
                        && o.getActive())
                .toList();

        var totalOrders = (long) orders.size();
        var totalRevenue = orders.stream()
                .map(o -> o.getTotal() != null ? o.getTotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var totalTax = orders.stream()
                .map(o -> o.getTax() != null ? o.getTax() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var avgOrderValue = totalOrders > 0
                ? totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return new SalesReportResponse(date, date, totalOrders, totalRevenue, totalTax, avgOrderValue);
    }

    @Transactional(readOnly = true)
    public SalesReportResponse getMonthlySales(int year, int month) {
        var start = LocalDate.of(year, month, 1);
        var end = start.withDayOfMonth(start.lengthOfMonth());

        var startDateTime = start.atStartOfDay();
        var endDateTime = end.atTime(LocalTime.MAX);

        var orders = orderRepository.findAll().stream()
                .filter(o -> o.getCreatedAt() != null
                        && !o.getCreatedAt().isBefore(startDateTime)
                        && !o.getCreatedAt().isAfter(endDateTime)
                        && o.getActive())
                .toList();

        var totalOrders = (long) orders.size();
        var totalRevenue = orders.stream()
                .map(o -> o.getTotal() != null ? o.getTotal() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var totalTax = orders.stream()
                .map(o -> o.getTax() != null ? o.getTax() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var avgOrderValue = totalOrders > 0
                ? totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        return new SalesReportResponse(start, end, totalOrders, totalRevenue, totalTax, avgOrderValue);
    }
}
