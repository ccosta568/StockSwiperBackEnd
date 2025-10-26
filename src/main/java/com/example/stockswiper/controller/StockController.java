package com.example.stockswiper.controller;

import com.example.stockswiper.model.Stock;
import com.example.stockswiper.model.StockCard;
import com.example.stockswiper.service.StockService;
import com.example.stockswiper.service.DeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "http://localhost:4200")
public class StockController {
    
    @Autowired
    private StockService stockService;
    
    @Autowired
    private DeckService deckService;
    
    @GetMapping("/deck")
    public ResponseEntity<List<StockCard>> getDeck(@RequestHeader(value = "X-Device-Id", required = false) String deviceId) {
        String userId = deviceId != null ? deviceId : "demo-user";
        
        if (!stockService.canUserSwipe(userId)) {
            return ResponseEntity.status(429)
                .header("X-RateLimit-Limit", "20")
                .header("X-RateLimit-Remaining", "0")
                .header("X-RateLimit-Reset", String.valueOf(System.currentTimeMillis() + 86400000))
                .build();
        }
        
        List<StockCard> deck = deckService.getTodaysDeck();
        
        return ResponseEntity.ok()
            .header("X-Deck-Date", LocalDate.now().toString())
            .header("Cache-Control", "public, max-age=300")
            .header("X-RateLimit-Limit", "20")
            .header("X-RateLimit-Remaining", String.valueOf(20 - getCurrentSwipeCount(userId)))
            .body(deck);
    }
    
    @PostMapping("/swipe")
    public ResponseEntity<Map<String, String>> recordSwipe(
            @RequestParam String symbol, 
            @RequestParam boolean liked,
            @RequestHeader(value = "X-Device-Id", required = false) String deviceId) {
        
        String userId = deviceId != null ? deviceId : "demo-user";
        
        try {
            stockService.recordSwipe(symbol, liked, userId);
            return ResponseEntity.ok()
                .header("X-RateLimit-Remaining", String.valueOf(20 - getCurrentSwipeCount(userId)))
                .body(Map.of("status", "success"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(429)
                .header("X-RateLimit-Limit", "20")
                .header("X-RateLimit-Remaining", "0")
                .body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/swipe-status")
    public Map<String, Object> getSwipeStatus(@RequestHeader(value = "X-Device-Id", required = false) String deviceId) {
        String userId = deviceId != null ? deviceId : "demo-user";
        boolean canSwipe = stockService.canUserSwipe(userId);
        int remaining = 20 - getCurrentSwipeCount(userId);
        
        return Map.of(
            "canSwipe", canSwipe, 
            "dailyLimit", 20,
            "remaining", remaining
        );
    }
    
    private int getCurrentSwipeCount(String userId) {
        return 20 - (stockService.canUserSwipe(userId) ? 20 : 0); // Simplified for now
    }
    
    // Legacy endpoint - deprecated
    @GetMapping("/random")
    @Deprecated
    public ResponseEntity<Stock> getRandomStock() {
        return ResponseEntity.status(410).build(); // Gone - use /deck instead
    }
}