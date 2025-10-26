package com.example.stockswiper.repository;

import com.example.stockswiper.model.UserSwipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface UserSwipeRepository extends JpaRepository<UserSwipe, Long> {
    
    @Query("SELECT COUNT(u) FROM UserSwipe u WHERE u.userId = ?1 AND u.swipeDate = ?2")
    long countByUserIdAndSwipeDate(String userId, LocalDate date);
}