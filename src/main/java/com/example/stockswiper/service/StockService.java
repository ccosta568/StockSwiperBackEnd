package com.example.stockswiper.service;

import com.example.stockswiper.model.Stock;
import com.example.stockswiper.model.UserSwipe;
import com.example.stockswiper.repository.UserSwipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class StockService {
    
    @Autowired
    private UserSwipeRepository swipeRepository;
    
    @Autowired
    private AlphaVantageService alphaVantageService;
    
    @Autowired
    private DailyStockPoolService dailyStockPoolService;
    
    private Random random = new Random();
    
    public Stock getRandomDividendStock() {
        List<String> todaysStocks = dailyStockPoolService.getTodaysStockPool();
        String symbol = todaysStocks.get(random.nextInt(todaysStocks.size()));
        return alphaVantageService.getStockData(symbol);
    }
    
    public boolean canUserSwipe(String userId) {
        long todaySwipes = swipeRepository.countByUserIdAndSwipeDate(userId, LocalDate.now());
        return todaySwipes < 20;
    }
    
    public void recordSwipe(String symbol, boolean liked) {
        recordSwipe(symbol, liked, "demo-user");
    }
    
    public void recordSwipe(String symbol, boolean liked, String userId) {
        if (!canUserSwipe(userId)) {
            throw new RuntimeException("Daily swipe limit reached");
        }
        
        UserSwipe swipe = new UserSwipe(userId, symbol, liked);
        swipeRepository.save(swipe);
    }
}