package com.emerson.pricecontrol.service;

import com.emerson.pricecontrol.entity.Item;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ChartService {

    public byte[] createProductPriceTrendChart(Long productId, List<Item> items) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Item item : items) {
            dataset.addValue(item.getPrice(), "Price", item.getDate().toString());
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Price Trend for Product ID " + productId,
            "Date",
            "Price",
            dataset
        );

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(outputStream, chart, 800, 600);
        return outputStream.toByteArray();
    }
}
