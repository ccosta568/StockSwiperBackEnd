package com.example.stockswiper.service;

import com.example.stockswiper.model.Stock;
import com.example.stockswiper.model.StockCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
public class DeckService {
    
    @Autowired
    private AlphaVantageService alphaVantageService;
    
    @Autowired
    private DailyStockPoolService dailyStockPoolService;
    
    private Map<LocalDate, List<StockCard>> deckCache = new HashMap<>();
    
    public List<StockCard> getTodaysDeck() {
        LocalDate today = LocalDate.now();
        
        if (deckCache.containsKey(today)) {
            return deckCache.get(today);
        }
        
        return buildDeck(today);
    }
    
    private List<StockCard> buildDeck(LocalDate date) {
        List<String> symbols = dailyStockPoolService.getTodaysStockPool();
        List<StockCard> deck = new ArrayList<>();
        
        // Build deck with sector diversity (max 4 per sector)
        Map<String, Integer> sectorCount = new HashMap<>();
        
        for (String symbol : symbols) {
            if (deck.size() >= 20) break;
            
            try {
                Stock stock = alphaVantageService.getStockData(symbol);
                String sector = stock.getSector();
                
                if (sectorCount.getOrDefault(sector, 0) < 4) {
                    deck.add(new StockCard(stock, date));
                    sectorCount.put(sector, sectorCount.getOrDefault(sector, 0) + 1);
                }
            } catch (Exception e) {
                System.out.println("Failed to fetch data for: " + symbol);
            }
        }
        
        // Cache the deck
        deckCache.put(date, deck);
        
        // Clean old cache entries
        deckCache.entrySet().removeIf(entry -> entry.getKey().isBefore(date.minusDays(2)));
        
        return deck;
    }
}