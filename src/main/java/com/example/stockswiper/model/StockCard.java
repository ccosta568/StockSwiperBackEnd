package com.example.stockswiper.model;

import java.time.LocalDate;

public class StockCard {
    private String symbol;
    private String name;
    private String sector;
    private Double dividendYield;
    private Double price;
    private String description;
    private LocalDate deckDate;
    
    public StockCard() {}
    
    public StockCard(Stock stock, LocalDate deckDate) {
        this.symbol = stock.getSymbol();
        this.name = stock.getName();
        this.sector = stock.getSector();
        this.dividendYield = stock.getDividendYield();
        this.price = stock.getPrice();
        this.description = stock.getDescription();
        this.deckDate = deckDate;
    }
    
    // Getters and setters
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
    
    public Double getDividendYield() { return dividendYield; }
    public void setDividendYield(Double dividendYield) { this.dividendYield = dividendYield; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public LocalDate getDeckDate() { return deckDate; }
    public void setDeckDate(LocalDate deckDate) { this.deckDate = deckDate; }
}