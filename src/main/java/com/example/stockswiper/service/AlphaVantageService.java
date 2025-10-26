package com.example.stockswiper.service;

import com.example.stockswiper.model.Stock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AlphaVantageService {
    
    @Value("${alphavantage.api-key}")
    private String apiKey;
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    
    public AlphaVantageService() {
        this.webClient = WebClient.builder()
            .baseUrl("https://www.alphavantage.co")
            .build();
        this.objectMapper = new ObjectMapper();
    }
    
    public Stock getStockData(String symbol) {
        try {
            String response = webClient.get()
                .uri("/query?function=OVERVIEW&symbol={symbol}&apikey={apikey}", symbol, apiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();
            
            return parseStockData(response, symbol);
        } catch (Exception e) {
            System.out.println("Alpha Vantage API error for " + symbol + ": " + e.getMessage());
            return createFallbackStock(symbol);
        }
    }
    
    private Stock parseStockData(String json, String symbol) {
        try {
            JsonNode root = objectMapper.readTree(json);
            
            // Check for API limit or error
            if (root.has("Note") || root.has("Error Message")) {
                return createFallbackStock(symbol);
            }
            
            String name = root.path("Name").asText(symbol + " Corp");
            String sector = root.path("Sector").asText("Unknown");
            String description = root.path("Description").asText("No description available");
            
            // Parse dividend yield (as percentage)
            double dividendYield = 0.0;
            String divYieldStr = root.path("DividendYield").asText("0");
            if (!divYieldStr.equals("None") && !divYieldStr.isEmpty()) {
                dividendYield = Double.parseDouble(divYieldStr) * 100; // Convert to percentage
            }
            
            // Parse price (52 week high as fallback)
            double price = root.path("52WeekHigh").asDouble(100.0);
            
            return new Stock(symbol, name, sector, dividendYield, price, description);
            
        } catch (Exception e) {
            System.out.println("JSON parsing error for " + symbol + ": " + e.getMessage());
            return createFallbackStock(symbol);
        }
    }
    
    private Stock createFallbackStock(String symbol) {
        return new Stock(symbol, symbol + " Corp", "Unknown", 0.0, 0.0, "API data unavailable - using fallback");
    }
}