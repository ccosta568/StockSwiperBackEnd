package com.example.stockswiper.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "daily_stock_pool")
public class DailyStockPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String symbol;
    private LocalDate poolDate;
    private int position;
    
    public DailyStockPool() {}
    
    public DailyStockPool(String symbol, LocalDate poolDate, int position) {
        this.symbol = symbol;
        this.poolDate = poolDate;
        this.position = position;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    
    public LocalDate getPoolDate() { return poolDate; }
    public void setPoolDate(LocalDate poolDate) { this.poolDate = poolDate; }
    
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
}