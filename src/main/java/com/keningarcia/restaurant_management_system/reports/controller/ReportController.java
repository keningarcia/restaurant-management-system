package com.keningarcia.restaurant_management_system.reports.controller;

import com.keningarcia.restaurant_management_system.reports.dto.SalesReportResponse;
import com.keningarcia.restaurant_management_system.reports.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/sales/daily")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<SalesReportResponse> getDailySales(@RequestParam LocalDate date) {
        return ResponseEntity.ok(reportService.getDailySales(date));
    }

    @GetMapping("/sales/monthly")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<SalesReportResponse> getMonthlySales(
            @RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(reportService.getMonthlySales(year, month));
    }
}
