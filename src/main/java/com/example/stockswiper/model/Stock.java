package com.example.stockswiper.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    private String symbol;
    
    private String name;
    private String sector;
    private Double dividendYield;
    private Double price;
    private String description;
    
    public Stock() {}
    
    public Stock(String symbol, String name, String sector, Double dividendYield, Double price, String description) {
        this.symbol = symbol;
        this.name = name;
        this.sector = sector;
        this.dividendYield = dividendYield;
        this.price = price;
        this.description = description;
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
}