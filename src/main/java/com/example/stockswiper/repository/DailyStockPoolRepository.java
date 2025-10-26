package com.example.stockswiper.repository;

import com.example.stockswiper.model.DailyStockPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyStockPoolRepository extends JpaRepository<DailyStockPool, Long> {
    
    @Query("SELECT d.symbol FROM DailyStockPool d WHERE d.poolDate = ?1 ORDER BY d.position")
    List<String> findSymbolsByPoolDate(LocalDate date);
    
    void deleteByPoolDate(LocalDate date);
    
    boolean existsByPoolDate(LocalDate date);
}