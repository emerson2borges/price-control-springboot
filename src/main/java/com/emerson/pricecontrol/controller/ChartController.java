package com.emerson.pricecontrol.controller;

import com.emerson.pricecontrol.entity.Item;
import com.emerson.pricecontrol.entity.Product;
import com.emerson.pricecontrol.repository.ItemRepository;
import com.emerson.pricecontrol.repository.ProductRepository;
import com.emerson.pricecontrol.service.ChartService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/charts")
@Controller
public class ChartController {

    private ChartService chartService;
    private ItemRepository itemRepository;
    private ProductRepository productRepository;

    @Autowired
    public ChartController(
        ChartService chartService,
        ItemRepository itemRepository,
        ProductRepository productRepository
    ) {
        this.chartService = chartService;
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/product/{productId}/price-trend")
    public void getPriceTrend(@PathVariable Long productId, HttpServletResponse response) throws IOException {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Produto não encontrado");
            return;
        }

        List<Item> items = itemRepository.findByProductOrderByPriceAsc(product);
        if (items.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT, "Nenhum registro encontrado para o produto");
            return;
        }

        byte[] chart = chartService.createProductPriceTrendChart(productId, items);
        response.setContentType("image/png");
        response.getOutputStream().write(chart);
        response.getOutputStream().flush();
    }
}
