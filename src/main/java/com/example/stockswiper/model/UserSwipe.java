package com.example.stockswiper.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_swipes")
public class UserSwipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String userId;
    private String stockSymbol;
    private boolean liked;
    private LocalDate swipeDate;
    
    public UserSwipe() {}
    
    public UserSwipe(String userId, String stockSymbol, boolean liked) {
        this.userId = userId;
        this.stockSymbol = stockSymbol;
        this.liked = liked;
        this.swipeDate = LocalDate.now();
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getStockSymbol() { return stockSymbol; }
    public void setStockSymbol(String stockSymbol) { this.stockSymbol = stockSymbol; }
    
    public boolean isLiked() { return liked; }
    public void setLiked(boolean liked) { this.liked = liked; }
    
    public LocalDate getSwipeDate() { return swipeDate; }
    public void setSwipeDate(LocalDate swipeDate) { this.swipeDate = swipeDate; }
}