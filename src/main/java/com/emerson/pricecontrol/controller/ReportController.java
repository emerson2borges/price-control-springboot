package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

//@RestController
@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(@Autowired ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/lowest-price/{productId}")
    public BigDecimal getLowestPriceForProduct(@PathVariable Long productId) {
        return reportService.findLowestPriceForProduct(productId);
    }

    @GetMapping("/cheapest-day/{productId}")
    public LocalDate getCheapestDayForProduct(@PathVariable Long productId) {
        return reportService.findCheapestDayForProduct(productId);
    }

    @GetMapping("/product/{productId}")
    public String getProductReports(@PathVariable Long productId, Model model) {
        BigDecimal lowestPrice = reportService.findLowestPriceForProduct(productId);
        LocalDate cheapestDay = reportService.findCheapestDayForProduct(productId);

        model.addAttribute("lowestPrice", lowestPrice);
        model.addAttribute("cheapestDay", cheapestDay);

        System.out.println(model);

        return "index";
    }
}
