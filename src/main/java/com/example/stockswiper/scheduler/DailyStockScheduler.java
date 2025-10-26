package com.example.stockswiper.scheduler;

import com.example.stockswiper.service.DailyStockPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyStockScheduler {
    
    @Autowired
    private DailyStockPoolService dailyStockPoolService;
    
    // Run at 12:01 AM every day
    @Scheduled(cron = "0 1 0 * * *")
    public void generateDailyStockPool() {
        System.out.println("Generating new daily stock pool...");
        dailyStockPoolService.getTodaysStockPool();
        System.out.println("Daily stock pool generated successfully");
    }
}