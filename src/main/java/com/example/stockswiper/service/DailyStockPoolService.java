package com.example.stockswiper.service;

import com.example.stockswiper.model.DailyStockPool;
import com.example.stockswiper.repository.DailyStockPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class DailyStockPoolService {
    
    @Autowired
    private DailyStockPoolRepository poolRepository;
    
    private final Random random = new Random();
    
    // Large pool of real stock symbols across all sectors
    private final List<String> stockUniverse = Arrays.asList(
        // Tech giants
        "AAPL", "MSFT", "GOOGL", "AMZN", "META", "TSLA", "NVDA", "AMD", "NFLX", "ADBE",
        // Finance
        "JPM", "BAC", "WFC", "GS", "MS", "C", "USB", "PNC", "TFC", "COF",
        // Healthcare
        "JNJ", "PFE", "UNH", "ABBV", "MRK", "TMO", "ABT", "DHR", "BMY", "AMGN",
        // Consumer
        "WMT", "HD", "PG", "KO", "PEP", "COST", "NKE", "MCD", "SBUX", "TGT",
        // Industrial
        "BA", "CAT", "MMM", "HON", "UPS", "LMT", "RTX", "GE", "DE", "EMR",
        // Energy
        "XOM", "CVX", "COP", "EOG", "SLB", "MPC", "VLO", "PSX", "OXY", "HAL",
        // Growth/Emerging
        "PLTR", "ROKU", "SQ", "SHOP", "SNOW", "ZM", "DOCU", "CRWD", "DDOG", "NET",
        "OKTA", "TWLO", "ZS", "ESTC", "SPLK", "MDB", "TEAM", "WDAY", "NOW", "CRM",
        // REITs
        "AMT", "PLD", "CCI", "EQIX", "PSA", "EXR", "AVB", "EQR", "MAA", "UDR",
        // Utilities
        "NEE", "DUK", "SO", "AEP", "EXC", "XEL", "WEC", "ES", "AWK", "ATO",
        // Materials
        "LIN", "APD", "SHW", "ECL", "FCX", "NEM", "DOW", "DD", "PPG", "IFF",
        // Telecom
        "T", "VZ", "TMUS", "CHTR", "CMCSA", "DIS", "NFLX", "PARA", "WBD", "FOX"
    );
    
    public List<String> getTodaysStockPool() {
        LocalDate today = LocalDate.now();
        
        // Check if today's pool already exists
        if (poolRepository.existsByPoolDate(today)) {
            return poolRepository.findSymbolsByPoolDate(today);
        }
        
        // Generate new daily pool
        return generateDailyPool(today);
    }
    
    private List<String> generateDailyPool(LocalDate date) {
        // Clean up old pools (keep last 7 days)
        LocalDate cutoff = date.minusDays(7);
        poolRepository.deleteByPoolDate(cutoff);
        
        // Generate 25 random stocks (5 extra in case some fail)
        List<String> shuffled = new ArrayList<>(stockUniverse);
        Collections.shuffle(shuffled, random);
        List<String> dailyPool = shuffled.subList(0, Math.min(25, shuffled.size()));
        
        // Save to database
        for (int i = 0; i < dailyPool.size(); i++) {
            DailyStockPool poolEntry = new DailyStockPool(dailyPool.get(i), date, i);
            poolRepository.save(poolEntry);
        }
        
        return dailyPool;
    }
}